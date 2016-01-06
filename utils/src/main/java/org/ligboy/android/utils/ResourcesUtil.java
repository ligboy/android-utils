package org.ligboy.android.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION_CODES;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Resource Util
 * @author Ligboy.Liu ligboy@gmail.com.
 */
public final class ResourcesUtil {

    private ResourcesUtil() {
        throw new IllegalAccessError();
    }

    /**
     * Return a color value associated with a particular attribute ID.
     *
     * @param context Context used for getting the dimension.
     * @param attribute    theme attribute.
     * @param defaultColor Value to return if the attribute is not defined or not a resource.
     * @param theme theme
     * @return color value
     */
    @ColorInt
    public static int getAttributeColor(Context context, @AttrRes int attribute,
                                        @ColorInt int defaultColor, @Nullable Theme theme) {
        int themeColor;
        if (theme == null) {
            theme = context.getTheme();
        }
        if (theme != null) {
            try {
                TypedArray ta = theme.obtainStyledAttributes(new int[]{attribute});
                themeColor = ta.getColor(0, defaultColor);
                ta.recycle();
                return themeColor;
            } catch (NotFoundException ignored) {
            }
        }
        return defaultColor;
    }


    /**
     * Return a dimensional attribute.
     * Unit conversions are based on the current {@link DisplayMetrics}
     * associated with the resources.
     * <p>
     *
     * @param context Context used for getting the dimension.
     * @param attrId theme attribute.
     * @param defValue Value to return if the attribute is not defined or not a resource.
     * @param theme theme
     * @return Attribute dimension value multiplied by the appropriate metric, or defValue if not defined.
     */
    public static float getAttributeDimension(final Context context, @AttrRes int attrId,
                                              float defValue, @Nullable Theme theme) {
        float dimension = defValue;
        if (theme == null) {
            theme = context.getTheme();
        }
        if (theme != null) {
            try {
                TypedArray ta = theme.obtainStyledAttributes(new int[]{attrId});
                dimension = ta.getDimension(0, defValue);
                ta.recycle();
            } catch (NotFoundException ignored) {
            }
        }
        return dimension;
    }

    /**
     * Return a dimensional attribute for use as an offset in raw pixels.  This is the same as
     * {@link #getAttributeDimension}, except the returned value is converted to
     * integer pixels for use as a size.  A size conversion involves
     * rounding the base value, and ensuring that a non-zero base value
     * is at least one pixel in size.
     * <p>
     * This method will throw an exception if the attribute is defined but is
     * not a dimension.
     * 获取特定主题的指定attribute的像素尺寸<p>
     * 该返回像素值最小不可能为0
     *
     * @param context Context used for getting the dimension.
     * @param attrId theme attribute.
     * @param defValue Value to return if the attribute is not defined or not a resource.
     * @param theme theme
     * @return Attribute dimension value multiplied by the appropriate metric and truncated
     *         to integer pixels, or defValue if not defined.
     */
    public static int getAttributeDimensionPixelSize(final Context context, @AttrRes int attrId,
                                                     int defValue, @Nullable Theme theme) {
        int dimension = defValue;
        if (theme == null) {
            theme = context.getTheme();
        }
        if (theme != null) {
            try {
                TypedArray ta = theme.obtainStyledAttributes(new int[]{attrId});
                dimension = ta.getDimensionPixelSize(0, defValue);
                ta.recycle();
            } catch (NotFoundException ignored) {
            }
        }
        return dimension;
    }

    /**
     * Return a dimensional attribute for use as an offset in raw pixels.
     * This is the same as {@link #getAttributeDimension},
     * except the returned value is converted to integer pixels for you.
     * An offset conversion involves simply truncating the base value to an integer.
     *
     * @param context Context used for getting the color.
     * @param attrId theme attribute.
     * @param defValue Value to return if the attribute is not defined or not a resource.
     * @param theme theme
     * @return Attribute dimension value multiplied by the appropriate metric and
     *         truncated to integer pixels, or defValue if not defined.
     */
    public static int getAttributeDimensionPixelOffset(final Context context, @AttrRes int attrId,
                                                       int defValue, @Nullable Theme theme) {
        int dimension = defValue;
        if (theme == null) {
            theme = context.getTheme();
        }
        if (theme != null) {
            try {
                TypedArray ta = theme.obtainStyledAttributes(new int[]{attrId});
                dimension = ta.getDimensionPixelOffset(0, defValue);
                ta.recycle();
            } catch (NotFoundException ignored) {
            }
        }
        return dimension;
    }

