package io.keepcoding.madridguide.interactors;

import android.content.Context;

import io.keepcoding.madridguide.manager.db.ShopDAO;
import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.model.Shops;
import io.keepcoding.madridguide.util.MainThread;

public class CacheAllShopsInteractor {
    public interface CacheAllShopsInteractorResponse {
        public void response(boolean success);
    }

    public void execute(final Context context, final Shops shops,
                        final CacheAllShopsInteractorResponse response) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ShopDAO dao = new ShopDAO(context);

                boolean success = true;
                for (Shop shop: shops.allElements()) {
                    success = dao.insert(shop) > 0;

                    if (!success) {
                        break;
                    }
                }

                final boolean shopsInserted = success;

                MainThread.run(new Runnable() {
                    @Override
                    public void run() {
                        if (response != null) {
                            response.response(shopsInserted);
                        }
                    }
                });
            }
        }).start();
    }
}
