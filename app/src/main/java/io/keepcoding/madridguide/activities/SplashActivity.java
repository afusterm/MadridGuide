package io.keepcoding.madridguide.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.interactors.CacheAllShopsInteractor;
import io.keepcoding.madridguide.interactors.GetAllShopsFromLocalCacheInteractor;
import io.keepcoding.madridguide.interactors.GetAllShopsInteractor;
import io.keepcoding.madridguide.interactors.GetAllShopsInteractorResponse;
import io.keepcoding.madridguide.model.Shops;
import io.keepcoding.madridguide.navigator.Navigator;
import io.keepcoding.madridguide.util.Cache;
import io.keepcoding.madridguide.util.NetworkUtils;

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

        getAllShopsFromLocalCache();
    }

    private void getAllShopsFromLocalCache() {
        if (Cache.hasExpired(this)) {
            Cache.delete(this);
        }

        new GetAllShopsFromLocalCacheInteractor().execute(getApplicationContext(), new GetAllShopsInteractorResponse() {
            @Override
            public void response(Shops shops) {
                if (shops.size() > 0) {
                    Navigator.navigateFromSplashActivityToMainActivity(SplashActivity.this);
                    finish();
                }

                if (NetworkUtils.isNetworkAvailable(SplashActivity.this)) {
                    getShopsFromInternetAndCache();
                }
            }
        });
    }

    private void getShopsFromInternetAndCache() {
        new GetAllShopsInteractor().execute(getApplicationContext(), new GetAllShopsInteractorResponse() {
            @Override
            public void response(Shops shops) {
                cacheAllShops(shops);
            }
        });
    }

    private void cacheAllShops(Shops shops) {
        new CacheAllShopsInteractor().execute(getApplicationContext(), shops,
                new CacheAllShopsInteractor.CacheAllShopsInteractorResponse() {
                    @Override
                    public void response(boolean success) {
                        Navigator.navigateFromSplashActivityToMainActivity(SplashActivity.this);
                        finish();

                        if (success) {
                            Cache.setDayToNow(SplashActivity.this);
                        }
                    }
                }
        );
    }
}
