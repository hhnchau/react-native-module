package com.helloworld;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

public class CustomPlayerModule extends SimpleViewManager<PlayerView> {
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
}
