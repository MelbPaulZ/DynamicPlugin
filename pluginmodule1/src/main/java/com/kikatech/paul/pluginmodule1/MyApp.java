package com.kikatech.paul.pluginmodule1;

import android.app.Application;
import android.content.Context;
import com.kikatech.paul.dynamicplugin.PluginManager;


public class MyApp extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.init(base);
    }

}
