package org.ligboy.android.utils;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Log util
 * <p>Remove logging by proguard</p>
 * <pre color="blue">
 * #Remove logging
 * -assumenosideeffects class org.ligboy.android.utils.LogUtil {
 *     public static void v(...);
 *     public static void i(...);
 *     public static void w(...);
 *     public static void d(...);
 *     public static void e(...);
 * }
 * </pre>
 * @author Ligboy.Liu ligboy@gmail.com.
 */
public final class LogUtil {

    private static final int MAX_LOG_TAG_LENGTH = 23;

    private static boolean sInitialized;
    private static boolean sDebug;
    private static String sTagPrefix;
    private static int sTagPrefixLength;

    private LogUtil() {
        throw new IllegalAccessError();
    }

    /**
     * Initialize the log util once.
     * <p/>Call this in {@link Application#onCreate()}
     * @param debug debuggable. Usually, It's BuildConfig.DEBUG.
     * @param prefix The prefix of log tag.
     */
    public static void initialize(boolean debug, @Nullable String prefix) {
        if (sInitialized) {
            return;
        }
        sDebug = debug;
        sTagPrefix = prefix;
        sTagPrefixLength = prefix != null ? prefix.length() : 0;
        sInitialized = true;
    }

    /**
     * Initialize the log util once.
     * <p/>Call this in {@link Application#onCreate()}
     * @param debug debuggable. Usually, It's BuildConfig.DEBUG.
     */
    public static void initialize(boolean debug) {
        initialize(debug, null);
    }

    /**
     * Initialize the log util once.
     * @param context Context
     * @param prefix The prefix of log tag.
     */
    public static void initialize(@NonNull Context context, @Nullable String prefix) {
        if (sInitialized) {
            return;
        }
        boolean debug = false;
        String packageName = context.getPackageName();
        String buildConfigClass = packageName + ".BuildConfig";
        try {
            Field buildConfigField = Class.forName(buildConfigClass).getDeclaredField("DEBUG");
            debug = buildConfigField.getBoolean(null);
        } catch (Exception ignored) {
        }
        initialize(debug, prefix);
    }

    /**
     * Initialize the log util once.
     * @param context Context
     */
    public static void initialize(@NonNull Context context) {
        initialize(context, null);
    }

    /**
     * Make the log tag
     * @param tag TAG
     * @return The Generated tag.
     */
    public static String makeLogTag(String tag) {
        if (tag.length() > MAX_LOG_TAG_LENGTH - sTagPrefixLength) {
            return sTagPrefixLength + tag.substring(0, MAX_LOG_TAG_LENGTH - sTagPrefixLength - 1);
        }

        return sTagPrefix + tag;
    }

    /**
     * Make the log tag
     * <p/>Don't use this when obfuscating class names!
     * @return The Generated tag.
     */
    public static String makeLogTag(Class clazz) {
        return makeLogTag(clazz.getSimpleName());
    }


    public static void v(String tag, Object... messages) {
        if (sDebug) {
            Log.v(tag, makeMessage(messages));
        }
    }

    public static void v(String tag, Throwable throwable, Object... messages) {
        if (sDebug) {
            Log.v(tag, makeMessage(messages), throwable);
        }
    }

    public static void d(String tag, Object... messages) {
        if (sDebug) {
            Log.d(tag, makeMessage(messages));
        }
    }

    public static void d(String tag, Throwable throwable, Object... messages) {
        if (sDebug) {
            Log.d(tag, makeMessage(messages), throwable);
        }
    }

    public static void i(String tag, Object... messages) {
        if (sDebug) {
            Log.i(tag, makeMessage(messages));
        }
    }

    public static void i(String tag, Throwable throwable, Object... messages) {
        if (sDebug) {
            Log.i(tag, makeMessage(messages), throwable);
        }
    }

    public static void w(String tag, Object... messages) {
        if (sDebug) {
            Log.w(tag, makeMessage(messages));
        }
    }

    public static void w(String tag, Throwable throwable, Object... messages) {
        if (sDebug) {
            Log.w(tag, makeMessage(messages), throwable);
        }
    }

    public static void e(String tag, Object... messages) {
        if (sDebug) {
            Log.e(tag, makeMessage(messages));
        }
    }

    public static void e(String tag, Throwable throwable, Object... messages) {
        if (sDebug) {
            Log.e(tag, makeMessage(messages), throwable);
        }
    }

    private static String makeMessage(Object... messages) {
        String message;
        if (messages != null && messages.length == 1) {
            // handle this common case without the extra cost of creating a stringbuffer:
            message = messages[0].toString();
        } else {
            StringBuilder sb = new StringBuilder();
            if (messages != null) for (Object m : messages) {
                sb.append(m);
            }
            message = sb.toString();
        }
        return message;
    }

}
