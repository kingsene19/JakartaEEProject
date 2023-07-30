package com.example.bikeshopmobile.activities.stock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.bikeshopmobile.Database;
import com.example.bikeshopmobile.R;
import com.example.bikeshopmobile.entites.Produit;
import com.example.bikeshopmobile.entites.Magasin;
import com.example.bikeshopmobile.entites.Stock;
import com.example.bikeshopmobile.entites.StockPK;
import com.example.bikeshopmobile.services.ApiService;
import com.example.bikeshopmobile.utils.ApiInterface;
import com.example.bikeshopmobile.utils.ConnectionManager;
import com.example.bikeshopmobile.utils.CustomSpinnerAdapter;
import com.example.bikeshopmobile.utils.RetrofitClient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStock extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;
    EditText quantiteEditText;
    Button btn1;
    ImageButton btn2;
    ApiInterface apiInterface;
    List<Produit> produitList;
    List<Magasin> magasinList;
    List<String> produitListString = new ArrayList<>();
    List<String> magasinListString = new ArrayList<>();
    int selectedPositionMagasin, selectedPositionProduit;
    Database db = new Database(this, "BikeShop", null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        quantiteEditText = findViewById(R.id.quantiteEditText);
        btn1 = findViewById(R.id.addButton);
        btn2 = findViewById(R.id.backButton);

        broadcastReceiver =  new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && "ACTION_PRODUITS_LOADED".equals(intent.getAction())) {
                    List<Produit> data = (List<Produit>) intent.getSerializableExtra("produitList");
                    if (data != null) {
                        produitList = data;
                        for (Produit e: produitList) {
                            produitListString.add(e.getNom());
                        }
                        Spinner produitSpinner = findViewById(R.id.produitSpinner);
                        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(AddStock.this, R.layout.spinner_item, produitListString);
                        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
                        produitSpinner.setAdapter(spinnerAdapter);
                        produitSpinner.setSelection(0);
                        produitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                View selectedItem = parent.getSelectedView();
                                if (selectedItem != null) {
                                    selectedItem.setBackgroundColor(getResources().getColor(R.color.bg_dark_subtle));
                                    String selectedProduit = (String) parent.getItemAtPosition(position);
                                    selectedPositionProduit = getProduitPosition(selectedProduit);
                                    produitSpinner.setSelection(selectedPositionProduit);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                    }
                } else if (intent != null && "ACTION_MAGASINS_LOADED".equals(intent.getAction())) {
                    List<Magasin> data = (List<Magasin>) intent.getSerializableExtra("magasinList");
                    if (data != null) {
                        magasinList = data;
                        for (Magasin m: magasinList) {
                            magasinListString.add(m.getNom());
                        }
                        Spinner magasinSpinner = findViewById(R.id.magasinSpinner);
                        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(AddStock.this, R.layout.spinner_item, magasinListString);
                        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
                        magasinSpinner.setAdapter(spinnerAdapter);
                        magasinSpinner.setSelection(0);
                        magasinSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                View selectedItem = parent.getSelectedView();
                                if (selectedItem != null) {
                                    selectedItem.setBackgroundColor(getResources().getColor(R.color.bg_dark_subtle));
                                    String selectedMagasin = (String) parent.getItemAtPosition(position);
                                    selectedPositionMagasin = getMagasinPosition(selectedMagasin);
                                    magasinSpinner.setSelection(selectedPositionMagasin);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                }
            }
        };

        IntentFilter filter1 = new IntentFilter("ACTION_PRODUITS_LOADED");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter1);
        IntentFilter filter2 = new IntentFilter("ACTION_MAGASINS_LOADED");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter2);
        getProduits();
        getMagasins();


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer qte = Integer.parseInt(quantiteEditText.getText().toString());
                if (qte==null) {
                    Toast.makeText(AddStock.this, "Les champs quantite, magasin et produit sont obligatoires", Toast.LENGTH_SHORT);
                } else {
                    Stock stock = new Stock();
                    stock.setStockPK(new StockPK(selectedPositionProduit, selectedPositionMagasin));
                    stock.setQuantite(qte);
                    Produit prod = new Produit(produitList.get(selectedPositionProduit).getId());
                    prod.setNom(produitList.get(selectedPositionProduit).getNom());
                    Magasin mag = new Magasin(magasinList.get(selectedPositionMagasin).getId());
                    mag.setNom(magasinList.get(selectedPositionMagasin).getNom());
                    stock.setProduitId(prod);
                    stock.setMagasinId(mag);
                    addStock(stock);
                }

                }
            });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddStock.this, ListStocks.class));
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    private int getProduitPosition(String s) {
        if (s != null && produitList != null) {
            for (int i = 0; i < produitList.size(); i++) {
                if (s.equals(produitList.get(i).getNom())) {
                    return i;
                }
            }
        }
        return 0;
    }

    private int getMagasinPosition(String s) {
        if (s != null && magasinList != null) {
            for (int i = 0; i < magasinList.size(); i++) {
                if (s.equals(magasinList.get(i).getNom())) {
                    return i;
                }
            }
        }
        return 0;
    }

    private void getProduits() {
        Intent serviceIntent = new Intent(this, ApiService.class);
        serviceIntent.setAction("getProduits");
        startService(serviceIntent);
    }

    private void getMagasins() {
        Intent serviceIntent = new Intent(this, ApiService.class);
        serviceIntent.setAction("getMagasins");
        startService(serviceIntent);
    }


    private void addStock(Stock stock) {
        db.addStock(stock);
        startActivity(new Intent(AddStock.this, ListStocks.class));
    }
}