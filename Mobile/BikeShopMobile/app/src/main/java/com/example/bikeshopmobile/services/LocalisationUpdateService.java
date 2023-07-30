package com.example.bikeshopmobile.services;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.bikeshopmobile.entites.Localisation;
import com.example.bikeshopmobile.utils.ApiInterface;
import com.example.bikeshopmobile.utils.RetrofitClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalisationUpdateService extends Service {

    private static final long LOCATION_UPDATE_INTERVAL = 15 * 60 * 1000; // 15 minutes
    private ApiInterface apiInterface;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    sendLocationToServer(location);
                }
            }
        };

        startLocationUpdates();

        return START_STICKY;
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationRequest locationRequest = new LocationRequest()
                    .setInterval(LOCATION_UPDATE_INTERVAL)
                    .setFastestInterval(LOCATION_UPDATE_INTERVAL)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    private void sendLocationToServer(Location location) {
        Call<Localisation> call = apiInterface.addOrUpdateLocalisation(new Localisation(location.getLatitude(), location.getLongitude()));
        call.enqueue(new Callback<Localisation>() {
            @Override
            public void onResponse(Call<Localisation> call, Response<Localisation> response) {
                Log.d("LocationUpdateService", "Localisation Envoyé");
            }

            @Override
            public void onFailure(Call<Localisation> call, Throwable t) {
                Log.d("LocationUpdateService", "Error "+t);
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }
}
