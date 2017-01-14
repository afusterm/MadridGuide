package io.keepcoding.madridguide.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.fragments.ShopsFragment;
import io.keepcoding.madridguide.interactors.GetAllShopsFromLocalCacheInteractor;
import io.keepcoding.madridguide.interactors.GetAllShopsInteractorResponse;
import io.keepcoding.madridguide.manager.db.DBConstants;
import io.keepcoding.madridguide.manager.db.ShopDAO;
import io.keepcoding.madridguide.manager.db.provider.MadridGuideProvider;
import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.model.Shops;
import io.keepcoding.madridguide.navigator.Navigator;
import io.keepcoding.madridguide.views.OnElementClick;

public class ShopsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final double LATITUDE_MADRID = 40.4166922;
    public static final double LONGITUDE_MADRID = -3.7038497;
    public static final int ZOOM_MADRID = 10;

    private ShopsFragment shopsFragment;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops);

        shopsFragment = (ShopsFragment) getSupportFragmentManager().findFragmentById(R.id.activity_shops_fragment_shops);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        setupMapFragment();
        setupShopsFragment();

        // acceso a los datos por content loader
        LoaderManager loaderManager = getSupportLoaderManager();
        // XXX loaderManager.initLoader(0, null, this);
    }

    private void setupShopsFragment() {
        GetAllShopsFromLocalCacheInteractor interactor = new GetAllShopsFromLocalCacheInteractor();
        interactor.execute(this, new GetAllShopsInteractorResponse() {
            @Override
            public void response(Shops shops) {
                shopsFragment.setShops(shops);
            }
        });

        shopsFragment.setListener(new OnElementClick<Shop>() {
            @Override
            public void clickedOn(Shop shop, int position) {
                Navigator.navigateFromShopsActivityToShopDetailActivity(ShopsActivity.this, shop);
            }
        });
    }

    private void setupMapFragment() {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (ActivityCompat.checkSelfPermission(ShopsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(ShopsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }

                centerMapOnMadrid(googleMap);
                googleMap.setMyLocationEnabled(true);
            }
        });
    }

    private void centerMapOnMadrid(GoogleMap googleMap) {
        LatLng latLng = new LatLng(LATITUDE_MADRID, LONGITUDE_MADRID);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_MADRID));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(this,
                MadridGuideProvider.SHOPS_URI,
                DBConstants.ALL_COLUMNS,
                null,
                null,
                null);

        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        final Shops shops = ShopDAO.getShops(data);

        shopsFragment.setListener(new OnElementClick<Shop>() {
            @Override
            public void clickedOn(Shop shop, int position) {
                Navigator.navigateFromShopsActivityToShopDetailActivity(ShopsActivity.this, shop);
            }
        });

        shopsFragment.setShops(shops);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
