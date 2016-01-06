package org.ligboy.android.utils;

import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * @author Ligboy.Liu ligboy@gmail.com.
 */
public final class SystemUtil {

    /**
     * An ordered list of ABIs supported by this device.
     * The most preferred ABI is the first element in the list. See
     * @return The list of ABIs.
     */
    @SuppressWarnings("deprecation")
    @NonNull
    public static String[] getSupportedAbis() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (TextUtils.isEmpty(Build.CPU_ABI2)) {
                return new String[] {Build.CPU_ABI};
            } else {
                return new String[] {Build.CPU_ABI, Build.CPU_ABI2};
            }
        } else {
            return Build.SUPPORTED_ABIS;
        }
    }
}
