package com.kikatech.paul.dynamicplugin;

import android.app.Instrumentation;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PluginHooker {

    public static void hookInstrumentation(Context context){
        try {
            Class activityThreadClass = context.getClassLoader().loadClass("android.app.ActivityThread");
            Method activityThreadGetMethod = activityThreadClass.getMethod("currentActivityThread");
            Object activityThreadObject = activityThreadGetMethod.invoke(activityThreadClass);
            Field activityThreadInstrumentField = activityThreadClass.getDeclaredField("mInstrumentation");
            activityThreadInstrumentField.setAccessible(true);
            Object activityThreadInstrument = activityThreadInstrumentField.get(activityThreadObject);

            PluginActivityInstrumentation pluginActivityInstrumentation = new PluginActivityInstrumentation((Instrumentation) activityThreadInstrument);
            activityThreadInstrumentField.set(activityThreadObject, pluginActivityInstrumentation);
            Log.d("","");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}
