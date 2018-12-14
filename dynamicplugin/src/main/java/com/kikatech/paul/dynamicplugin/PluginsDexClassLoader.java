package com.kikatech.paul.dynamicplugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import dalvik.system.DexClassLoader;

/**
 * @author puzhao
 */
public class PluginsDexClassLoader extends ClassLoader {
    public Map<String, DexClassLoader> pluginNameToPluginDexLoaderMap;


    public PluginsDexClassLoader(ClassLoader parent) {
        super(parent);
        this.pluginNameToPluginDexLoaderMap = new HashMap<>();
    }

    public synchronized DexClassLoader addOrReplaceDexClassLoader(String pluginName, DexClassLoader dexClassLoader){
        return pluginNameToPluginDexLoaderMap.put(pluginName, dexClassLoader);
    }

    public synchronized DexClassLoader removeDexClassLoader(String pluginName){
        return pluginNameToPluginDexLoaderMap.remove(pluginName);
    }

    public synchronized void removeAllPluginDexLoader(){
        pluginNameToPluginDexLoaderMap.clear();
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        synchronized (this){
            Iterator<DexClassLoader> dexClassLoaderIterator = pluginNameToPluginDexLoaderMap.values().iterator();
            while (dexClassLoaderIterator.hasNext()){
                DexClassLoader currentDexClassLoader = dexClassLoaderIterator.next();
                Class clazz = null;
                try {
                    clazz = currentDexClassLoader.loadClass(name);
                    if (clazz != null){
                        return clazz;
                    }
                }catch (ClassNotFoundException ignore){
                }
            }
        }
        return super.loadClass(name);
    }
}
