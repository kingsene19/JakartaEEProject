package com.example.bikeshopmobile.activities.categorie;

import androidx.appcompat.app.AppCompatActivity;
import com.example.bikeshopmobile.R;
import com.example.bikeshopmobile.activities.article.ListArticles;
import com.example.bikeshopmobile.entites.Categorie;
import com.example.bikeshopmobile.utils.ApiInterface;
import com.example.bikeshopmobile.utils.RetrofitClient;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsCategories extends AppCompatActivity {

    EditText nomEditText;
    Button btn1;
    ImageButton btn2;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_categories);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Categorie selectedCategorie = (Categorie) getIntent().getSerializableExtra("selectedCategorie");
        nomEditText = findViewById(R.id.nomEditText);
        btn1 = findViewById(R.id.modifyButton);
        btn2 = findViewById(R.id.backButton);

        nomEditText.setText(String.valueOf(selectedCategorie.getNom()));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom = nomEditText.getText().toString();
                if (nom==null) {
                    Toast.makeText(DetailsCategories.this, "Le nom est obligatoire", Toast.LENGTH_SHORT);
                } else {
                    if (selectedCategorie!=null) {
                        selectedCategorie.setNom(nom);
                        updateCategorie(selectedCategorie);
                    }
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsCategories.this, ListCategories.class));
            }
        });

    }

    private void updateCategorie(Categorie categorie) {
        Call<Categorie> call = apiInterface.addOrUpdateCategorie(categorie);
        call.enqueue(
                new Callback<Categorie>() {
                    @Override
                    public void onResponse(Call<Categorie> call, Response<Categorie> response) {
                        if (response.isSuccessful()) {
                            startActivity(new Intent(DetailsCategories.this, ListCategories.class));
                        } else {
                            Toast.makeText(DetailsCategories.this, "L'enregistrement des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Categorie> call, Throwable t) {
                        Log.d("DetailsCategorie", "Error "+t);
                    }
                }
        );
    }
}