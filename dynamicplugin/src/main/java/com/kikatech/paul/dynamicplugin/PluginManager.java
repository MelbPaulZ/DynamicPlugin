package com.kikatech.paul.dynamicplugin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.support.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author puzhao
 */
public class PluginManager implements PluginListener {

    private final static String TAG = "PluginManager";
    private Context context;
    private Map<String, Plugin> pluginApkNameToPluginMap;
    private volatile static boolean isInit = false;
    private static PluginManager instance;
    private Resources pluginTotalResources;

    private PluginManager(Context context) {
        this.context = context;
        pluginApkNameToPluginMap = new HashMap<>();
    }

    public synchronized static void init(Context context) {
        if (isInit) {
            throw new RuntimeException("PluginManager has already inited");
        }
        instance = new PluginManager(context);
        isInit = true;
    }

    public static PluginManager getInstance() {
        if (!isInit) {
            throw new RuntimeException("PluginManager has not been init");
        }
        return instance;
    }

    /**
     * @param plugin if old plugin version code is not equals to the new plugin version code
     *               the new plugin will replace the old plugin.
     * @return return false if the plugin is already in @{{@link #pluginApkNameToPluginMap}}
     */
    public synchronized boolean addPlugin(Plugin plugin) {
        String pluginApkName = plugin.getPluginApkName();
        if (pluginApkNameToPluginMap.containsKey(pluginApkName)) {
            Plugin originPlugin = pluginApkNameToPluginMap.get(pluginApkName);
            if (originPlugin != null) {
                String originVersionCode = originPlugin.getVersionCode();
                if (!originVersionCode.equals(plugin.getVersionCode())) {
                    pluginApkNameToPluginMap.put(plugin.getPluginApkName(), plugin);
                    onPluginReloaded(pluginApkName, plugin.getVersionCode());
                    return true;
                }
            }
        } else {
            pluginApkNameToPluginMap.put(pluginApkName, plugin);
            onPluginLoaded(pluginApkName);
            return true;
        }
        return false;
    }

    public synchronized boolean removePlugin(String name) {
        Plugin removedPlugin = pluginApkNameToPluginMap.remove(name);
        if (removedPlugin == null) {
            LogUtil.e(TAG, "Plugin with name " + name + " is not existed, no need to remove");
            return false;
        }
        onPluginRemoved(name);
        return true;
    }

    public Class loadClass(String className) throws ClassNotFoundException {
        Iterator<Plugin> pluginIterators = pluginApkNameToPluginMap.values().iterator();
        Class clazz = null;
        while (pluginIterators.hasNext()) {
            Plugin plugin = pluginIterators.next();
            try {
                clazz = plugin.loadClass(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (clazz != null) {
                return clazz;
            }
        }
        throw new ClassNotFoundException("Find all plugins but unable to load class : " + className);
    }

    @Nullable
    public Resources getResources() {
        return pluginTotalResources;
    }

    /**
     * Must use getMethods and
     */
    private synchronized void updatePluginResources() {
        Iterator<Plugin> pluginIterators = pluginApkNameToPluginMap.values().iterator();
        if (!pluginIterators.hasNext()) {
            pluginTotalResources = context.getResources();
            return;
        }
        AssetManager currentAssetManager = context.getResources().getAssets();
        try {
            Method[] methods = currentAssetManager.getClass().getMethods();
            for (Method method: methods){
                if (method.getName().equals("addAssetPath")){
                    while (pluginIterators.hasNext()){
                        Plugin plugin = pluginIterators.next();
                        method.invoke(currentAssetManager, plugin.getPluginApk().getAbsolutePath());
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        pluginTotalResources = PluginResources.getPluginResources(context.getResources(), currentAssetManager);
    }


    @Override
    public void onPluginLoaded(String pluginName) {
        LogUtil.d(TAG, "new plugin loaded : " + pluginName);
        updatePluginResources();
    }

    @Override
    public void onPluginReloaded(String pluginName, String versionCode) {
        LogUtil.d(TAG, "plugin is reloaded : " + pluginName + " " + versionCode);
        updatePluginResources();
    }

    @Override
    public void onPluginRemoved(String pluginName) {
        LogUtil.d(TAG, "plugin is removed : " + pluginName);
        updatePluginResources();
    }
}
