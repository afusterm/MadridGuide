package io.keepcoding.madridguide.manager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

import io.keepcoding.madridguide.model.BaseModel;
import io.keepcoding.madridguide.model.Shop;

import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_ADDRESS;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_DESCRIPTION;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_ID;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_IMAGE_URL;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_LATITUDE;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_LOGO_IMAGE_URL;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_LONGITUDE;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_NAME;
import static io.keepcoding.madridguide.manager.db.DBConstants.Shop.KEY_SHOP_URL;

public abstract class BaseDAO<E extends BaseModel> implements DAOPersistable<E> {
    protected WeakReference<Context> context;
    protected SQLiteDatabase db;
    DBHelper dbHelper;

    public BaseDAO(Context context, DBHelper dbHelper) {
        this.db = dbHelper.getDB();
        this.context = new WeakReference<Context>(context);
        this.dbHelper = dbHelper;
    }

    @NonNull
    public static Shop getShop(Cursor c) {
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

    /**
     * Insert a element in DB.
     * @param element shouldn't be null.
     * @return 0 if element is null, id if insert is OK, INVALID_ID if insert fails.
     */
    @Override
    public long insert(@NonNull E element) {
        if (element == null) {
            return 0;
        }

        db.beginTransaction();
        long id = DBHelper.INVALID_ID;
        try {
            id = dbHelper.getWritableDatabase().insert(getTableName(), null, getContentValues(element));
            element.setId(id);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        return id;
    }

    @Override
    public int delete(long id) {
        return db.delete(getTableName(), getIdFieldName() + " = " + id, null);
    }

    @Override
    public void deleteAll() {
        db.delete(getTableName(), null, null);
    }

    @Nullable
    @Override
    public Cursor queryCursor() {
        Cursor c = db.query(getTableName(), getAllColumnNames(), null, null, null, null, getIdFieldName());
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
        }

        return c;
    }

    @Override
    public @Nullable E query(final long id) {
        Cursor c = db.query(getTableName(), getAllColumnNames(), getIdFieldName() + " = " + id, null, null, null, getIdFieldName());
        if (c != null && c.getCount() == 1) {
            c.moveToFirst();
        } else {
            return null;
        }

        E element = getElementFromCursor(c);

        return element;
    }

    @Nullable
    @Override
    public List<E> query() {
        Cursor c = queryCursor();

        if (c == null || !c.moveToFirst()) {
            return null;
        }

        List<E> elements = new LinkedList<>();

        c.moveToFirst();
        do {
            E element = getElementFromCursor(c);
            elements.add(element);
        } while (c.moveToNext());

        return elements;
    }

    public Cursor queryCursor(long id) {
        Cursor c = db.query(getTableName(), getAllColumnNames(), "ID = " + id, null, null, null, getIdFieldName());
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
        }

        return c;
    }

    public abstract @NonNull ContentValues getContentValues(final @NonNull E element);
    public abstract E getElementFromCursor(Cursor c);
    public abstract @NonNull String getTableName();
    public abstract @NonNull String getIdFieldName();
    public abstract @NonNull String[] getAllColumnNames();
}
