package com.sagetech.conference_android.app.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.sagetech.conference_android.app.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.app.AppObservable;
import timber.log.Timber;

public class SplashActivity extends ActionBarActivity {

    private Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        subscription = AppObservable.bindActivity(this, Observable.just(1))
                .delay(3, TimeUnit.SECONDS)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        Timber.i("onCompleted");
                        Intent intent = new Intent(getApplicationContext(), ConferenceDetailActivity.class);
                        intent.putExtra("id", Long.valueOf(getString(R.string.google_io_conference_id)));
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.i("onError", e);
                    }

                    @Override
                    public void onNext(Integer s) {
                        Timber.i("On Next");
                    }
                });
        Timber.i("done!!!");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }

}
