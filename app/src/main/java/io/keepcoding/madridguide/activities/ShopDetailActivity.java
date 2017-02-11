package io.keepcoding.madridguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.util.Constants;

import static io.keepcoding.madridguide.util.Constants.GOOGLE_MAPS_STATIC_IMAGE_BASE_URL;

public class ShopDetailActivity extends AppCompatActivity {
    private static final String TAG = ShopDetailActivity.class.getCanonicalName();

    @BindView(R.id.activity_shop_detail_shop_name_text)
    TextView shopNameText;

    @BindView(R.id.activity_shop_detail_shop_logo_image)
    ImageView shopLogoImage;

    @BindView(R.id.activity_shop_detail_shop_description)
    TextView shopDescriptionText;

    @BindView(R.id.activity_shop_detail_shop_address)
    TextView shopAddressText;

    @BindView(R.id.activity_shop_detail_image_map)
    ImageView shopMapImage;

    Shop shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        ButterKnife.bind(this);

        getDetailShopFromCallingIntent();

        updateUI();
    }

    private void getDetailShopFromCallingIntent() {
        Intent i = getIntent();
        if (i != null) {
            shop = (Shop) i.getSerializableExtra(Constants.INTENT_KEY_DETAIL_SHOP);
        }
    }

    private void updateUI() {
        final String urlImageMap = String.format(Locale.ENGLISH, "%s?center=%f,%f&zoom=17&size=320x220&scale=2",
                GOOGLE_MAPS_STATIC_IMAGE_BASE_URL, shop.getLatitude(), shop.getLongitude());

        shopNameText.setText(shop.getName());
        shopDescriptionText.setText(shop.getDescription());
        shopAddressText.setText(shop.getAddress());
        Picasso.with(this)
                .load(shop.getLogoImgUrl())
                .into(shopLogoImage);
        Picasso.with(this)
                .load(urlImageMap)
                .into(shopMapImage);
    }
}
