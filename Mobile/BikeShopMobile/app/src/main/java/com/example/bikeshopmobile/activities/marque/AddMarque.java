package com.example.bikeshopmobile.activities.marque;

import androidx.appcompat.app.AppCompatActivity;
import com.example.bikeshopmobile.R;
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

public class AddMarque extends AppCompatActivity {

    EditText nomEditText;
    Button btn1;
    ImageButton btn2;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marque);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        nomEditText = findViewById(R.id.nomEditText);
        btn1 = findViewById(R.id.addButton);
        btn2 = findViewById(R.id.backButton);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom=nomEditText.getText().toString();
                if (nom==null) {
                    Toast.makeText(AddMarque.this, "Le champ nom est obligatoire", Toast.LENGTH_SHORT);
                } else {
                    Marque marque = new Marque();
                    marque.setNom(nom);
                    addMarque(marque);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddMarque.this, ListMarques.class));
            }
        });
    }

    private void addMarque(Marque marque) {
        Call<Marque> call = apiInterface.addOrUpdateMarque(marque);
        call.enqueue(
                new Callback<Marque>() {
                    @Override
                    public void onResponse(Call<Marque> call, Response<Marque> response) {
                        if (response.isSuccessful()) {
                            startActivity(new Intent(AddMarque.this, ListMarques.class));
                        } else {
                            Toast.makeText(AddMarque.this, "L'enregistrement des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Marque> call, Throwable t) {
                        Log.d("AddMarques", "Error "+t);
                    }
                }
        );
    }
}