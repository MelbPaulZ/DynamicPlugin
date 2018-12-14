package com.kikatech.paul.dynamicplugin;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;

/**
 * @author puzhao
 */
public class PluginActivityInstrumentation extends Instrumentation {
    private Instrumentation instrumentation;

    public PluginActivityInstrumentation(Instrumentation instrumentation) {
        this.instrumentation = instrumentation;
    }

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        PluginManager pluginManager = PluginManager.getInstance();
        Activity activity = null;
        if (pluginManager != null && pluginManager.getPluginsDexClassLoader() != null){
            try {
                activity = super.newActivity(pluginManager.getPluginsDexClassLoader(), className, intent);
            }catch (ClassNotFoundException ignore){

            }catch (IllegalAccessException ignore){

            }catch (InstantiationException ignore){

            }
            if (activity != null){
                return activity;
            }
        }
        return super.newActivity(cl, className, intent);
    }
}
