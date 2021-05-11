package com.helloworld;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class CustomModule extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;

    public CustomModule(ReactApplicationContext context) {
        super(context);
        reactContext =context;
    }

    @ReactMethod
    public void show(){
        Toast.makeText(reactContext, "Hello React Native", Toast.LENGTH_SHORT).show();
    }

    @ReactMethod
    public String getValue(){
        return "HELLO";
    }


    @NonNull
    @Override
    public String getName() {
        return "CustomModule";
    }
}
