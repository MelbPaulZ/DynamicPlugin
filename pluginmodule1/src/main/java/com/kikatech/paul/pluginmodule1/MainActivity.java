package com.kikatech.paul.pluginmodule1;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.kikatech.paul.dynamicplugin.LogUtil;
import com.kikatech.paul.dynamicplugin.PluginActivityInterface;

/**
 * @author puzhao
 */
public class MainActivity extends AppCompatActivity implements PluginActivityInterface {

    private AppCompatActivity proxyActivity;

    @Override
    public void onStart() {
        Toast.makeText(proxyActivity.getApplicationContext(), "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRestart() {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
        LogUtil.d("onPause");
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (proxyActivity != null) {
            proxyActivity.setContentView(R.layout.activity_main);
            Class clazz = PluginFragment.class;
            PluginFragment pluginFragment = null;
            try {
                pluginFragment = (PluginFragment) clazz.newInstance();
                proxyActivity.getSupportFragmentManager().beginTransaction().add(R.id.container, pluginFragment).commit();
                LogUtil.d("onCreate");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setProxy(AppCompatActivity proxyActivity, String dexPath) {
        this.proxyActivity = proxyActivity;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onNewIntent(Intent intent) {
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return false;
    }
}
