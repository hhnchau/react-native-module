package com.helloworld;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.IllegalViewOperationException;
import com.facebook.react.uimanager.NativeViewHierarchyManager;
import com.facebook.react.uimanager.UIBlock;
import com.facebook.react.uimanager.UIManagerModule;

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

    @ReactMethod
    public void doCallbackTask(int aNumber, Callback successCallback, Callback failedCallback) {
        try {
            if (aNumber == 100) {
                throw new Exception("");
            }
            String name = "KP";
            String email = "kingpes@gmail.com";
            int age = 39;
            successCallback.invoke(name, email, age);
        } catch (Exception e) {
            failedCallback.invoke(e.getMessage());
        }
    }

    @ReactMethod
    public void doPromiseTask(int aNumber, Promise promise){
        try {
            WritableMap map = Arguments.createMap();
            map.putString("name", "Kingpes");
            map.putString("email", "kingpes@gmail.com");
            map.putInt("age", 50);
            promise.resolve(map);
        } catch (Exception e) {
            promise.reject("1","A error");
        }

    }


    @NonNull
    @Override
    public String getName() {
        return "CustomModule";
    }
}
