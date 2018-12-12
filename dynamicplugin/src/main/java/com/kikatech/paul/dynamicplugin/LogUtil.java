package com.kikatech.paul.dynamicplugin;

import android.util.Log;

/**
 * @author puzhao
 */
public abstract class LogUtil {
    private final static String TAG = "LogUtil";
    private final static boolean DEBUG = true;

    public static void d(String msg){
        if (DEBUG){
            d(TAG, msg);
        }
    }

    public static void d(String title, String msg){
        if (DEBUG){
            Log.d(title, msg);
        }
    }

    public static void e(String msg){
        e(TAG, msg);
    }

    public static void e(String title, String msg){
        Log.e(title, msg);
    }


}
