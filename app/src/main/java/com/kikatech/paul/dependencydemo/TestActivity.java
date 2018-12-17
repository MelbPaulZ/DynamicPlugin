package com.kikatech.paul.dependencydemo;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.kikatech.paul.dynamicplugin.Plugin;
import com.kikatech.paul.dynamicplugin.PluginManager;

import java.io.IOException;

/**
 * @author puzhao
 */
public class TestActivity extends BaseActivity {

    PluginManager pluginManager;

    private final static String PLUGIN1_PACKAGE_NAME = "com.kikatech.paul.pluginmodule1";
    private final static String PLUGIN1_APK_NAME = "pluginmodule1-release.apk";
    private final static String PLUGIN1_VERSION_CODE = "0.2";
    private String versionCode = "";

    private final static String PLUGIN2_PACKAGE_NAME = "com.kikatech.paul.pluginmodule2";
    private final static String PLUGIN2_APK_NAME = "pluginmodule2-release.apk";
    private final static String PLUGIN2_VERSION_CODE = "1.0";
    private String versionCode2 = "";


    @Override
    protected void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initTest();
    }

    private void initTest(){
        pluginManager = PluginManager.getInstance();
        versionCode = PLUGIN1_VERSION_CODE;

        findViewById(R.id.plugin1_btn)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plugin plugin = null;
                try {
                    plugin = new Plugin(v.getContext(),
                            PLUGIN1_PACKAGE_NAME, versionCode, PLUGIN1_APK_NAME);
                    if(pluginManager.addPlugin(plugin)){
                        Toast.makeText(v.getContext(), "add plugin1 successfully", Toast.LENGTH_SHORT).show();
                        versionCode += ".1";
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Toast.makeText(v.getContext(), "add plugin1 failed", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.remove_plugin1_btn)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pluginManager.removePlugin(PLUGIN1_APK_NAME)){
                    Toast.makeText(v.getContext(), "remove plugin1 successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(v.getContext(), "remove plugin1 failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        versionCode2 = PLUGIN2_VERSION_CODE;
        findViewById(R.id.plugin2_btn)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plugin plugin = null;
                try {
                    plugin = new Plugin(v.getContext(),
                            PLUGIN2_PACKAGE_NAME, versionCode2, PLUGIN2_APK_NAME);
                    if(pluginManager.addPlugin(plugin)){
                        Toast.makeText(v.getContext(), "add plugin2 successfully", Toast.LENGTH_SHORT).show();
                        versionCode2 += ".2";
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                Toast.makeText(v.getContext(), "add plugin2 failed", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.remove_plugin2_btn)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pluginManager.removePlugin(PLUGIN2_APK_NAME)){
                    Toast.makeText(v.getContext(), "remove plugin2 successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(v.getContext(), "remove plugin2 failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.load_fragment1)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = false;
                try {
                    Class clazz = pluginManager.loadClass("com.kikatech.paul.pluginmodule1.PluginFragment");
                    Fragment fragment = (Fragment) clazz.newInstance();
                    getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
                    isSuccess = true;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }

                if (isSuccess){
                    Toast.makeText(v.getContext(), "Load fragment1 successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(v.getContext(), "Load fragment1 failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.load_fragment2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSuccess = false;
                try {
                    Class clazz = pluginManager.loadClass("com.kikatech.paul.pluginmodule2.TestFragment");
                    Fragment fragment = (Fragment) clazz.newInstance();
                    getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
                    isSuccess = true;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }

                if (isSuccess){
                    Toast.makeText(v.getContext(), "Load fragment2 successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(v.getContext(), "Load fragment2 failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.clean_fragment_btn)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Fragment fragment: getSupportFragmentManager().getFragments()){
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
                Toast.makeText(v.getContext(), "Clean all fragment", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.load_remote_activity_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, ProxyActivity.class);
                startActivity(intent);
//                try {
//                    Class clazz = getClassLoader().loadClass("com.kikatech.paul.pluginmodule1.ProxyActivity");
//                    Intent intent = new Intent(TestActivity.this, clazz);
//                    startActivity(intent);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    @Override
    public ClassLoader getClassLoader() {
        if (pluginManager != null && pluginManager.getPluginsDexClassLoader() != null){
            return pluginManager.getPluginsDexClassLoader();
        }
        return super.getClassLoader();
    }

    @Override
    public Resources getResources() {
        if (pluginManager != null && pluginManager.getResources()!=null){
            return pluginManager.getResources();
        }
        return super.getResources();
    }
}
