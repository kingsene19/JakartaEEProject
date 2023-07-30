package com.example.bikeshopmobile.activities.stock;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bikeshopmobile.Database;
import com.example.bikeshopmobile.R;
import com.example.bikeshopmobile.activities.HomeActivity;
import com.example.bikeshopmobile.entites.Reponse;
import com.example.bikeshopmobile.entites.Stock;
import com.example.bikeshopmobile.utils.ApiInterface;
import com.example.bikeshopmobile.utils.ConnectionManager;
import com.example.bikeshopmobile.utils.RetrofitClient;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListStocks extends AppCompatActivity implements ConnectionManager.OnConnectionCheckListener{

    List<Stock> stockList;
    ListView listView;
    ApiInterface apiInterface;
    Button btn1;
    ImageButton btn2;
    Database db = new Database(this, "BikeShop", null,1);
    private ConnectionManager connectionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_stocks);

        listView = findViewById(R.id.listView);
        btn1 = findViewById(R.id.addButton);
        btn2 = findViewById(R.id.backButton);
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        connectionManager = new ConnectionManager(this);
        connectionManager.setOnConnectionCheckListener(this);
        connectionManager.checkApiAccess();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListStocks.this, AddStock.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListStocks.this, HomeActivity.class));
            }
        });
    }

    private void getStocks() {
        stockList = db.getAllEntries();
        ArrayAdapter<Stock> adapter = new ArrayAdapter<Stock>(ListStocks.this, R.layout.list_tile_layout, R.id.title, stockList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if (view == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    view = inflater.inflate(R.layout.list_tile_layout, parent, false);
                }

                Stock item = getItem(position);

                if (item != null) {
                    TextView elementNameTextView = view.findViewById(R.id.title);
                    TextView detailsTextView = view.findViewById(R.id.details);
                    ImageView trashcanIcon = view.findViewById(R.id.trashcanIcon);

                    elementNameTextView.setText(item.getProduitId().getNom().substring(0, 10));
                    detailsTextView.setText("Magasin: " + item.getMagasinId().getNom().substring(0, 10) + " Quantite: " + item.getQuantite());

                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ListStocks.this, DetailsStocks.class);
                            intent.putExtra("selectedStock", item);
                            startActivity(intent);
                        }
                    });

                    trashcanIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteStock(item.getStockPK().getMagasinId(), item.getStockPK().getProduitId());
                        }
                    });
                }

                return view;
            }
        };
        listView.setAdapter(adapter);
    }

    private void deleteStock(Integer magasin_id, Integer produit_id) {
        db.delete(magasin_id, produit_id);
        startActivity(new Intent(ListStocks.this, ListStocks.class));
    }

    @Override
    public void onConnectionChecked(boolean isAccessible) {
        if (isAccessible) {
            List<Stock> deletedStocks = db.getDeletedStocks();
            if (deletedStocks!=null) {
                for (Stock s: deletedStocks) {
                    Call<Reponse> call = apiInterface.deleteStock(s.getStockPK().getMagasinId(), s.getStockPK().getProduitId());
                    call.enqueue(new Callback<Reponse>() {
                        @Override
                        public void onResponse(Call<Reponse> call, Response<Reponse> response) {
                            db.removeAfterSyncDelete(s.getStockPK().getProduitId(), s.getStockPK().getMagasinId());
                        }

                        @Override
                        public void onFailure(Call<Reponse> call, Throwable t) {
                            Toast.makeText(ListStocks.this, "La synchronisation a échouée", Toast.LENGTH_SHORT);
                        }
                    });
                }
            }
            List<Stock> stocks = db.getUpdatedOrAddedStocks();
            if (stocks!=null) {
                for (Stock s: stocks) {
                    Call<Stock> call = apiInterface.addOrUpdateStock(s);
                    call.enqueue(new Callback<Stock>() {
                        @Override
                        public void onResponse(Call<Stock> call, Response<Stock> response) {
                            db.removeAfterSyncAddOrUpdate(s.getStockPK().getProduitId(), s.getStockPK().getMagasinId());
                        }

                        @Override
                        public void onFailure(Call<Stock> call, Throwable t) {
                            Toast.makeText(ListStocks.this,"La synchornisation a échoué", Toast.LENGTH_SHORT);
                        }
                    });
                }
            }
        } else {
            Log.d("ListStock", "Connection Not Available");
        }
        getStocks();
    }
}