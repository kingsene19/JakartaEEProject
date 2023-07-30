package com.example.bikeshopmobile.activities.article;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.example.bikeshopmobile.R;
import com.example.bikeshopmobile.activities.HomeActivity;
import com.example.bikeshopmobile.entites.ArticleCommande;
import com.example.bikeshopmobile.entites.Commande;
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
import android.widget.ArrayAdapter;
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

public class DetailsArticles extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;
    List<Produit> produitList;
    List<String> produitListString = new ArrayList<>();
    TextView articleView;
    EditText quantiteEditText, prixDepartEditText, remiseEditText;
    Button btn1;
    ImageButton btn2;
    ApiInterface apiInterface;
    int selectedPosition;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_articles);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        ArticleCommande selectedArticle = (ArticleCommande) getIntent().getSerializableExtra("selectedArticle");
        articleView = findViewById(R.id.articleView);
        quantiteEditText = findViewById(R.id.quantiteEditText);
        prixDepartEditText = findViewById(R.id.prixDepartEditText);
        remiseEditText = findViewById(R.id.remiseEditText);
        btn1 = findViewById(R.id.modifyButton);
        btn2 = findViewById(R.id.backButton);

        articleView.setText("Commande " + selectedArticle.getArticleCommandePK().getNumeroCommande()+ ", Ligne " + selectedArticle.getArticleCommandePK().getLigne());
        quantiteEditText.setText(String.valueOf(selectedArticle.getQuantite()));
        prixDepartEditText.setText(String.valueOf(selectedArticle.getPrixDepart()));
        remiseEditText.setText(String.valueOf(selectedArticle.getRemise()));

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
                        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(DetailsArticles.this, R.layout.spinner_item, produitListString);
                        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
                        produitSpinner.setAdapter(spinnerAdapter);
                        if (selectedArticle != null) {
                            selectedPosition = getProductPosition(selectedArticle.getProduitId().getNom());
                            produitSpinner.setSelection(selectedPosition);
                        }
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
                BigDecimal prix = new BigDecimal(prixDepartEditText.getText().toString());
                BigDecimal remise = new BigDecimal(remiseEditText.getText().toString());
                Integer qte = Integer.parseInt(quantiteEditText.getText().toString());
                Produit produit = produitList.get(selectedPosition);
                if (selectedArticle != null) {
                    if (prix==null || remise==null || qte==null || produit==null) {
                        Toast.makeText(DetailsArticles.this, "Tous les champs sont obligatoires", Toast.LENGTH_SHORT);
                    } else {
                        selectedArticle.setPrixDepart(prix);
                        selectedArticle.setRemise(remise);
                        selectedArticle.setQuantite(qte);
                        selectedArticle.setProduitId(produit);
                        updateArticleCommande(selectedArticle);
                    }
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsArticles.this, ListArticles.class));
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

    private void updateArticleCommande(ArticleCommande articleCommande) {
        Call<ArticleCommande> call = apiInterface.addOrUpdateArticle(articleCommande);
        call.enqueue(
                new Callback<ArticleCommande>() {
                    @Override
                    public void onResponse(Call<ArticleCommande> call, Response<ArticleCommande> response) {
                        if (response.isSuccessful()) {
                            startActivity(new Intent(DetailsArticles.this, ListArticles.class));
                        } else {
                            Toast.makeText(DetailsArticles.this, "L'enregistrement des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArticleCommande> call, Throwable t) {
                        Log.d("DetailsArticles", "Error "+t);
                    }
                }
        );
    }
}