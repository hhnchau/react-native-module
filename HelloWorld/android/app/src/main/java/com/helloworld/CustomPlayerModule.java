package com.helloworld;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

public class CustomPlayerModule extends SimpleViewManager<PlayerView> {
    private static final int STOP = 1;
    private static final int RESUME = 2;

    @NonNull
    @Override
    public String getName() {
        return "CustomPlayerModule";
    }

    @NonNull
    @Override
    protected PlayerView createViewInstance(@NonNull ThemedReactContext reactContext) {

        return new PlayerView(reactContext);
    }


    @ReactProp(name = "uid")
    public void setUid(PlayerView player, @Nullable ReadableMap map) {
        String uid = map.getString("uid");
        String pw = map.getString("pw");
        player.init(uid, pw);

        Log.e("RN_TAG", uid + pw);
    }


    @Override
    public void receiveCommand(@NonNull PlayerView player, String command, @Nullable ReadableArray args) {
        Assertions.assertNotNull(player);
        Assertions.assertNotNull(args);

        Log.e("RN_TAG", command );

        switch (command) {
            case "Stop": {

                return;
            }

            default:
                throw new IllegalArgumentException(String.format(
                        "Unsupported command %d received by %s.",
                        command,
                        getClass().getSimpleName()));
        }

    }

    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        return MapBuilder.of(
                "stop", STOP,
                "resume", RESUME
        );
    }
}
