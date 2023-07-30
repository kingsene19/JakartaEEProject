package com.example.bikeshopmobile.activities.article;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.example.bikeshopmobile.R;
import com.example.bikeshopmobile.activities.HomeActivity;
import com.example.bikeshopmobile.entites.ArticleCommande;
import com.example.bikeshopmobile.entites.ArticleCommandePK;
import com.example.bikeshopmobile.entites.Produit;
import com.example.bikeshopmobile.services.ApiService;
import com.example.bikeshopmobile.utils.ApiInterface;
import com.example.bikeshopmobile.utils.CustomSpinnerAdapter;
import com.example.bikeshopmobile.utils.RetrofitClient;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddArticle extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;
    List<Produit> produitList;
    List<String> produitListString = new ArrayList<>();
    EditText quantiteEditText, prixDepartEditText, remiseEditText, ligneEditText, numeroEditText;
    Button btn1;
    ImageButton btn2;
    ApiInterface apiInterface;
    ArticleCommande articleCommande;
    int selectedPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_article);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        quantiteEditText = findViewById(R.id.quantiteEditText);
        prixDepartEditText = findViewById(R.id.prixDepartEditText);
        remiseEditText = findViewById(R.id.remiseEditText);
        ligneEditText = findViewById(R.id.ligneEditText);
        numeroEditText = findViewById(R.id.numeroEditText);
        btn1 = findViewById(R.id.addButton);
        btn2 = findViewById(R.id.backButton);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && "ACTION_PRODUITS_LOADED".equals(intent.getAction())) {
                    List<Produit> data = (List<Produit>) intent.getSerializableExtra("produitList");
                    if (data != null) {
                        produitList = data;
                        for (Produit p: produitList) {
                            produitListString.add(p.getNom());
                        }
                        Spinner produitSpinner = findViewById(R.id.produitSpinner);
                        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(AddArticle.this, R.layout.spinner_item, produitListString);
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
                                    selectedPosition = getProductPosition(selectedProduit);
                                    produitSpinner.setSelection(selectedPosition);
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

        IntentFilter filter = new IntentFilter("ACTION_PRODUITS_LOADED");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
        getProduits();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Produit produit = produitList.get(selectedPosition);
                Integer qte = Integer.parseInt(quantiteEditText.getText().toString());
                BigDecimal remise = new BigDecimal(remiseEditText.getText().toString());
                BigDecimal prixDepart = new BigDecimal(prixDepartEditText.getText().toString());
                Integer numero = Integer.parseInt(numeroEditText.getText().toString());
                Integer ligne = Integer.parseInt(ligneEditText.getText().toString());
                if (ligne==null|| numero==null||prixDepart==null||remise==null||qte==null||produit==null) {
                    Toast.makeText(AddArticle.this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT);
                } else {
                    articleCommande = new ArticleCommande();
                    articleCommande.setArticleCommandePK(new ArticleCommandePK(numero,ligne));
                    articleCommande.setPrixDepart(prixDepart);
                    articleCommande.setRemise(remise);
                    articleCommande.setQuantite(qte);
                    articleCommande.setProduitId(produit);
                    addArticleCommande(articleCommande);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddArticle.this, ListArticles.class));
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

    private void getProduits() {
        Intent serviceIntent = new Intent(this, ApiService.class);
        serviceIntent.setAction("getProduits");
        startService(serviceIntent);
    }

    private int getProductPosition(String product) {
        if (product != null && produitListString != null) {
            for (int i = 0; i < produitListString.size(); i++) {
                if (product.equals(produitListString.get(i))) {
                    return i;
                }
            }
        }
        return 0;
    }

    private void addArticleCommande(ArticleCommande articleCommande) {
        Call<ArticleCommande> call = apiInterface.addOrUpdateArticle(articleCommande);
        call.enqueue(
                new Callback<ArticleCommande>() {
                    @Override
                    public void onResponse(Call<ArticleCommande> call, Response<ArticleCommande> response) {
                        if (response.isSuccessful()) {
                            startActivity(new Intent(AddArticle.this, ListArticles.class));
                        } else {
                            Toast.makeText(AddArticle.this, "L'enregistrement des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArticleCommande> call, Throwable t) {
                        Log.d("AddArticle", "Error "+t);
                    }
                }
        );
    }
}