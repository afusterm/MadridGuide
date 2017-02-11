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
import io.keepcoding.madridguide.model.Activity;
import io.keepcoding.madridguide.util.Constants;

import static io.keepcoding.madridguide.util.Constants.GOOGLE_MAPS_STATIC_IMAGE_BASE_URL;


public class ActivityDetailActivity extends AppCompatActivity {
    private static final String TAG = ActivityDetailActivity.class.getCanonicalName();

    @BindView(R.id.activity_activity_detail_activity_name_text)
    TextView activityNameText;

    @BindView(R.id.activity_activity_detail_activity_logo_image)
    ImageView activityLogoImage;

    @BindView(R.id.activity_activity_detail_activity_description)
    TextView activityDescriptionText;

    @BindView(R.id.activity_activity_detail_activity_address)
    TextView activityAddressText;

    @BindView(R.id.activity_activity_detail_image_map)
    ImageView activityMapImage;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_detail);

        ButterKnife.bind(this);

        getDetailActivityFromCallingIntent();

        updateUI();
    }

    private void getDetailActivityFromCallingIntent() {
        Intent i = getIntent();
        if (i != null) {
            activity = (Activity) i.getSerializableExtra(Constants.INTENT_KEY_DETAIL_ACTIVITY);
        }
    }

    private void updateUI() {
        final String urlImageMap = String.format(Locale.ENGLISH, "%s?center=%f,%f&zoom=17&size=320x220&scale=2",
                GOOGLE_MAPS_STATIC_IMAGE_BASE_URL, activity.getLatitude(), activity.getLongitude());

        activityNameText.setText(activity.getName());
        activityDescriptionText.setText(activity.getDescription());
        activityAddressText.setText(activity.getAddress());
        Picasso.with(this)
                .load(activity.getLogoImgUrl())
                .into(activityLogoImage);
        Picasso.with(this)
                .load(urlImageMap)
                .into(activityMapImage);
    }
}
