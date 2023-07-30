package com.example.bikeshopmobile.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.bikeshopmobile.utils.ApiInterface;
import com.example.bikeshopmobile.utils.RetrofitClient;
import com.example.bikeshopmobile.entites.ArticleCommande;
import com.example.bikeshopmobile.entites.Categorie;
import com.example.bikeshopmobile.entites.Client;
import com.example.bikeshopmobile.entites.Commande;
import com.example.bikeshopmobile.entites.Employe;
import com.example.bikeshopmobile.entites.Magasin;
import com.example.bikeshopmobile.entites.Marque;
import com.example.bikeshopmobile.entites.Produit;
import com.example.bikeshopmobile.entites.Stock;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiService extends IntentService {

    private static final String TAG = "ApiService";
    private ApiInterface apiInterface;

    public ApiService() {
        super("ApiService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (action != null ) {
                switch (action) {
                    case "getArticles":
                        getArticles();
                        break;
                    case "getCategories":
                        getCategories();
                        break;
                    case "getClients":
                        getClients();
                        break;
                    case "getCommandes":
                        getCommandes();
                        break;
                    case "getEmployes":
                        getEmployes();
                        break;
                    case "getMagasins":
                        getMagasins();
                        break;
                    case "getMarques":
                        getMarques();
                        break;
                    case "getProduits":
                        getProduits();
                        break;
                    case "getStocks":
                        getStocks();
                        break;
                    default:
                        Log.d(TAG, "Action inconnue"+action);
                        break;
                }
            }
        }
    }

    private void getArticles() {
        Call<List<ArticleCommande>> call = apiInterface.getArticles();
        call.enqueue(new Callback<List<ArticleCommande>>() {
            @Override
            public void onResponse(Call<List<ArticleCommande>> call, Response<List<ArticleCommande>> response) {
                if (response.isSuccessful()) {
                    Intent broadcastIntent = new Intent("ACTION_ARTICLES_LOADED");
                    broadcastIntent.putExtra("articleCommandeList", (Serializable) response.body());
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "La récupération des données a échoué", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ArticleCommande>> call, Throwable t) {
                Log.d(TAG, "Error" + t);
            }
        });
    }

    private void getCategories() {
        Call<List<Categorie>> call = apiInterface.getCategories();
        call.enqueue(
                new Callback<List<Categorie>>() {
                    @Override
                    public void onResponse(Call<List<Categorie>> call, Response<List<Categorie>> response) {
                        if (response.isSuccessful()) {
                            Intent broadcastIntent = new Intent("ACTION_CATEGORIES_LOADED");
                            broadcastIntent.putExtra("categorieList", (Serializable) response.body());
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "La récupération des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Categorie>> call, Throwable t) {
                        Log.d(TAG, "Error " + t);
                    }
                }
        );
    }

    private void getClients() {
        Call<List<Client>> call = apiInterface.getClients();
        call.enqueue(
                new Callback<List<Client>>() {
                    @Override
                    public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                        if (response.isSuccessful()) {
                            Intent broadcastIntent = new Intent("ACTION_CLIENTS_LOADED");
                            broadcastIntent.putExtra("clientList", (Serializable) response.body());
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "La récupération des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Client>> call, Throwable t) {
                        Log.d(TAG, "Error " + t);
                    }
                }
        );
    }

    private void getCommandes() {
        Call<List<Commande>> call = apiInterface.getCommandes();
        call.enqueue(
                new Callback<List<Commande>>() {
                    @Override
                    public void onResponse(Call<List<Commande>> call, Response<List<Commande>> response) {
                        if (response.isSuccessful()) {
                            Intent broadcastIntent = new Intent("ACTION_COMMANDES_LOADED");
                            broadcastIntent.putExtra("commandeList", (Serializable) response.body());
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "La récupération des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Commande>> call, Throwable t) {
                        Log.d(TAG, "Error "+t);
                    }
                }
        );
    }

    private void getEmployes() {
        Call<List<Employe>> call = apiInterface.getEmployes();
        call.enqueue(
                new Callback<List<Employe>>() {
                    @Override
                    public void onResponse(Call<List<Employe>> call, Response<List<Employe>> response) {
                        if (response.isSuccessful()) {
                            Intent broadcastIntent = new Intent("ACTION_EMPLOYES_LOADED");
                            broadcastIntent.putExtra("employeList", (Serializable) response.body());
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "La récupération des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Employe>> call, Throwable t) {
                        Log.d(TAG, "Error "+t);
                    }
                }
        );
    }

    private void getMagasins() {
        Call<List<Magasin>> call = apiInterface.getMagasins();
        call.enqueue(
                new Callback<List<Magasin>>() {
                    @Override
                    public void onResponse(Call<List<Magasin>> call, Response<List<Magasin>> response) {
                        if (response.isSuccessful()) {
                            Intent broadcastIntent = new Intent("ACTION_MAGASINS_LOADED");
                            broadcastIntent.putExtra("magasinList", (Serializable) response.body());
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
                        } else {
                            Toast.makeText(getApplicationContext(),"La récupération des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Magasin>> call, Throwable t) {
                        Log.d(TAG, "Error "+t);
                    }
                }
        );
    }

    private void getMarques() {
        Call<List<Marque>> call = apiInterface.getMarques();
        call.enqueue(
                new Callback<List<Marque>>() {
                    @Override
                    public void onResponse(Call<List<Marque>> call, Response<List<Marque>> response) {
                        if (response.isSuccessful()) {
                            Intent broadcastIntent = new Intent("ACTION_MARQUES_LOADED");
                            broadcastIntent.putExtra("marqueList", (Serializable) response.body());
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "La récupérationdes des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Marque>> call, Throwable t) {
                        Log.d(TAG, "Error "+t);
                    }
                }
        );
    }

    private void getProduits() {
        Call<List<Produit>> call = apiInterface.getProduits();
        call.enqueue(
                new Callback<List<Produit>>() {
                    @Override
                    public void onResponse(Call<List<Produit>> call, Response<List<Produit>> response) {
                        if (response.isSuccessful()) {
                            Intent broadcastIntent = new Intent("ACTION_PRODUITS_LOADED");
                            broadcastIntent.putExtra("produitList", (Serializable) response.body());
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "La récupération des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Produit>> call, Throwable t) {
                        Log.d(TAG, "Error "+t);
                    }
                }
        );
    }

    private void getStocks() {
        Call<List<Stock>> call = apiInterface.getStocks();
        call.enqueue(
                new Callback<List<Stock>>() {
                    @Override
                    public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                        if (response.isSuccessful()) {
                            Intent broadcastIntent = new Intent("ACTION_STOCKS_LOADED");
                            broadcastIntent.putExtra("stockList", (Serializable) response.body());
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(broadcastIntent);
                        } else {
                            Toast.makeText(getApplicationContext(), "La récupération des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Stock>> call, Throwable t) {
                        Log.d(TAG, "Error "+ t);
                    }
                }
        );
    }
}