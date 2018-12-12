package com.kikatech.paul.dynamicplugin;

public interface PluginListener {
    void onPluginLoaded(String pluginName);
    void onPluginReloaded(String pluginName, String versionCode);
    void onPluginRemoved(String pluginName);
}
