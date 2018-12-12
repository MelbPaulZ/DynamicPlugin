package com.kikatech.paul.dependencydemo;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.kikatech.paul.dynamicplugin.Plugin;
import com.kikatech.paul.dynamicplugin.PluginManager;

public class TestActivity extends AppCompatActivity {

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
    public Resources getResources() {
        if (pluginManager != null && pluginManager.getResources() != null){
            return pluginManager.getResources();
        }
        return super.getResources();
    }

    @Override
    protected void onCreate(@androidx.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        pluginManager = PluginManager.getInstance();
        versionCode = PLUGIN1_VERSION_CODE;

        findViewById(R.id.plugin1_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plugin plugin = new Plugin(v.getContext(),
                        PLUGIN1_PACKAGE_NAME, versionCode, PLUGIN1_APK_NAME);
                if(pluginManager.addPlugin(plugin)){
                    Toast.makeText(v.getContext(), "add plugin1 successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(v.getContext(), "add plugin1 failed", Toast.LENGTH_SHORT).show();
                }
                versionCode += ".1";
            }
        });

        findViewById(R.id.remove_plugin1_btn).setOnClickListener(new View.OnClickListener() {
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
        findViewById(R.id.plugin2_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Plugin plugin = new Plugin(v.getContext(),
                        PLUGIN2_PACKAGE_NAME, PLUGIN2_VERSION_CODE, PLUGIN2_APK_NAME);
                if(pluginManager.addPlugin(plugin)){
                    Toast.makeText(v.getContext(), "add plugin2 successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(v.getContext(), "add plugin2 failed", Toast.LENGTH_SHORT).show();
                }
                versionCode2 += ".2";
            }
        });

        findViewById(R.id.remove_plugin2_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pluginManager.removePlugin(PLUGIN2_APK_NAME)){
                    Toast.makeText(v.getContext(), "remove plugin2 successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(v.getContext(), "remove plugin2 failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.load_fragment1).setOnClickListener(new View.OnClickListener() {
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

        findViewById(R.id.clean_fragment_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Fragment fragment: getSupportFragmentManager().getFragments()){
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
                Toast.makeText(v.getContext(), "Clean all fragment", Toast.LENGTH_SHORT).show();
            }
        });



    }

}
