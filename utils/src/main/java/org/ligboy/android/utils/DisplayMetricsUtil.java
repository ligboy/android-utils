package org.ligboy.android.utils;

import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

/**
 * @author Ligboy.Liu ligboy@gmail.com.
 */
public final class DisplayMetricsUtil {

    /**
     * Converts dip dimension to its final value as an integer pixel size.
     * <p/>This is the same as {@link #dip2PixelOffset(DisplayMetrics, float)}, except ensuring that a
     * non-zero base value is at least one pixel in size.
     * @param displayMetrics Resources used.
     * @param dip dip dimension.
     * @return The number of pixels.
     */
    public static int dip2Pixel(@NonNull final DisplayMetrics displayMetrics, @FloatRange(from = 0f) float dip) {
        float density = displayMetrics.density;
        final float value = (dip * density);
        int res = (int) (value + 0.5f);
        if (res != 0) return res;
        if (value == 0) return 0;
        if (value > 0) return 1;
        return -1;
    }

    /**
     * Converts dip dimension to its final value as an integer pixel size.
     * @param displayMetrics DisplayMetrics used.
     * @param dip
     * @return
     */
    public static int dip2PixelOffset(@NonNull final DisplayMetrics displayMetrics, @FloatRange(from = 0f) float dip) {
        float density = displayMetrics.density;
        return (int) (dip * density);
    }

    /**
     * Converts pixel dimension to its final value as an float dip size.
     * @param displayMetrics DisplayMetrics used.
     * @param pixel floating point value on dip unit.
     * @return
     */
    public static float pixel2Dip(@NonNull final DisplayMetrics displayMetrics, @IntRange(from = 0) int pixel) {
        float density = displayMetrics.density;
        return (pixel / density);
    }
}
