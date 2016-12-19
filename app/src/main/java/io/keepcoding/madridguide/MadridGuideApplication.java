package io.keepcoding.madridguide;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import io.keepcoding.madridguide.manager.db.ShopDAO;
import io.keepcoding.madridguide.model.Shop;

public class MadridGuideApplication extends Application {
    private static WeakReference<Context> appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = new WeakReference<Context>(getApplicationContext());

        insertTestDataInDB();

        Picasso.with(getApplicationContext()).setLoggingEnabled(true);
        Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);
    }

    private void insertTestDataInDB() {
        ShopDAO dao = new ShopDAO(getAppContext());
        for (int i = 0; i < 20; i++) {
            Shop shop = new Shop(10, "Shop " + i).setLogoImgUrl("http://platea.pntic.mec.es/~mmotta/web11ab/elfary.jpg");
            dao.insert(shop);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public static Context getAppContext() {
        return appContext.get();
    }
}
