package com.kikatech.paul.dependencydemo;

import android.app.Application;
import com.kikatech.paul.dynamicplugin.PluginManager;


public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PluginManager.init(this);
    }
}
