package io.keepcoding.madridguide.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.adapters.CustomActivityInfoWindowAdapter;
import io.keepcoding.madridguide.fragments.ActivitiesFragment;
import io.keepcoding.madridguide.interactors.GetAllActivitiesFromLocalCacheInteractor;
import io.keepcoding.madridguide.interactors.GetAllActivitiesInteractorResponse;
import io.keepcoding.madridguide.model.Activities;
import io.keepcoding.madridguide.model.Activity;
import io.keepcoding.madridguide.navigator.Navigator;
import io.keepcoding.madridguide.views.OnElementClick;

import static io.keepcoding.madridguide.util.Constants.LATITUDE_MADRID;
import static io.keepcoding.madridguide.util.Constants.LONGITUDE_MADRID;
import static io.keepcoding.madridguide.util.Constants.ZOOM_MADRID;


public class ActivitiesActivity extends AppCompatActivity {
    private ActivitiesFragment activitiesFragment;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities);

        activitiesFragment = (ActivitiesFragment) getSupportFragmentManager().findFragmentById(R.id.activity_activities_fragment_activities);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        getActivitiesAndSetupFragments();
    }

    private void getActivitiesAndSetupFragments() {
        GetAllActivitiesFromLocalCacheInteractor interactor = new GetAllActivitiesFromLocalCacheInteractor();
        interactor.execute(this, new GetAllActivitiesInteractorResponse() {
            @Override
            public void response(Activities activities) {
                setupActivitiesFragment(activities);
                setupMapFragment(activities);
            }
        });
    }

    private void setupActivitiesFragment(final @NonNull Activities activities) {
        activitiesFragment.setActivities(activities);

        activitiesFragment.setListener(new OnElementClick<Activity>() {
            @Override
            public void clickedOn(Activity activity, int position) {
                Navigator.navigateFromActivitiesActivityToActivityDetailActivity(ActivitiesActivity.this, activity);
            }
        });
    }

    private void setupMapFragment(final @NonNull Activities activities) {
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                if (ActivityCompat.checkSelfPermission(ActivitiesActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(ActivitiesActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }

                centerMapOnMadrid(googleMap);
                googleMap.setMyLocationEnabled(true);

                final Map<Marker, Activity> markerActivityMap = createMarkerActivities(googleMap, activities);
                googleMap.setInfoWindowAdapter(new CustomActivityInfoWindowAdapter(getLayoutInflater(), markerActivityMap));

                googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Activity activity = markerActivityMap.get(marker);
                        Navigator.navigateFromActivitiesActivityToActivityDetailActivity(ActivitiesActivity.this, activity);
                    }
                });
            }
        });
    }

    private Map<Marker, Activity> createMarkerActivities(GoogleMap map, final @NonNull Activities activities) {
        Map<Marker, Activity> markerActivityMap = new HashMap<>((int)activities.size());

        for (Activity activity: activities.allElements()) {
            LatLng latlng = new LatLng(activity.getLatitude(), activity.getLongitude());
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(latlng)
                    .title(activity.getName()));
            markerActivityMap.put(marker, activity);
        }

        return markerActivityMap;
    }

    private void centerMapOnMadrid(GoogleMap googleMap) {
        LatLng latLng = new LatLng(LATITUDE_MADRID, LONGITUDE_MADRID);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(ZOOM_MADRID));
    }
}
