package com.kikatech.paul.dependencydemo;

import android.app.Application;
import android.content.Context;

import com.kikatech.paul.dynamicplugin.PluginManager;


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PluginManager.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

}
