package org.ligboy.android.utils;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;

import java.util.Date;

/**
 * Time Utils
 * @author Ligboy.Liu ligboy@gmail.com.
 */
public final class TimeUtils {

    private TimeUtils() {
        throw new IllegalAccessError();
    }

    /**
     * Format time duration.
     * <p>12 days 12 hours 56 minutes 29 seconds</p>
     * @param durationMills The duration of time span.
     * @return The formatted time span.
     */
    @Nullable
    public static String formatDuration(@NonNull final Context context, long durationMills) {

        final Resources res = context.getResources();
        final StringBuilder text = new StringBuilder();

        if (durationMills < 0) {
            durationMills = -durationMills;
        }

        int day = 0;
        int hour = 0;
        int minute = 0;
        int second;

        if (durationMills >= DateUtils.DAY_IN_MILLIS) {
            day = (int) (durationMills / DateUtils.DAY_IN_MILLIS);
            durationMills -= day * DateUtils.DAY_IN_MILLIS;
        }
        if (durationMills >= DateUtils.HOUR_IN_MILLIS) {
            hour = (int) (durationMills / DateUtils.HOUR_IN_MILLIS);
            durationMills -= hour * DateUtils.HOUR_IN_MILLIS;
        }
        if (durationMills >= DateUtils.MINUTE_IN_MILLIS) {
            minute = (int) (durationMills / DateUtils.MINUTE_IN_MILLIS);
            durationMills -= minute * DateUtils.MINUTE_IN_MILLIS;
        }
        second = (int) (durationMills / DateUtils.SECOND_IN_MILLIS);

        try {
            if (day > 0) {
                text.append(res.getQuantityString(R.plurals.duration_days, day, day));
            }
            if (hour > 0) {
                if (text.length() > 0) {
                    text.append(' ');
                }
                text.append(res.getQuantityString(R.plurals.duration_hours, hour, hour));
            }
            if (minute > 0) {
                if (text.length() > 0) {
                    text.append(' ');
                }
                text.append(res.getQuantityString(R.plurals.duration_minutes, minute, minute));
            }

            if (text.length() > 0) {
                text.append(' ');
            }
            text.append(res.getQuantityString(R.plurals.duration_seconds, second, second));
        } catch (Resources.NotFoundException e) {
            return null;
        }
        return text.toString();
    }

    /**
     * Format time duration.
     * <p>12 days 12 hours 56 minutes 29 seconds</p>
     * @param startTime start time
     * @param endTime end time
     * @return The formatted time span.
     */
    @Nullable
    public static String formatDuration(@NonNull final Context context, @NonNull Date startTime, @NonNull Date endTime) {
        return formatDuration(context, endTime.getTime() - startTime.getTime());
    }

    /**
     * Format time duration.
     * <p>12 days 12 hours 56 minutes 29 seconds</p>
     * @param startTime start time
     * @param endTime end time
     * @return The formatted time span.
     */
    @Nullable
    public static String formatDuration(@NonNull final Context context, long startTime, long endTime) {
        return formatDuration(context, endTime - startTime);
    }
}
