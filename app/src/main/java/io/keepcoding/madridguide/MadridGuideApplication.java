package io.keepcoding.madridguide;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import io.keepcoding.madridguide.interactors.CacheAllShopsInteractor;
import io.keepcoding.madridguide.interactors.GetAllShopsFromLocalCacheInteractor;
import io.keepcoding.madridguide.interactors.GetAllShopsInteractor;
import io.keepcoding.madridguide.interactors.GetAllShopsInteractorResponse;
import io.keepcoding.madridguide.model.Shops;
import io.keepcoding.madridguide.navigator.Navigator;
import io.keepcoding.madridguide.util.Cache;
import io.keepcoding.madridguide.util.NetworkUtils;

public class MadridGuideApplication extends Application {
    private static WeakReference<Context> appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = new WeakReference<>(getApplicationContext());

        Picasso.with(getApplicationContext()).setLoggingEnabled(true);
        Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);

        getAllShopsFromLocalCache();
    }

    private void getAllShopsFromLocalCache() {
        if (Cache.hasExpired(this)) {
            Cache.delete(this);
        }

        new GetAllShopsFromLocalCacheInteractor().execute(getApplicationContext(), new GetAllShopsInteractorResponse() {
            @Override
            public void response(Shops shops) {
                if (shops.size() > 0) {
                    Navigator.navigateToMainActivity();
                    return;
                }

                if (NetworkUtils.isNetworkAvailable(appContext.get())) {
                    getShopsFromInternetAndCache();
                }
            }
        });
    }

    private void getShopsFromInternetAndCache() {
        new GetAllShopsInteractor().execute(getApplicationContext(), new GetAllShopsInteractorResponse() {
            @Override
            public void response(Shops shops) {
                cacheAllShops(shops);
            }
        });
    }

    private void cacheAllShops(Shops shops) {
        new CacheAllShopsInteractor().execute(getApplicationContext(), shops,
                new CacheAllShopsInteractor.CacheAllShopsInteractorResponse() {
                    @Override
                    public void response(boolean success) {
                        if (success) {
                            Cache.setDayToNow(getApplicationContext());
                        }

                        Navigator.navigateToMainActivity();
                    }
                }
        );
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static Context getAppContext() {
        return appContext.get();
    }
}
