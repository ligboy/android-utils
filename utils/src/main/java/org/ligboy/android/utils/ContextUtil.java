package org.ligboy.android.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Ligboy.Liu ligboy@gmail.com.
 */
public final class ContextUtil {

    private ContextUtil() {
        throw new IllegalAccessError();
    }

    /**
     * Return the handle to a system-level service by class.
     * @param context Context
     * @param serviceClass The class of the desired service.
     * @return The service or null if the class is not a supported system service.
     */
    @SuppressWarnings({"unchecked", "ResourceType"})
    @Nullable
    public static <T> T getSystemService(@NonNull Context context, @NonNull Class<T> serviceClass) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            String serviceName = serviceClass.getName();
            return (T) context.getSystemService(serviceName);
        } else {
            return context.getSystemService(serviceClass);
        }
    }

    /**
     * Return the handle to a system-level service by name.
     * The class of the returned object varies by the requested name.
     * <p/>see {@link Context}
     * @param context Context
     * @param serviceName The name of the desired service.
     * @return The service or null if the class is not a supported system service.
     *
     * @see Context#WINDOW_SERVICE
     * @see android.view.WindowManager
     * @see Context#LAYOUT_INFLATER_SERVICE
     * @see android.view.LayoutInflater
     * @see Context#ACTIVITY_SERVICE
     * @see android.app.ActivityManager
     * @see Context#POWER_SERVICE
     * @see android.os.PowerManager
     * @see Context#ALARM_SERVICE
     * @see android.app.AlarmManager
     * @see Context#NOTIFICATION_SERVICE
     * @see android.app.NotificationManager
     * @see Context#KEYGUARD_SERVICE
     * @see android.app.KeyguardManager
     * @see Context#LOCATION_SERVICE
     * @see android.location.LocationManager
     * @see Context#SEARCH_SERVICE
     * @see android.app.SearchManager
     * @see Context#SENSOR_SERVICE
     * @see android.hardware.SensorManager
     * @see Context#STORAGE_SERVICE
     * @see android.os.storage.StorageManager
     * @see Context#VIBRATOR_SERVICE
     * @see android.os.Vibrator
     * @see Context#CONNECTIVITY_SERVICE
     * @see android.net.ConnectivityManager
     * @see Context#WIFI_SERVICE
     * @see android.net.wifi.WifiManager
     * @see Context#AUDIO_SERVICE
     * @see android.media.AudioManager
     * @see Context#MEDIA_ROUTER_SERVICE
     * @see android.media.MediaRouter
     * @see Context#TELEPHONY_SERVICE
     * @see android.telephony.TelephonyManager
     * @see Context#TELEPHONY_SUBSCRIPTION_SERVICE
     * @see android.telephony.SubscriptionManager
     * @see Context#CARRIER_CONFIG_SERVICE
     * @see android.telephony.CarrierConfigManager
     * @see Context#INPUT_METHOD_SERVICE
     * @see android.view.inputmethod.InputMethodManager
     * @see Context#UI_MODE_SERVICE
     * @see android.app.UiModeManager
     * @see Context#DOWNLOAD_SERVICE
     * @see android.app.DownloadManager
     * @see Context#BATTERY_SERVICE
     * @see android.os.BatteryManager
     * @see Context#JOB_SCHEDULER_SERVICE
     * @see android.app.job.JobScheduler
     * @see Context#NETWORK_STATS_SERVICE
     * @see android.app.usage.NetworkStatsManager
     */
    @SuppressWarnings({"unchecked"})
    @Nullable
    public static <T> T getSystemService(@NonNull Context context, @ServiceName @NonNull String serviceName) {
        return (T) context.getSystemService(serviceName);
    }

    /**
     * Determine whether <em>you</em> have been granted a particular permission.
     *
     * @param permission The name of the permission being checked.
     *
     * @return {@link android.content.pm.PackageManager#PERMISSION_GRANTED} if you have the
     * permission, or {@link android.content.pm.PackageManager#PERMISSION_DENIED} if not.
     *
     * @see android.content.pm.PackageManager#checkPermission(String, String)
     * @see android.Manifest.permission
     */
    @SuppressWarnings("ConstantConditions")
    @PermissionResult
    public static int checkSelfPermission(@NonNull Context context, @NonNull String permission) {
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }

        return context.checkPermission(permission, android.os.Process.myPid(), Process.myUid());
    }


    /**
     * 创建Android data cache 临时文件
     *
     * @param context      Context
     * @param prefix       临时文件前缀.前缀长度不短于3个字符.
     * @param suffix       临时文件后缀.
     * @param subDirectory 子目录名，null则位于cache根目录
     * @return 临时文件
     */
    @Nullable
    public static File creatTempFile(@NonNull final Context context, @NonNull String prefix, @Nullable String suffix, @Nullable String subDirectory) {
        File tempFile = null;
        //当输出文件不存在时，创建
        File cacheDerectory = null;
        File outputDir = null;
        File externalCacheDir = context.getExternalCacheDir();
        //判断是否具有WRITE_EXTERNAL_STORAGE 权限
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT || context.checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && externalCacheDir != null && externalCacheDir.exists()) {
            cacheDerectory = externalCacheDir;
        } else {
            cacheDerectory = context.getCacheDir();
        }
        if (!TextUtils.isEmpty(subDirectory)) {
            outputDir = new File(cacheDerectory, subDirectory);
        } else {
            outputDir = cacheDerectory;
        }
        if (outputDir != null
                && (outputDir.exists()
                || outputDir.mkdirs())) {
            try {
                tempFile = File.createTempFile(prefix, suffix, outputDir);
            } catch (IOException ignored) {
            }
        }

        return tempFile;
    }

    /**
     * 创建Android data cache 临时文件
     *
     * @param context Context
     * @param prefix  临时文件前缀.前缀长度不短于3个字符.
     * @param suffix  临时文件后缀.
     * @return 临时文件
     */
    @Nullable
    public static File creatTempFile(@NonNull final Context context, @NonNull String prefix, @Nullable String suffix) {
        return creatTempFile(context, prefix, suffix, null);
    }


    @StringDef({
            Context.POWER_SERVICE,
            Context.WINDOW_SERVICE,
            Context.LAYOUT_INFLATER_SERVICE,
            Context.ACCOUNT_SERVICE,
            Context.ACTIVITY_SERVICE,
            Context.ALARM_SERVICE,
            Context.NOTIFICATION_SERVICE,
            Context.ACCESSIBILITY_SERVICE,
            Context.CAPTIONING_SERVICE,
            Context.KEYGUARD_SERVICE,
            Context.LOCATION_SERVICE,
            //@hide: COUNTRY_DETECTOR,
            Context.SEARCH_SERVICE,
            Context.SENSOR_SERVICE,
            Context.STORAGE_SERVICE,
            Context.WALLPAPER_SERVICE,
            Context.VIBRATOR_SERVICE,
            //@hide: STATUS_BAR_SERVICE,
            Context.CONNECTIVITY_SERVICE,
            //@hide: UPDATE_LOCK_SERVICE,
            //@hide: NETWORKMANAGEMENT_SERVICE,
            Context.NETWORK_STATS_SERVICE,
            //@hide: NETWORK_POLICY_SERVICE,
            Context.WIFI_SERVICE,
//            Context.WIFI_PASSPOINT_SERVICE,
            Context.WIFI_P2P_SERVICE,
//            Context.WIFI_SCANNING_SERVICE,
            //@hide: WIFI_RTT_SERVICE,
            //@hide: ETHERNET_SERVICE,
//            Context.WIFI_RTT_SERVICE,
            Context.NSD_SERVICE,
            Context.AUDIO_SERVICE,
            Context.FINGERPRINT_SERVICE,
            Context.MEDIA_ROUTER_SERVICE,
            Context.TELEPHONY_SERVICE,
            Context.TELEPHONY_SUBSCRIPTION_SERVICE,
            Context.CARRIER_CONFIG_SERVICE,
            Context.TELECOM_SERVICE,
            Context.CLIPBOARD_SERVICE,
            Context.INPUT_METHOD_SERVICE,
            Context.TEXT_SERVICES_MANAGER_SERVICE,
            Context.APPWIDGET_SERVICE,
            //@hide: VOICE_INTERACTION_MANAGER_SERVICE,
            //@hide: BACKUP_SERVICE,
            Context.DROPBOX_SERVICE,
            //@hide: DEVICE_IDLE_CONTROLLER,
            Context.DEVICE_POLICY_SERVICE,
            Context.UI_MODE_SERVICE,
            Context.DOWNLOAD_SERVICE,
            Context.NFC_SERVICE,
            Context.BLUETOOTH_SERVICE,
            //@hide: SIP_SERVICE,
            Context.USB_SERVICE,
            Context.LAUNCHER_APPS_SERVICE,
            //@hide: SERIAL_SERVICE,
            //@hide: HDMI_CONTROL_SERVICE,
            Context.INPUT_SERVICE,
            Context.DISPLAY_SERVICE,
            Context.USER_SERVICE,
            Context.RESTRICTIONS_SERVICE,
            Context.APP_OPS_SERVICE,
            Context.CAMERA_SERVICE,
            Context.PRINT_SERVICE,
            Context.CONSUMER_IR_SERVICE,
            //@hide: TRUST_SERVICE,
            Context.TV_INPUT_SERVICE,
            //@hide: NETWORK_SCORE_SERVICE,
            Context.USAGE_STATS_SERVICE,
            Context.MEDIA_SESSION_SERVICE,
            Context.BATTERY_SERVICE,
            Context.JOB_SCHEDULER_SERVICE,
            //@hide: PERSISTENT_DATA_BLOCK_SERVICE,
            Context.MEDIA_PROJECTION_SERVICE,
            Context.MIDI_SERVICE,
//            Context.RADIO_SERVICE,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ServiceName {}

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface ServiceNameLollipop {}

    @IntDef({PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_DENIED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PermissionResult {}
}
