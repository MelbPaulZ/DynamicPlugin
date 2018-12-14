package com.kikatech.paul.dependencydemo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.kikatech.paul.dynamicplugin.PluginActivityInterface;
import com.kikatech.paul.dynamicplugin.PluginManager;


/**
 * @author puzhao
 */
public class ProxyActivity extends BaseActivity {
    private final static String TAG = "ProxyActivity";
    private PluginActivityInterface realActivity;

    @Override
    protected void onStart() {
        super.onStart();
        if (realActivity != null){
            realActivity.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (realActivity != null){
            realActivity.onRestart();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (realActivity != null){
            onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (realActivity != null){
            realActivity.onRestart();
        }
    }

    @Override
    protected void onPause() {
        if (realActivity != null){
            realActivity.onResume();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (realActivity != null){
            realActivity.onStop();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (realActivity != null){
            realActivity.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            PluginManager pluginManager = PluginManager.getInstance();
            Class clazz = pluginManager.loadClass("com.kikatech.paul.pluginmodule1.MainActivity");
            PluginActivityInterface remoteActivity = (PluginActivityInterface) clazz.newInstance();
            remoteActivity.setProxy(this, "");
            this.realActivity = remoteActivity;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        if (realActivity != null){
            realActivity.onCreate(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (realActivity != null){
            realActivity.onSaveInstanceState(outState);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (realActivity != null){
            realActivity.onNewIntent(intent);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (realActivity != null){
            realActivity.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (realActivity != null){
            realActivity.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (realActivity != null){
            realActivity.onKeyUp(keyCode, event);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams params) {
        super.onWindowAttributesChanged(params);
        if (realActivity != null){
            realActivity.onWindowAttributesChanged(params);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (realActivity != null){
            realActivity.onWindowFocusChanged(hasFocus);
        }
    }
}

