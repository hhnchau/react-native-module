package com.helloworld;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.text.ReactTextView;

import java.util.Map;

public class CustomViewModule extends SimpleViewManager<CustomView> {

    @NonNull
    @Override
    public String getName() {
        return "CustomViewModule";
    }

    @NonNull
    @Override
    protected CustomView createViewInstance(@NonNull ThemedReactContext reactContext) {
        Log.e("RN_TAG", "==================createViewInstance================");
        return new CustomView(reactContext);
    }


    @ReactProp(name = "message")
    public void setMessage(CustomView view, @Nullable String message) {
        Log.e("RN_TAG", "ANDROID_SAMPLE_UI");
        view.setMessage(message);
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        Log.e("RN_TAG", "==================Register Native Click================");
        return MapBuilder.<String, Object>builder().put("nativeClick", MapBuilder.of("registrationName", "onClick")).build();
    }

}
