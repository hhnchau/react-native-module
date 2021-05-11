package com.helloworld;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.events.RCTEventEmitter;


public class CustomView extends LinearLayout {

    private String message = "";

    public CustomView(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context){
        inflate(context, R.layout.demo, this);
        Button btn = findViewById(R.id.btn);

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Native Toast" + message, Toast.LENGTH_SHORT).show();

                callNativeEvent();
            }
        });


        findViewById(R.id.btn1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                WritableMap event = Arguments.createMap();
                event.putString("name", "Kingpes");
                event.putString("email", "Kingpes@gmail.com");
                event.putInt("age", 40);

                ((ReactContext)getContext()).getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                        .emit("eventA", event);
            }
        });

    }

    public void setMessage(String message) {
        this.message = message;
    }


    public void callNativeEvent(){
        Log.e("RN_TAG", "==================callNativeEvent================");

        WritableMap event = Arguments.createMap();
        event.putString("nativeSendEvent", "Emitted an event");

        ReactContext reactContext = (ReactContext) getContext();
        reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
                getId(),
                "nativeClick",
                event
        );

    }

}
