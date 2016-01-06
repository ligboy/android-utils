package org.ligboy.android.utils;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * View Util
 * @author Ligboy.Liu ligboy@gmail.com.
 */
public final class ViewUtil {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private ViewUtil() {
        throw new IllegalAccessError();
    }

    /**
     *  setBackground compat
     */
    @SuppressWarnings("deprecation")
    public static void setBackground(@NonNull View view, @Nullable Drawable drawable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    /**
     * Generate a value suitable for use in setId(int).
     * <p/>This value will not collide with ID values generated at build time by aapt for R.id.
     */
    @IdRes
    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            for (; ; ) {
                final int result = sNextGeneratedId.get();
                // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }
    }

    /**
     * Sets the text appearance from the specified style resource.
     * <p/>
     *
     * @param resId the resource identifier of the style to apply
     */
    @SuppressWarnings("deprecation")
    public static void setTextAppearance(@NonNull TextView textView, @StyleRes int resId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            textView.setTextAppearance(textView.getContext(), resId);
        } else {
            textView.setTextAppearance(resId);
        }
    }
}
