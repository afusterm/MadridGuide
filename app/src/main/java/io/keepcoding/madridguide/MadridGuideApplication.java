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

public class MadridGuideApplication extends Application {
    private static WeakReference<Context> appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = new WeakReference<Context>(getApplicationContext());

        Picasso.with(getApplicationContext()).setLoggingEnabled(true);
        Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);

        getAllShopsFromLocalCache();
    }

    private void getAllShopsFromLocalCache() {
        new GetAllShopsFromLocalCacheInteractor().execute(getApplicationContext(), new GetAllShopsInteractorResponse() {
            @Override
            public void response(Shops shops) {
                if (shops.size() > 0) {
                    return;
                }

                getShopsFromInternetAndCache();
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
