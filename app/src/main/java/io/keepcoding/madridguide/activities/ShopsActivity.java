package io.keepcoding.madridguide.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.fragments.ShopsFragment;
import io.keepcoding.madridguide.manager.db.ShopDAO;
import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.model.Shops;

public class ShopsActivity extends AppCompatActivity {
    private ShopsFragment shopsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        shopsFragment = (ShopsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_shops_fragment_shops);
        getShops();
    }

    public void getShops() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ShopDAO dao = new ShopDAO(ShopsActivity.this);
                List<Shop> shopList = dao.query();
                final Shops shops = Shops.build(shopList);

                ShopsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        shopsFragment.setShops(shops);
                    }
                });
            }
        }).start();
    }
}
