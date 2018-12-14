package com.kikatech.paul.dynamicplugin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import dalvik.system.DexClassLoader;


/**
 * @author puzhao
 */
public class Plugin {
    private Context context;
    private String packageName;
    private String versionCode;
    private String pluginApkName;
    private DexClassLoader dexClassLoader;
    private File apkFile;
    private String filePath;
    private AssetManager assetManager;
    private Resources pluginResources;

    public Plugin(Context context, String packageName, String versionCode, String pluginApkName) throws IOException, ClassNotFoundException {
        this.context = context;
        this.packageName = packageName;
        this.versionCode = versionCode;
        this.pluginApkName = pluginApkName;
        init();
    }

    public Class<?> loadClass(String className) throws ClassNotFoundException {
        if (dexClassLoader == null) {
            LogUtil.e("plug class loader is null, cannot load class");
            throw new RuntimeException("plug class loader is null, cannot load class");
        }
        return dexClassLoader.loadClass(className);
    }

    private void init() throws ClassNotFoundException, IOException {
        if (FileUtil.ExistSDCard()) {
            filePath = context.getExternalCacheDir() + File.separator + pluginApkName;
        }else{
            filePath = context.getCacheDir() + File.separator + pluginApkName;
        }
        apkFile = new File(filePath);
        if (apkFile.exists()) {
            createAssetManagerAndPluginResources();
            initClassLoader();

        } else {
            createFileFromFilePath(filePath);
            createAssetManagerAndPluginResources();
            initClassLoader();
        }
    }

    private void createAssetManagerAndPluginResources() throws ClassNotFoundException {
        assetManager = PluginResources.getPluginAssetManager(apkFile);
        pluginResources = PluginResources.getPluginResources(context.getResources(), assetManager);
    }

    private void initClassLoader() {
        if (apkFile == null) {
            LogUtil.e("apkFile is null, cannot init class loader");
            return;
        }
        String apkFilePath = apkFile.getAbsolutePath();
        File optimizedDirectoryFile = context.getDir(pluginApkName, Context.MODE_PRIVATE);
        if (optimizedDirectoryFile == null) {
            LogUtil.e("Optimized file is null, cannot init class loader");
            return;
        }
        String optimizeDirectory = optimizedDirectoryFile.getAbsolutePath();
        dexClassLoader = new DexClassLoader(apkFilePath, optimizeDirectory, null, context.getClassLoader());
    }

    private void createFileFromFilePath(String filePath) throws IOException {
        InputStream is = new FileInputStream(apkFile);
        FileOutputStream os = new FileOutputStream(filePath);
        int len = 0;
        byte[] buffer = new byte[1024];
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        os.close();
        is.close();
    }

    public String getPackageName() {
        return packageName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public String getPluginApkName() {
        return pluginApkName;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public File getPluginApk(){
        return apkFile;
    }

    public Resources getResources() {
        return pluginResources;
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }
}
