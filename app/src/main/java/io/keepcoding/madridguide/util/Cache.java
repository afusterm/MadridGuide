package io.keepcoding.madridguide.util;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.keepcoding.madridguide.manager.db.ActivityDAO;
import io.keepcoding.madridguide.manager.db.ShopDAO;

public class Cache {
    private static final String TIME_CACHE = "TIME_CACHE";
    private static final int DAYS_TO_UPDATE = 7;

    public static boolean hasExpired(final @NonNull Context context) {
        Calendar today = Calendar.getInstance();
        long savedTime = PreferenceManager.getDefaultSharedPreferences(context).getLong(TIME_CACHE, 0);
        long days = TimeUnit.DAYS.convert(today.getTimeInMillis() - savedTime, TimeUnit.MILLISECONDS);

        return days > DAYS_TO_UPDATE;
    }

    public static void delete(final @NonNull Context context) {
        ShopDAO shopDAO = new ShopDAO(context);
        shopDAO.deleteAll();
        ActivityDAO activityDAO = new ActivityDAO(context);
        activityDAO.deleteAll();
    }

    public static void setDayToNow(final @NonNull Context context) {
        Calendar today = Calendar.getInstance();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putLong(TIME_CACHE, today.getTimeInMillis())
                .apply();
    }
}
