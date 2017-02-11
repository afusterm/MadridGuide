package io.keepcoding.madridguide;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import io.keepcoding.madridguide.interactors.CacheAllActivitiesInteractor;
import io.keepcoding.madridguide.interactors.CacheAllInteractorResponse;
import io.keepcoding.madridguide.interactors.CacheAllShopsInteractor;
import io.keepcoding.madridguide.interactors.GetAllActivitiesFromLocalCacheInteractor;
import io.keepcoding.madridguide.interactors.GetAllActivitiesInteractor;
import io.keepcoding.madridguide.interactors.GetAllActivitiesInteractorResponse;
import io.keepcoding.madridguide.interactors.GetAllShopsFromLocalCacheInteractor;
import io.keepcoding.madridguide.interactors.GetAllShopsInteractor;
import io.keepcoding.madridguide.interactors.GetAllShopsInteractorResponse;
import io.keepcoding.madridguide.model.Activities;
import io.keepcoding.madridguide.model.Shops;
import io.keepcoding.madridguide.navigator.Navigator;
import io.keepcoding.madridguide.util.Cache;
import io.keepcoding.madridguide.util.NetworkUtils;

public class MadridGuideApplication extends Application {
    private static final int MAX_DOWNLOADS = 2;
    private static WeakReference<Context> appContext;
    private int downloads;

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

        new GetAllActivitiesFromLocalCacheInteractor().execute(getApplicationContext(), new GetAllActivitiesInteractorResponse() {
            @Override
            public void response(Activities activities) {
                if (activities.size() > 0) {
                    downloadFinished();
                    return;
                }

                if (NetworkUtils.isNetworkAvailable(appContext.get())) {
                    getActivitiesFromInternetAndCache();
                }
            }
        });

        new GetAllShopsFromLocalCacheInteractor().execute(getApplicationContext(), new GetAllShopsInteractorResponse() {
            @Override
            public void response(Shops shops) {
                if (shops.size() > 0) {
                    downloadFinished();
                    return;
                }

                if (NetworkUtils.isNetworkAvailable(appContext.get())) {
                    getShopsFromInternetAndCache();
                }
            }
        });
    }

    private synchronized void downloadFinished() {
        downloads++;
        if (downloads == MAX_DOWNLOADS) {
            Navigator.navigateToMainActivity();
        }
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
                new CacheAllInteractorResponse() {
                    @Override
                    public void response(boolean success) {
                        if (success) {
                            Cache.setDayToNow(getApplicationContext());
                        }

                        downloadFinished();
                    }
                }
        );
    }

    private void getActivitiesFromInternetAndCache() {
        new GetAllActivitiesInteractor().execute(getApplicationContext(), new GetAllActivitiesInteractorResponse() {
            @Override
            public void response(Activities activities) {
                cacheAllActivities(activities);
            }
        });
    }

    private void cacheAllActivities(Activities activities) {
        new CacheAllActivitiesInteractor().execute(getApplicationContext(), activities, new CacheAllInteractorResponse() {
            @Override
            public void response(boolean success) {
                if (success) {
                    Cache.setDayToNow(getApplicationContext());
                }

                downloadFinished();
            }
        });
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static Context getAppContext() {
        return appContext.get();
    }
}
