package io.keepcoding.madridguide.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.navigator.Navigator;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.activity_splash_title_textview)
    TextView titleTextView;

    @BindView(R.id.activity_splash_progressbar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
        Navigator.setStartActivity(this);
    }
}
