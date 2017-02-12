package io.keepcoding.madridguide.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.navigator.Navigator;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Navigator.setStartActivity(this);
    }
}
