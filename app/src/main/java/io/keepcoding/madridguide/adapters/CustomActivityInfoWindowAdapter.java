package io.keepcoding.madridguide.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.Map;

import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.model.Activity;


public class CustomActivityInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Map<Marker, Activity> markerActivityMap;
    private WeakReference<Context> context;
    private View view;
    private ImageView logoImageView;
    private TextView nameTextView;

    public CustomActivityInfoWindowAdapter(LayoutInflater inflater, final @NonNull Map<Marker, Activity> markerActivityMap) {
        this.markerActivityMap = markerActivityMap;
        context = new WeakReference<>(inflater.getContext());
        view = inflater.inflate(R.layout.custom_info_contents, null);
        logoImageView = (ImageView) view.findViewById(R.id.custom_info_contents_logo);
        nameTextView = (TextView) view.findViewById(R.id.custom_info_contents_name);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(final Marker marker) {
        final Activity activity = markerActivityMap.get(marker);
        nameTextView.setText(activity.getName());

        Picasso.with(context.get())
                .load(activity.getLogoImgUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(logoImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        // When I click a marker, InfoWindow is shown and imageView shows placeholder
                        // image. If I click it again, Picasso loads the image successfully. This is
                        // for avoid that.
                        if (marker.isInfoWindowShown()) {
                            marker.hideInfoWindow();
                            Picasso.with(context.get())
                                    .load(activity.getLogoImgUrl())
                                    .into(logoImageView);
                            marker.showInfoWindow();
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });

        return view;
    }
}
