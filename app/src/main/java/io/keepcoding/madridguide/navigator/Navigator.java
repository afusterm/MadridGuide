package io.keepcoding.madridguide.navigator;

import android.content.Intent;

import io.keepcoding.madridguide.activities.MainActivity;
import io.keepcoding.madridguide.activities.ShopDetailActivity;
import io.keepcoding.madridguide.activities.ShopsActivity;
import io.keepcoding.madridguide.activities.SplashActivity;
import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.util.Constants;

public class Navigator {
    public static Intent navigateFromSplashActivityToMainActivity(final SplashActivity splashActivity) {
        final Intent i = new Intent(splashActivity, MainActivity.class);
        splashActivity.startActivity(i);

        return i;
    }

    public static Intent navigateFromMainActivityToShopsActivity(final MainActivity mainActivity) {
        final Intent i = new Intent(mainActivity, ShopsActivity.class);
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
}
