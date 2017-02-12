package io.keepcoding.madridguide.navigator;

import android.app.Activity;
import android.content.Intent;

import java.lang.ref.WeakReference;

import io.keepcoding.madridguide.activities.ActivitiesActivity;
import io.keepcoding.madridguide.activities.ActivityDetailActivity;
import io.keepcoding.madridguide.activities.MainActivity;
import io.keepcoding.madridguide.activities.ShopDetailActivity;
import io.keepcoding.madridguide.activities.ShopsActivity;
import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.util.Constants;

public class Navigator {
    private static WeakReference<Activity> startActivity;

    public static Intent navigateFromMainActivityToShopsActivity(final MainActivity mainActivity) {
        final Intent i = new Intent(mainActivity, ShopsActivity.class);
        mainActivity.startActivity(i);

        return i;
    }

    public static Intent navigateFromMainActivityToActivitiesActivity(final MainActivity mainActivity) {
        final Intent i = new Intent(mainActivity, ActivitiesActivity.class);
        mainActivity.startActivity(i);

        return i;
    }

    public static Intent navigateFromShopsActivityToShopDetailActivity(final ShopsActivity shopsActivity,
                                                                       Shop detail) {
        final Intent i = new Intent(shopsActivity, ShopDetailActivity.class);
        i.putExtra(Constants.INTENT_KEY_DETAIL_SHOP, detail);

        shopsActivity.startActivity(i);
        return i;
    }

    public static Intent navigateFromActivitiesActivityToActivityDetailActivity(final ActivitiesActivity activitiesActivity,
                                                                       io.keepcoding.madridguide.model.Activity detail) {
        final Intent i = new Intent(activitiesActivity, ActivityDetailActivity.class);
        i.putExtra(Constants.INTENT_KEY_DETAIL_ACTIVITY, detail);

        activitiesActivity.startActivity(i);
        return i;
    }

    public static Intent navigateToMainActivity() {
        if (startActivity == null) {
            return null;
        }

        final Intent i = new Intent(startActivity.get(), MainActivity.class);
        startActivity.get().startActivity(i);
        startActivity.get().finish();

        return i;
    }

    public static void setStartActivity(final Activity startActivity) {
        Navigator.startActivity = new WeakReference<>(startActivity);
    }
}
