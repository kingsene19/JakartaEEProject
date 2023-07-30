package com.example.bikeshopmobile.activities.magasin;

import androidx.appcompat.app.AppCompatActivity;
import com.example.bikeshopmobile.R;
import com.example.bikeshopmobile.entites.Adresse;
import com.example.bikeshopmobile.entites.Magasin;
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

public class DetailsMagasins extends AppCompatActivity {

    ApiInterface apiInterface;
    EditText edNom, edEmail, edTel, edCode, edVille, edEtat, edAdresse;
    Button btn1;
    ImageButton btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_magasins);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Magasin selectedMagasin = (Magasin) getIntent().getSerializableExtra("selectedMagasin");
        edNom = findViewById(R.id.nomEditText);
        edEmail = findViewById(R.id.emailEditText);
        edTel = findViewById(R.id.telephoneEditText);
        edCode = findViewById(R.id.codeEditText);
        edVille = findViewById(R.id.villeEditText);
        edEtat = findViewById(R.id.etatEditText);
        edAdresse = findViewById(R.id.adresseEditText);
        btn1 = findViewById(R.id.modifyButton);

        edNom.setText(String.valueOf(selectedMagasin.getNom()));
        edEmail.setText(String.valueOf(selectedMagasin.getEmail()));
        edTel.setText(String.valueOf(selectedMagasin.getTelephone()));
        edCode.setText(String.valueOf(selectedMagasin.getAdresse().getCodeZip()));
        edVille.setText(String.valueOf(selectedMagasin.getAdresse().getVille()));
        edAdresse.setText(String.valueOf(selectedMagasin.getAdresse().getAdresse()));
        edEtat.setText(String.valueOf(selectedMagasin.getAdresse().getEtat()));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom =edNom.getText().toString();
                String email=edEmail.getText().toString();
                String telephone=edTel.getText().toString();
                if (nom==null||email==null||telephone==null) {
                Toast.makeText(DetailsMagasins.this, "Le nom, email et téléphone sont obligatoires", Toast.LENGTH_SHORT);
                } else {
                    if (selectedMagasin!=null) {
                        selectedMagasin.setNom(nom);
                        selectedMagasin.setEmail(email);
                        selectedMagasin.setTelephone(telephone);
                        selectedMagasin.setAdresse(new Adresse(edAdresse.getText().toString(),edVille.getText().toString(),edEtat.getText().toString(),edCode.getText().toString()));
                        updateMagasin(selectedMagasin);
                    }
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsMagasins.this, ListMagasins.class));
            }
        });
    }

    private void updateMagasin(Magasin Magasin) {
        Call<Magasin> call = apiInterface.addOrUpdateMagasin(Magasin);
        call.enqueue(
                new Callback<Magasin>() {
                    @Override
                    public void onResponse(Call<Magasin> call, Response<Magasin> response) {
                        if (response.isSuccessful()) {
                            startActivity(new Intent(DetailsMagasins.this, ListMagasins.class));
                        } else {
                            Toast.makeText(DetailsMagasins.this, "L'enregistrement des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Magasin> call, Throwable t) {
                        Log.d("DetailsMagasin", "Error "+t);
                    }
                }
        );
    }
}