    /**
     * Return a drawable object associated with a particular resource ID and
     * styled for the specified theme. Various types of objects will be
     * returned depending on the underlying resource -- for example, a solid
     * color, PNG image, scalable image, etc.
     * <p>
     * Prior to API level 21, the theme will not be applied and this method
     * simply calls through to {@link Resources#getDrawable(int)}.
     *
     * @param id The desired resource identifier, as generated by the aapt
     *           tool. This integer encodes the package, type, and resource
     *           entry. The value 0 is an invalid identifier.
     * @param theme The theme used to style the drawable attributes, may be
     *              {@code null}.
     * @return Drawable An object that can be used to draw this resource.
     * @throws NotFoundException Throws NotFoundException if the given ID does
     *         not exist.
     */
    @SuppressWarnings("deprecation")
    @Nullable
    public static Drawable getDrawable(@NonNull final Resources resources, @DrawableRes int id,
                                       @Nullable Resources.Theme theme) throws NotFoundException {
        Drawable drawable;
        if (SDK_INT >= VERSION_CODES.LOLLIPOP) {
            drawable = resources.getDrawable(id, theme);
        } else {
            drawable = resources.getDrawable(id);
        }
        return drawable;
    }


    /**
     * Return a drawable object associated with a particular resource ID for
     * the given screen density in DPI and styled for the specified theme.
     * <p>
     * Prior to API level 15, the theme and density will not be applied and
     * this method simply calls through to {@link Resources#getDrawable(int)}.
     * <p>
     * Prior to API level 21, the theme will not be applied and this method
     * calls through to Resources#getDrawableForDensity(int, int).
     *
     * @param id The desired resource identifier, as generated by the aapt
     *           tool. This integer encodes the package, type, and resource
     *           entry. The value 0 is an invalid identifier.
     * @param density The desired screen density indicated by the resource as
     *                found in {@link android.util.DisplayMetrics}.
     * @param theme The theme used to style the drawable attributes, may be
     *              {@code null}.
     * @return Drawable An object that can be used to draw this resource.
     * @throws NotFoundException Throws NotFoundException if the given ID does
     *         not exist.
     */
    @Nullable
    @SuppressWarnings("deprecation")
    public static Drawable getDrawableForDensity(@NonNull Resources res,
                                                 @DrawableRes int id, int density,
                                                 @Nullable Theme theme) throws NotFoundException {
        if (SDK_INT >= VERSION_CODES.LOLLIPOP) {
            return res.getDrawableForDensity(id, density, theme);
        } else if (SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            return res.getDrawableForDensity(id, density);
        } else {
            return res.getDrawable(id);
        }
    }

    /**
     * Returns a themed color integer associated with a particular resource ID.
     * If the resource holds a complex {@link ColorStateList}, then the default
     * color from the set is returned.
     * <p>
     * Prior to API level 23, the theme will not be applied and this method
     * calls through to {@link Resources#getColor(int)}.
     *
     * @param id The desired resource identifier, as generated by the aapt
     *           tool. This integer encodes the package, type, and resource
     *           entry. The value 0 is an invalid identifier.
     * @param theme The theme used to style the color attributes, may be
     *              {@code null}.
     * @return A single color value in the form {@code 0xAARRGGBB}.
     * @throws NotFoundException Throws NotFoundException if the given ID does
     *         not exist.
     */
    @ColorInt
    @SuppressWarnings("deprecation")
    public int getColor(@NonNull Resources res, @ColorRes int id, @Nullable Theme theme)
            throws NotFoundException {
        if (SDK_INT >= VERSION_CODES.M) {
            return res.getColor(id, theme);
        } else {
            return res.getColor(id);
        }
    }

    /**
     * Returns a themed color state list associated with a particular resource
     * ID. The resource may contain either a single raw color value or a
     * complex {@link ColorStateList} holding multiple possible colors.
     *
     * @param id    The desired resource identifier of a {@link ColorStateList},
     *              as generated by the aapt tool. This integer encodes the
     *              package, type, and resource entry. The value 0 is an invalid
     *              identifier.
     * @param theme The theme used to style the color attributes, may be
     *              {@code null}.
     * @return A themed ColorStateList object containing either a single solid
     * color or multiple colors that can be selected based on a state.
     * @throws NotFoundException Throws NotFoundException if the given ID does
     *                                     not exist.
     */
    @SuppressWarnings("deprecation")
    @Nullable
    public ColorStateList getColorStateList(@NonNull Context context, @ColorRes int id,
                                            @Nullable Resources.Theme theme) throws NotFoundException {
        if (SDK_INT < VERSION_CODES.M) {
            return context.getResources().getColorStateList(id);
        } else {
            return context.getResources().getColorStateList(id, theme);
        }
    }
}
