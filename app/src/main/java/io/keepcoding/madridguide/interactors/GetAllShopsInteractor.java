package io.keepcoding.madridguide.interactors;

import android.content.Context;

import java.util.List;

import io.keepcoding.madridguide.manager.net.NetworkManager;
import io.keepcoding.madridguide.manager.net.ShopEntity;
import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.model.Shops;
import io.keepcoding.madridguide.model.mappers.ShopEntityShopMapper;

public class GetAllShopsInteractor {
    public interface GetAllShopsInteractorResponse {
        public void response(Shops shops);
    }

    public void execute(final Context context, final GetAllShopsInteractorResponse response) {
        Shops shops = null;

        NetworkManager networkManager = new NetworkManager(context);
        networkManager.getShopsFromServer(new NetworkManager.GetShopsListener() {
            @Override
            public void getShopEntitiesSuccess(List<ShopEntity> result) {
                List<Shop> shops = new ShopEntityShopMapper().map(result);

                if (response != null) {
                    response.response(Shops.build(shops));
                }
            }

            @Override
            public void getShopEntitiesDidFail() {
                if (response != null) {
                    response.response(null);
                }
            }
        });
    }
}
