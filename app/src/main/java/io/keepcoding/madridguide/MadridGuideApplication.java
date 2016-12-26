package io.keepcoding.madridguide;

import android.app.Application;
import android.content.Context;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.List;

import io.keepcoding.madridguide.manager.db.ShopDAO;
import io.keepcoding.madridguide.manager.net.NetworkManager;
import io.keepcoding.madridguide.manager.net.ShopEntity;
import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.model.mappers.ShopEntityShopMapper;

public class MadridGuideApplication extends Application {
    private static WeakReference<Context> appContext;

    @Override
    public void onCreate() {
        super.onCreate();

        appContext = new WeakReference<Context>(getApplicationContext());

        // XXX insertTestDataInDB();

        Picasso.with(getApplicationContext()).setLoggingEnabled(true);
        Picasso.with(getApplicationContext()).setIndicatorsEnabled(true);

        // NOTE: testing
        NetworkManager networkManager = new NetworkManager(getApplicationContext());
        networkManager.getShopsFromServer(new NetworkManager.GetShopsListener() {
            @Override
            public void getShopEntitiesSuccess(List<ShopEntity> result) {
                List<Shop> shops = new ShopEntityShopMapper().map(result);
                ShopDAO dao = new ShopDAO(getAppContext());

                for (Shop shop: shops) {
                    dao.insert(shop);
                }
            }

            @Override
            public void getShopEntitiesDidFail() {

            }
        });
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
