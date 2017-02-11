package io.keepcoding.madridguide.manager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.LinkedList;
import java.util.List;

import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.model.Shops;

import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.ALL_COLUMNS;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_ADDRESS;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_DESCRIPTION;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_ID;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_IMAGE_URL;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_LATITUDE;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_LOGO_IMAGE_URL;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_LONGITUDE;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_NAME;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_URL;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.TABLE_SHOP;

public class ShopDAO extends BaseDAO<Shop> {

    public ShopDAO(Context context, DBHelper dbHelper) {
        super(context, dbHelper);
    }

    @NonNull
    @Override
    public ContentValues getContentValues(@NonNull Shop shop) {
        final ContentValues contentValues = new ContentValues();

        if (shop == null) {
            return contentValues;
        }

        contentValues.put(KEY_SHOP_ADDRESS, shop.getAddress());
        contentValues.put(KEY_SHOP_DESCRIPTION, shop.getDescription());
        contentValues.put(KEY_SHOP_IMAGE_URL, shop.getImageUrl());
        contentValues.put(KEY_SHOP_LOGO_IMAGE_URL, shop.getLogoImgUrl());
        contentValues.put(KEY_SHOP_LATITUDE, shop.getLatitude());
        contentValues.put(KEY_SHOP_LONGITUDE, shop.getLongitude());
        contentValues.put(KEY_SHOP_NAME, shop.getName());
        contentValues.put(KEY_SHOP_URL, shop.getUrl());

        return contentValues;
    }

    @Override
    public Shop getElementFromCursor(Cursor c) {
        long identifier = c.getLong(c.getColumnIndex(KEY_SHOP_ID));
        String name = c.getString(c.getColumnIndex(KEY_SHOP_NAME));
        Shop shop = new Shop(identifier, name);
        shop.setAddress(c.getString(c.getColumnIndex(KEY_SHOP_ADDRESS)));
        shop.setDescription(c.getString(c.getColumnIndex(KEY_SHOP_DESCRIPTION)));
        shop.setImageUrl(c.getString(c.getColumnIndex(KEY_SHOP_IMAGE_URL)));
        shop.setLogoImgUrl(c.getString(c.getColumnIndex(KEY_SHOP_LOGO_IMAGE_URL)));
        shop.setLatitude(c.getFloat(c.getColumnIndex(KEY_SHOP_LATITUDE)));
        shop.setLongitude(c.getFloat(c.getColumnIndex(KEY_SHOP_LONGITUDE)));
        shop.setUrl(c.getString(c.getColumnIndex(KEY_SHOP_URL)));

        return shop;
    }

    @NonNull
    @Override
    public String getTableName() {
        return TABLE_SHOP;
    }

    @NonNull
    @Override
    public String getIdFieldName() {
        return KEY_SHOP_ID;
    }

    @NonNull
    @Override
    public String[] getAllColumnNames() {
        return ALL_COLUMNS;
    }

    public ShopDAO(Context context) {
        this(context, DBHelper.getInstance(context));
    }

    public static @NonNull Shop getShopFromContentValues(final @NonNull ContentValues contentValues) {
        final Shop shop = new Shop(1, "");

        shop.setName(contentValues.getAsString(KEY_SHOP_NAME));
        shop.setAddress(contentValues.getAsString(KEY_SHOP_ADDRESS));
        shop.setDescription(contentValues.getAsString(KEY_SHOP_DESCRIPTION));
        shop.setImageUrl(contentValues.getAsString(KEY_SHOP_IMAGE_URL));
        shop.setLogoImgUrl(contentValues.getAsString(KEY_SHOP_LOGO_IMAGE_URL));
        shop.setUrl(contentValues.getAsString(KEY_SHOP_URL));
        shop.setLatitude(contentValues.getAsFloat(KEY_SHOP_LATITUDE));
        shop.setLongitude(contentValues.getAsFloat(KEY_SHOP_LONGITUDE));

        return shop;
    }

    @Override
    public void update(long id, @NonNull Shop data) {

    }

    @NonNull
    public static Shops getShops(Cursor data) {
        List<Shop> shopList = new LinkedList<>();

        while (data.moveToNext()) {
            Shop shop = ShopDAO.getShop(data);
            shopList.add(shop);
        }

        return Shops.build(shopList);
    }
}
