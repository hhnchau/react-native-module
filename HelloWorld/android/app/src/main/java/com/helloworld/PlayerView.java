package com.helloworld;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.tutk.sample.AVAPI.action.PlayerAction;
import com.tutk.sample.AVAPI.base.BaseApplication;
import com.tutk.sample.AVAPI.bean.DeviceBean;
import com.tutk.sample.AVAPI.bean.EventResult;
import com.tutk.sample.AVAPI.bean.PlayerViewBean;
import com.xc.p2pVideo.NativeMediaPlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import static com.xc.p2pVideo.NativeMediaPlayer.DECODE;


public class PlayerView extends RelativeLayout {
    private PlayerViewBean playerViewBean;
    private DeviceBean deviceBean;
    private RelativeLayout playerParent;
    private int screenWidth = 0, screenHeight = 0;

    public PlayerView(Context context) {
        super(context);
        BaseApplication base = new BaseApplication();
        base.initConfig();
        NativeMediaPlayer.JniInitClassToJni();
        EventBus.getDefault().register(this);
        initView(context);
        //init();
    }

    public void init(String uid, String pw) {

        ViewTreeObserver obse = playerParent.getViewTreeObserver();
        obse.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                playerParent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                screenWidth = playerParent.getWidth();
                screenHeight = playerParent.getHeight();
                Log.e("PlayerNative: ", "screenWidth is " + screenWidth + " screenHeight is " + screenHeight);
                initDeviceSource(uid, pw, screenWidth / 2, screenHeight);
                start();
            }
        });
    }

    private void initView(Context context) {
        inflate(context, R.layout.player_view, this);
        playerParent = findViewById(R.id.playerParent);
        playerViewBean = new PlayerViewBean();
        playerViewBean.setLinearLayout(findViewById(R.id.playerLin));
        playerViewBean.setProgressBar(findViewById(R.id.progress));
        playerViewBean.setSurfaceView(findViewById(R.id.surfaceview));
    }

    private void initDeviceSource(String uid, String pw, int width, int height) {
        deviceBean = new DeviceBean();
        deviceBean.setPlayerId(0);
        deviceBean.setDeviceUid(uid);
        deviceBean.setDevicePwd(pw);
        deviceBean.setDeviceName("admin");
        deviceBean.setPlayerViewBeans(playerViewBean);


        MediaCodec mediaCodec = null;
        MediaFormat mediaFormat;
        try {
            mediaCodec = MediaCodec.createDecoderByType(MediaFormat.MIMETYPE_VIDEO_HEVC);
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("PlayerNative: ", "mediaCodec create error.. " + e.getMessage());
        }
        if (mediaCodec == null) {
            Log.e("PlayerNative: ", "mediaCodec is null.. ");
        }
        Log.e("PlayerNative: ", "mediaCodec is not null.. ");

        mediaFormat = MediaFormat.createVideoFormat(MediaFormat.MIMETYPE_VIDEO_HEVC, width, height);
        deviceBean.setMediaCodec(mediaCodec);
        deviceBean.setMediaFormat(mediaFormat);
        deviceBean.setPlayerAction(new PlayerAction(deviceBean, NativeMediaPlayer.HARDDECODE));

        deviceBean.getPlayerViewBeans().getSurfaceView().getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                Log.e("PlayerNative: ", "surfaceChanged...surfaceChanged.configure.. ");
                try {

                    deviceBean.getMediaCodec().configure(deviceBean.getMediaFormat(), holder.getSurface(), null, 0);
                    deviceBean.getMediaCodec().start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
                Log.e("PlayerNative: ", "surfaceChanged...surfaceChanged.Callback..width is  " + width + " height is " + height);
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                Log.e("PlayerNative: ", "surfaceChanged...surfaceDestroyed.Callback.. ");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    deviceBean.getMediaCodec().reset();
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventResult event) {
        Log.e("PlayerNative: ", "onEventMainThread url is " + event.getRequestUrl() + " code is " + event.getResponseCode());
        if (event.getRequestUrl().equals(DECODE)) {
            if (event.getResponseCode() == 1) {
                deviceBean.getPlayerViewBeans().getProgressBar().setVisibility(View.GONE);
            }
        } else if (event.getRequestUrl().equals(NativeMediaPlayer.CONNECT)) {
            stopPlayer();
            Log.e("PlayerNative: ", "connect faliure!!!");
        }
    }

    private void startPlay() {
        deviceBean.getPlayerAction().setStartRead(true);
        deviceBean.getPlayerViewBeans().getSurfaceView().setVisibility(View.VISIBLE);

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            deviceBean.getPlayerAction().startDeviceConnection();
        }).start();
        deviceBean.getPlayerViewBeans().getProgressBar().setVisibility(View.VISIBLE);
    }

    private void stopPlayer() {
        deviceBean.getPlayerAction().setStartRead(false);
        if (deviceBean.getMediaCodec() != null) {
            try {
                deviceBean.getMediaCodec().stop();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        deviceBean.getPlayerViewBeans().getProgressBar().setVisibility(View.GONE);
        deviceBean.setDecode(false);
        deviceBean.getPlayerViewBeans().getSurfaceView().setVisibility(View.GONE);
    }

    public void start() {
        startPlay();
    }

    public void stop() {
        stopPlayer();
    }

}
