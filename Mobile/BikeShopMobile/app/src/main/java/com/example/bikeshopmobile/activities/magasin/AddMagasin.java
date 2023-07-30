package com.example.bikeshopmobile.activities.magasin;

import androidx.appcompat.app.AppCompatActivity;
import com.example.bikeshopmobile.R;
import com.example.bikeshopmobile.activities.employe.AddEmploye;
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

public class AddMagasin extends AppCompatActivity {

    ApiInterface apiInterface;
    EditText edNom, edEmail, edTel, edCode, edVille, edEtat, edAdresse;
    Button btn1;
    ImageButton btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_magasin);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        edNom = findViewById(R.id.nomEditText);
        edEmail = findViewById(R.id.emailEditText);
        edTel = findViewById(R.id.telephoneEditText);
        edCode = findViewById(R.id.codeEditText);
        edVille = findViewById(R.id.villeEditText);
        edEtat = findViewById(R.id.etatEditText);
        edAdresse = findViewById(R.id.adresseEditText);
        btn1 = findViewById(R.id.addButton);
        btn2 = findViewById(R.id.backButton);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom=edNom.getText().toString();
                String email=edEmail.getText().toString();
                String telephone=edTel.getText().toString();
                if (nom==null||email==null||telephone==null) {
                    Toast.makeText(AddMagasin.this, "Le nom, email et telephone sont obligatoires", Toast.LENGTH_SHORT);
                } else {
                    Magasin magasin = new Magasin();
                    magasin.setNom(nom);
                    magasin.setEmail(email);
                    magasin.setTelephone(telephone);
                    magasin.setAdresse(new Adresse(edAdresse.getText().toString(),edVille.getText().toString(),edEtat.getText().toString(),edCode.getText().toString()));
                    addMagasin(magasin);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddMagasin.this, ListMagasins.class));
            }
        });
    }

    private void addMagasin(Magasin Magasin) {
        Call<Magasin> call = apiInterface.addOrUpdateMagasin(Magasin);
        call.enqueue(
                new Callback<Magasin>() {
                    @Override
                    public void onResponse(Call<Magasin> call, Response<Magasin> response) {
                        if (response.isSuccessful()) {
                            startActivity(new Intent(AddMagasin.this, ListMagasins.class));
                        } else {
                            Toast.makeText(AddMagasin.this, "L'enregistrement des données a échoué", Toast.LENGTH_SHORT).show();
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