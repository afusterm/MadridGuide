package io.keepcoding.madridguide;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import io.keepcoding.madridguide.interactors.CacheAllShopsInteractor;
import io.keepcoding.madridguide.interactors.GetAllShopsInteractor;
import io.keepcoding.madridguide.manager.db.ShopDAO;
import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.model.Shops;

public class MadridGuideApplication extends Application {
    private static WeakReference<Context> appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = new WeakReference<Context>(getApplicationContext());

        // XXX insertTestDataInDB();

        Picasso.with(getApplicationContext()).setLoggingEnabled(true);
        Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);

        new GetAllShopsInteractor().execute(getApplicationContext(),
            new GetAllShopsInteractor.GetAllShopsInteractorResponse() {
                @Override
                public void response(Shops shops) {
                    new CacheAllShopsInteractor().execute(getApplicationContext(),
                        shops, new CacheAllShopsInteractor.CacheAllShopsInteractorResponse() {
                            @Override
                            public void response(boolean success) {

                            }
                        }
                    );
                }
            }
        );
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
