package com.example.bikeshopmobile.activities.article;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.example.bikeshopmobile.R;
import com.example.bikeshopmobile.activities.HomeActivity;
import com.example.bikeshopmobile.entites.ArticleCommande;
import com.example.bikeshopmobile.entites.Reponse;
import com.example.bikeshopmobile.services.ApiService;
import com.example.bikeshopmobile.utils.ApiInterface;
import com.example.bikeshopmobile.utils.RetrofitClient;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListArticles extends AppCompatActivity {

    List<ArticleCommande> articleCommandeList;
    BroadcastReceiver broadcastReceiver;
    ListView listView;
    Button btn1;
    ImageButton btn2;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_articles);

        listView = findViewById(R.id.listView);
        btn1 = findViewById(R.id.addButton);
        btn2 = findViewById(R.id.backButton);
        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && "ACTION_ARTICLES_LOADED".equals(intent.getAction())) {
                    List<ArticleCommande> data = (List<ArticleCommande>) intent.getSerializableExtra("articleCommandeList");
                    if (data != null) {
                        articleCommandeList = data;
                        ArrayAdapter<ArticleCommande> adapter = new ArrayAdapter<ArticleCommande>(ListArticles.this, R.layout.list_tile_layout, R.id.title, articleCommandeList) {
                            @Override
                            public View getView(int position, View convertView, ViewGroup parent) {
                                View view = super.getView(position, convertView, parent);
                                if (view == null) {
                                    LayoutInflater inflater = LayoutInflater.from(getContext());
                                    view = inflater.inflate(R.layout.list_tile_layout, parent, false);
                                }

                                ArticleCommande item = getItem(position);

                                if (item != null) {
                                    TextView elementNameTextView = view.findViewById(R.id.title);
                                    TextView detailsTextView = view.findViewById(R.id.details);
                                    ImageView trashcanIcon = view.findViewById(R.id.trashcanIcon);

                                    elementNameTextView.setText("Article commande " + position);
                                    detailsTextView.setText("Commande " + item.getArticleCommandePK().getNumeroCommande() + " ligne " + item.getArticleCommandePK().getLigne());

                                    view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(ListArticles.this, DetailsArticles.class);
                                            intent.putExtra("selectedArticle", item);
                                            startActivity(intent);
                                        }
                                    });

                                    trashcanIcon.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            deleteArticleCommande(item.getArticleCommandePK().getNumeroCommande(), item.getArticleCommandePK().getLigne());
                                        }
                                    });
                                }

                                return view;
                            }
                        };
                        listView.setAdapter(adapter);
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter("ACTION_ARTICLES_LOADED");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);

        getArticles();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListArticles.this, AddArticle.class));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListArticles.this, HomeActivity.class));
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

    private void getArticles() {
        Intent serviceIntent = new Intent(this, ApiService.class);
        serviceIntent.setAction("getArticles");
        startService(serviceIntent);
    }

    private void deleteArticleCommande(Integer numero, Integer ligne) {
        Call<Reponse> call = apiInterface.deleteArticle(numero, ligne);
        call.enqueue(
                new Callback<Reponse>() {
                    @Override
                    public void onResponse(Call<Reponse> call, Response<Reponse> response) {
                        if (response.isSuccessful()) {
                            getArticles();
                            Toast.makeText(ListArticles.this, "L'article a été supprimé et les données rechargées", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ListArticles.this, "La récupération des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Reponse> call, Throwable t) {
                        Log.d("ListArticles", "Error "+t);
                    }
                }
        );
    }
}
