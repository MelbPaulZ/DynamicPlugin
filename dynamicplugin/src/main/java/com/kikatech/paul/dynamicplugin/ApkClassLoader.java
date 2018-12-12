package com.kikatech.paul.dynamicplugin;

import dalvik.system.DexClassLoader;

/**
 * @author puzhao
 */
public class ApkClassLoader extends DexClassLoader {
    public ApkClassLoader(String dexPath, String optimizedDirectory, String librarySearchPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, librarySearchPath, parent);
    }


}
