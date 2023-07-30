package com.example.bikeshopmobile.activities.marque;

import androidx.appcompat.app.AppCompatActivity;
import com.example.bikeshopmobile.R;
import com.example.bikeshopmobile.activities.magasin.DetailsMagasins;
import com.example.bikeshopmobile.entites.Marque;
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

public class DetailsMarques extends AppCompatActivity {

    EditText nomEditText;
    Button btn1;
    ImageButton btn2;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_marques);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Marque selectedMarque = (Marque) getIntent().getSerializableExtra("selectedMarque");
        nomEditText = findViewById(R.id.nomEditText);
        btn1 = findViewById(R.id.modifyButton);
        btn2 = findViewById(R.id.backButton);

        nomEditText.setText(String.valueOf(selectedMarque.getNom()));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom=nomEditText.getText().toString();
                if (nom==null) {
                    Toast.makeText(DetailsMarques.this, "Le champ nom est obligatoire", Toast.LENGTH_SHORT);
                } else {
                    if (selectedMarque!=null) {
                        selectedMarque.setNom(nom);
                        updateMarque(selectedMarque);
                    }
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsMarques.this, ListMarques.class));
            }
        });
    }

    private void updateMarque(Marque marque) {
        Call<Marque> call = apiInterface.addOrUpdateMarque(marque);
        call.enqueue(
                new Callback<Marque>() {
                    @Override
                    public void onResponse(Call<Marque> call, Response<Marque> response) {
                        if (response.isSuccessful()) {
                            startActivity(new Intent(DetailsMarques.this, ListMarques.class));
                        } else {
                            Toast.makeText(DetailsMarques.this, "L'enregistrement des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Marque> call, Throwable t) {
                        Log.d("DetailsMarques", "Error "+t);
                    }
                }
        );
    }
}