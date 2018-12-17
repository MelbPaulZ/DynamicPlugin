package com.kikatech.paul.dynamicplugin;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.view.ContextThemeWrapper;

import java.lang.reflect.Field;


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
                if (pluginManager.isClassInPlugin(className)){
                    Class superClass = activity.getClass();
                    // find super class of activity
                    while (!superClass.getName().equals(ContextThemeWrapper.class.getName())){
                        superClass = superClass.getSuperclass();
                    }
                    Field field = superClass.getDeclaredField("mResources");
                    field.setAccessible(true);
                    field.set(activity, pluginManager.getResources());
                }
            }catch (ClassNotFoundException ignore){

            }catch (IllegalAccessException ignore){

            }catch (InstantiationException ignore){

            } catch (NoSuchFieldException ignore) {

            }
            if (activity != null){
                return activity;
            }
        }
        return instrumentation.newActivity(cl, className, intent);
    }
}
