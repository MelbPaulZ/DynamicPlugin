package com.kikatech.paul.dependencydemo;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;

import com.kikatech.paul.dynamicplugin.PluginManager;

/**
 * @author puzhao
 */
public class BaseActivity extends AppCompatActivity {

    PluginManager pluginManager;
    @Override
    public Resources getResources() {
        if (pluginManager == null){
            pluginManager = PluginManager.getInstance();
        }
        if (pluginManager.getResources() != null){
            return pluginManager.getResources();
        }
        return super.getResources();
    }
}
