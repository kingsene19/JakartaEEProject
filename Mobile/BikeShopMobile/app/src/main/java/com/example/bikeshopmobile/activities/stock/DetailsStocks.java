package com.example.bikeshopmobile.activities.stock;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bikeshopmobile.Database;
import com.example.bikeshopmobile.R;
import com.example.bikeshopmobile.entites.Stock;
import com.example.bikeshopmobile.utils.ApiInterface;
import com.example.bikeshopmobile.utils.ConnectionManager;
import com.example.bikeshopmobile.utils.RetrofitClient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsStocks extends AppCompatActivity {

    EditText quantiteEditText;
    Button btn1;
    ImageButton btn2;
    ApiInterface apiInterface;
    Database db = new Database(this, "BikeShop", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_stocks);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Stock selectedStock = (Stock) getIntent().getSerializableExtra("selectedStock");
        quantiteEditText = findViewById(R.id.quantiteEditText);
        btn1 = findViewById(R.id.modifyButton);
        btn2 = findViewById(R.id.backButton);

        quantiteEditText.setText(String.valueOf(selectedStock.getQuantite()));

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer qte = Integer.parseInt(quantiteEditText.getText().toString());
                if (qte==null) {
                    Toast.makeText(DetailsStocks.this, "Le champ quantite est obligatoire", Toast.LENGTH_SHORT);
                } else {
                    if (selectedStock!=null) {
                        selectedStock.setQuantite(qte);
                        updateStock(selectedStock);
                    }
                }

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailsStocks.this, ListStocks.class));
            }
        });
    }

    private void updateStock(Stock stock) {
        db.update(stock);
        startActivity(new Intent(DetailsStocks.this, ListStocks.class));
    }
}