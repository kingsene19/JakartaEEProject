package com.example.bikeshopmobile.activities.client;

import androidx.appcompat.app.AppCompatActivity;
import com.example.bikeshopmobile.R;
import com.example.bikeshopmobile.entites.Adresse;
import com.example.bikeshopmobile.entites.Client;
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

public class DetailsClients extends AppCompatActivity {

    ApiInterface apiInterface;
    EditText edNom, edPrenom, edEmail, edTel, edCode, edVille, edEtat, edAdresse;
    Button btn1;
    ImageButton btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_clients);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Client selectedClient = (Client) getIntent().getSerializableExtra("selectedClient");
        edNom = findViewById(R.id.nomEditText);
        edPrenom = findViewById(R.id.prenomEditText);
        edEmail = findViewById(R.id.emailEditText);
        edTel = findViewById(R.id.telephoneEditText);
        edCode = findViewById(R.id.codeEditText);
        edVille = findViewById(R.id.villeEditText);
        edEtat = findViewById(R.id.etatEditText);
        edAdresse = findViewById(R.id.adresseEditText);
        btn1 = findViewById(R.id.modifyButton);
        btn2 = findViewById(R.id.backButton);

        edNom.setText(String.valueOf(selectedClient.getNom()));
        edPrenom.setText(String.valueOf(selectedClient.getPrenom()));
        edEmail.setText(String.valueOf(selectedClient.getEmail()));
        edTel.setText(String.valueOf(selectedClient.getTelephone()));
        edCode.setText(String.valueOf(selectedClient.getAdresse().getCodeZip()));
        edVille.setText(String.valueOf(selectedClient.getAdresse().getVille()));
        edAdresse.setText(String.valueOf(selectedClient.getAdresse().getAdresse()));
        edEtat.setText(String.valueOf(selectedClient.getAdresse().getEtat()));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom=edNom.getText().toString();
                String prenom=edPrenom.getText().toString();
                String email=edEmail.getText().toString();
                if (nom==null||prenom==null||email==null) {
                    Toast.makeText(DetailsClients.this, "Le nom, prenom et email sont obligatoires", Toast.LENGTH_SHORT);
                } else {
                    if (selectedClient!=null) {
                        selectedClient.setNom(nom);
                        selectedClient.setPrenom(prenom);
                        selectedClient.setEmail(email);
                        selectedClient.setTelephone(edTel.getText().toString());
                        selectedClient.setAdresse(new Adresse(edAdresse.getText().toString(),edVille.getText().toString(),edEtat.getText().toString(),edCode.getText().toString()));
                        updateClient(selectedClient);
                    }
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsClients.this, ListClients.class));
            }
        });
    }

    private void updateClient(Client client) {
        Call<Client> call = apiInterface.addOrUpdateClient(client);
        call.enqueue(
                new Callback<Client>() {
                    @Override
                    public void onResponse(Call<Client> call, Response<Client> response) {
                        if (response.isSuccessful()) {
                            startActivity(new Intent(DetailsClients.this, ListClients.class));
                        } else {
                            Toast.makeText(DetailsClients.this, "L'enregistrement des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Client> call, Throwable t) {
                        Log.d("DetailsClient", "Error "+t);
                    }
                }
        );
    }
}