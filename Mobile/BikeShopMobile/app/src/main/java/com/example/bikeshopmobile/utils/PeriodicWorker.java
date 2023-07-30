package com.example.bikeshopmobile.utils;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.bikeshopmobile.services.LocalisationUpdateService;
import com.example.bikeshopmobile.services.StockService;

public class PeriodicWorker extends Worker {

    public PeriodicWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context context = getApplicationContext();
        Intent stockServiceIntent = new Intent(context, StockService.class);
        context.startService(stockServiceIntent);
        Intent localisationServiceIntent = new Intent(context, LocalisationUpdateService.class);
        context.startService(localisationServiceIntent);

        return Result.success();
    }
}
