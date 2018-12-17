package com.kikatech.paul.pluginmodule1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.kikatech.paul.dynamicplugin.PluginActivityInterface;

/**
 * @author puzhao
 */
public class ProxyActivity extends AppCompatActivity {

    PluginActivityInterface realActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        try {
//            Class clazz = getClassLoader().loadClass("com.kikatech.paul.pluginmodule1.MainActivity");
//            realActivity = (PluginActivityInterface) clazz.newInstance();
//            realActivity.setProxy(this,"");
//            realActivity.onCreate(savedInstanceState);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        }
        setContentView(R.layout.activity_proxy);
        Fragment pluginFragment = new PluginFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container, pluginFragment).commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (realActivity != null){
            realActivity.onStart();
        }
    }
}
