package com.example.bikeshopmobile.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.bikeshopmobile.Database;
import com.example.bikeshopmobile.R;
import com.example.bikeshopmobile.activities.HomeActivity;
import com.example.bikeshopmobile.activities.notification.NotificationActivity;
import com.example.bikeshopmobile.entites.Stock;
import com.example.bikeshopmobile.utils.ApiInterface;
import com.example.bikeshopmobile.utils.ConnectionManager;
import com.example.bikeshopmobile.utils.RetrofitClient;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockService extends Service implements ConnectionManager.OnConnectionCheckListener{

    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "stock_channel";
    private static final String NOTIFICATION_CHANNEL_NAME = "Stock Notification Channel";
    private ApiInterface apiInterface;
    private ConnectionManager connectionManager;
    String msg="";

    @Override
    public void onCreate(){
        super.onCreate();
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        connectionManager = new ConnectionManager(this);
        connectionManager.setOnConnectionCheckListener(this);
        connectionManager.checkApiAccess();
    }

    @Override
    public void onConnectionChecked(boolean isAccessible) {
        if (isAccessible) {
            initDB();
        } else {
            Database db = new Database(getApplicationContext(), "BikeShop",null,1);
            String msg  = db.checkStockQuantities();
            showStockNotification(msg);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        Notification notification = createForegroundNotification();
        startForeground(NOTIFICATION_ID, notification);

        return START_STICKY;
    }

    private void checkQuantities() {
        Database db = new Database(getApplicationContext(),"BikeShop",null,1);
        Call<List<Stock>> call = apiInterface.getStocks();
        call.enqueue(
                new Callback<List<Stock>>() {
                    @Override
                    public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                        if (response.isSuccessful()) {
                            for (Stock s: response.body()) {
                                db.save(s);
                                if (s.getQuantite() < 2) {
                                    msg += "Le stock pour " + s.getProduitId().getNom() + " à la boutique " + s.getMagasinId().getNom() + "\n";
                                }
                            }
                            if (msg!="") {
                                showStockNotification(msg);
                                msg="";
                            }
                        } else {
                            Log.d("StockService", "La récupération des données a échoué");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Stock>> call, Throwable t) {
                        Log.d("StockService", "Error "+ t);
                    }
                }
        );

    }


    private void initDB() {
        String databaseName = "BikeShop";
        String databasePath = getApplicationContext().getDatabasePath(databaseName).getPath();
        File databaseFile = new File(databasePath);
        if (databaseFile.exists()) {
            boolean isDeleted = databaseFile.delete();
            if (isDeleted) {
                checkQuantities();
                Log.d("StockService", "Base de données initialisée");
            } else {
                Log.d("StockService", "Pas de base de donnée");
            }
        } else {
            checkQuantities();
            Log.d("StockService", "Base de données initialisée");
        }
    }



    private void showStockNotification(String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_warning)
                .setContentTitle("Faible stock")
                .setContentText("Les stocks suivant sont faibles\n " + message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle("Faible stock");
        bigTextStyle.bigText("Les stocks suivant sont faibles\n " + message);
        builder.setStyle(bigTextStyle);

        Intent notificationIntent = new Intent(this, NotificationActivity.class);
        notificationIntent.putExtra("Message", message);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pendingIntent);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private Notification createForegroundNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_warning)
                .setContentTitle("Stock Service")
                .setContentText("Le service s'exécute en arrière plan")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent = new Intent(this, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pendingIntent);

        return builder.build();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
