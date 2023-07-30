package com.example.bikeshopmobile.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bikeshopmobile.R;
import com.example.bikeshopmobile.activities.article.ListArticles;
import com.example.bikeshopmobile.activities.categorie.ListCategories;
import com.example.bikeshopmobile.activities.client.ListClients;
import com.example.bikeshopmobile.activities.commande.ListCommandes;
import com.example.bikeshopmobile.activities.employe.ListEmployes;
import com.example.bikeshopmobile.activities.magasin.ListMagasins;
import com.example.bikeshopmobile.activities.marque.ListMarques;
import com.example.bikeshopmobile.activities.notification.NotificationActivity;
import com.example.bikeshopmobile.activities.produit.ListProduits;
import com.example.bikeshopmobile.activities.stock.ListStocks;

public class HomeActivity extends AppCompatActivity {

    public CardView article,categorie,client,commande,employe,magasin,marque,produit,stock,notif;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        article = findViewById(R.id.cardArticleCommande);
        categorie = findViewById(R.id.cardCategorie);
        client = findViewById(R.id.cardClient);
        commande = findViewById(R.id.cardCommande);
        employe = findViewById(R.id.cardEmploye);
        magasin = findViewById(R.id.cardMagasin);
        marque = findViewById(R.id.cardMarque);
        produit = findViewById(R.id.cardProduit);
        stock = findViewById(R.id.cardStock);
        notif = findViewById(R.id.cardNotif);

        article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListArticles.class));
            }
        });

        categorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListCategories.class));
            }
        });

        client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListClients.class));
            }
        });

        commande.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListCommandes.class));
            }
        });

        employe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListEmployes.class));
            }
        });

        magasin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListMagasins.class));
            }
        });

        marque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListMarques.class));
            }
        });

        produit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListProduits.class));
            }
        });

        stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, ListStocks.class));
            }
        });

        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, NotificationActivity.class));
            }
        });

        requestLocationPermission();
    }

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Vous avez autorisé la localisation. Vos coordonnées seront partagés au serveur", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Vous n'avez pas autorisé la localisation. Vos coordonnées ne seront pas partagé", Toast.LENGTH_SHORT).show();
            }
        }
    }

}