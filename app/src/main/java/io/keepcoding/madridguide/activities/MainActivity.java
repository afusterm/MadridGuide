package io.keepcoding.madridguide.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.navigator.Navigator;
import io.keepcoding.madridguide.util.NetworkUtils;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.activity_main_shops_button)
    Button shopsButton;

    @Override
    protected void onStart() {
        super.onStart();

        if (!NetworkUtils.isNetworkAvailable(this)) {
            Snackbar.make(findViewById(android.R.id.content), "No hay conexión a Internet",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setupShopsButton();
    }

    private void setupShopsButton() {
        shopsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigator.navigateFromMainActivityToShopsActivity(MainActivity.this);
            }
        });
    }
}
