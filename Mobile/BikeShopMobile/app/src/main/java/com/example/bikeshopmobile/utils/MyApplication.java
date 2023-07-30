package com.example.bikeshopmobile.utils;

import android.app.Application;
import android.util.Log;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MyApplication extends Application {

    private static final long WORK_INTERVAL_MINUTES = 15;

    @Override
    public void onCreate() {
        super.onCreate();
        schedulePeriodicWorker();
    }

    private void schedulePeriodicWorker() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                PeriodicWorker.class, WORK_INTERVAL_MINUTES, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();
        WorkManager workManager = WorkManager.getInstance(getApplicationContext());
        workManager.enqueue(periodicWorkRequest);
    }
}
