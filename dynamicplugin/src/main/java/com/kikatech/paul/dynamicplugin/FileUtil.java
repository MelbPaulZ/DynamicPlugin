package com.kikatech.paul.dynamicplugin;

/**
 * @author puzhao
 */
public class FileUtil {
    public static boolean ExistSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

}
