package com.example.bikeshopmobile.activities.commande;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.bikeshopmobile.R;
import com.example.bikeshopmobile.entites.Commande;
import com.example.bikeshopmobile.entites.Client;
import com.example.bikeshopmobile.entites.Employe;
import com.example.bikeshopmobile.entites.Magasin;
import com.example.bikeshopmobile.services.ApiService;
import com.example.bikeshopmobile.utils.ApiInterface;
import com.example.bikeshopmobile.utils.CustomSpinnerAdapter;
import com.example.bikeshopmobile.utils.RetrofitClient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCommande extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;
    List<Magasin> magasinList;
    List<Employe> employeList;
    List<Client> clientList;
    List<String> magasinListString = new ArrayList<>() ;
    List<String> clientListString = new ArrayList<>();
    List<String> employeListString =  new ArrayList<>();
    EditText edCommande, edVoulue, edStatut, edLivraision;
    Button btn1;
    ImageButton btn2;
    ApiInterface apiInterface;
    int selectedPositionClient, selectedPositionEmploye, selectedPositionMagasin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_commande);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        edCommande = findViewById(R.id.dateCommandeEditText);
        edVoulue = findViewById(R.id.dateVoulueEditText);
        edStatut = findViewById(R.id.statutEditText);
        edLivraision = findViewById(R.id.livraisonEditText);
        btn1 = findViewById(R.id.addButton);
        btn2 = findViewById(R.id.backButton);

        broadcastReceiver =  new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && "ACTION_CLIENTS_LOADED".equals(intent.getAction())) {
                    List<Client> data = (List<Client>) intent.getSerializableExtra("clientList");
                    if (data != null) {
                        clientList = data;
                        for (Client c: clientList) {
                            clientListString.add(c.getNom()+" "+c.getPrenom());
                        }
                        Spinner clientSpinner = findViewById(R.id.clientSpinner);
                        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(AddCommande.this, R.layout.spinner_item, clientListString);
                        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
                        clientSpinner.setAdapter(spinnerAdapter);
                        clientSpinner.setSelection(0);
                        clientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                View selectedItem = parent.getSelectedView();
                                if (selectedItem != null) {
                                    selectedItem.setBackgroundColor(getResources().getColor(R.color.bg_dark_subtle));
                                    String selectedClient = (String) parent.getItemAtPosition(position);
                                    selectedPositionClient = getClientPosition(selectedClient);
                                    clientSpinner.setSelection(selectedPositionClient);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                } else if (intent != null && "ACTION_EMPLOYES_LOADED".equals(intent.getAction())) {
                    List<Employe> data = (List<Employe>) intent.getSerializableExtra("employeList");
                    if (data != null) {
                        employeList = data;
                        for (Employe e: employeList) {
                            employeListString.add(e.getNom()+" "+e.getPrenom());
                        }
                        Spinner employeSpinner = findViewById(R.id.vendeurSpinner);
                        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(AddCommande.this, R.layout.spinner_item, employeListString);
                        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
                        employeSpinner.setAdapter(spinnerAdapter);
                        employeSpinner.setSelection(0);
                        employeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                View selectedItem = parent.getSelectedView();
                                if (selectedItem != null) {
                                    selectedItem.setBackgroundColor(getResources().getColor(R.color.bg_dark_subtle));
                                    String selectedEmploye = (String) parent.getItemAtPosition(position);
                                    selectedPositionEmploye = getEmployePosition(selectedEmploye);
                                    employeSpinner.setSelection(selectedPositionEmploye);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });

                    }
                } else if (intent != null && "ACTION_MAGASINS_LOADED".equals(intent.getAction())) {
                    List<Magasin> data = (List<Magasin>) intent.getSerializableExtra("magasinList");
                    if (data != null) {
                        magasinList = data;
                        for (Magasin m: magasinList) {
                            magasinListString.add(m.getNom());
                        }
                        Spinner magasinSpinner = findViewById(R.id.magasinSpinner);
                        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(AddCommande.this, R.layout.spinner_item, magasinListString);
                        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
                        magasinSpinner.setAdapter(spinnerAdapter);
                        magasinSpinner.setSelection(0);
                        magasinSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                View selectedItem = parent.getSelectedView();
                                if (selectedItem != null) {
                                    selectedItem.setBackgroundColor(getResources().getColor(R.color.bg_dark_subtle));
                                    String selectedMagasin = (String) parent.getItemAtPosition(position);
                                    selectedPositionMagasin = getMagasinPosition(selectedMagasin);
                                    magasinSpinner.setSelection(selectedPositionMagasin);
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

        IntentFilter filter1 = new IntentFilter("ACTION_CLIENTS_LOADED");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter1);
        IntentFilter filter2 = new IntentFilter("ACTION_EMPLOYES_LOADED");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter2);
        IntentFilter filter3 = new IntentFilter("ACTION_MAGASINS_LOADED");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter3);
        getClients();
        getEmployes();
        getMagasins();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Magasin magasin = magasinList.get(selectedPositionMagasin);
                Client client = clientList.get(selectedPositionClient);
                Employe vendeur = employeList.get(selectedPositionEmploye);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String dateCommande = convertLocalDateToString(LocalDate.parse(edCommande.getText().toString()));
                    String dateVoulue = convertLocalDateToString(LocalDate.parse(edVoulue.getText().toString()));
                    if (magasin==null||client==null||vendeur==null||dateCommande==null||dateVoulue==null) {
                        Toast.makeText(AddCommande.this,"Les champs magasin, client, vendeur, dateCommande et dateVoulue sont obligatoires", Toast.LENGTH_SHORT);
                    } else {
                        Commande commande = new Commande();
                        commande.setDateCommande(dateCommande);
                        commande.setDateLivraisonVoulue(dateVoulue);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            commande.setDateLivraison(convertLocalDateToString(LocalDate.parse(edLivraision.getText().toString())));
                        }
                        commande.setStatut(Short.parseShort(edStatut.getText().toString()));
                        commande.setMagasinId(magasin);
                        commande.setClientId(client);
                        commande.setVendeurId(vendeur);
                        addCommande(commande);
                    }
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddCommande.this, ListCommandes.class));
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

    private int getClientPosition(String s) {
        if (s != null && clientList != null) {
            for (int i = 0; i < clientList.size(); i++) {
                if (s.equals(clientList.get(i).getNom() + " " + clientList.get(i).getPrenom())) {
                    return i;
                }
            }
        }
        return 0;
    }

    private int getEmployePosition(String s) {
        if (s != null && employeList != null) {
            for (int i = 0; i < employeList.size(); i++) {
                if (s.equals(employeList.get(i).getNom() + " " + employeList.get(i).getPrenom())) {
                    return i;
                }
            }
        }
        return 0;
    }

    private int getMagasinPosition(String s) {
        if (s != null && magasinList != null) {
            for (int i = 0; i < magasinList.size(); i++) {
                if (s.equals(magasinList.get(i).getNom())) {
                    return i;
                }
            }
        }
        return 0;
    }

    private void getClients() {
        Intent serviceIntent = new Intent(this, ApiService.class);
        serviceIntent.setAction("getClients");
        startService(serviceIntent);
    }

    private void getMagasins() {
        Intent serviceIntent = new Intent(this, ApiService.class);
        serviceIntent.setAction("getMagasins");
        startService(serviceIntent);
    }

    private void getEmployes() {
        Intent serviceIntent = new Intent(this, ApiService.class);
        serviceIntent.setAction("getEmployes");
        startService(serviceIntent);
    }

    private void addCommande(Commande commande) {
        Call<Commande> call = apiInterface.addOrUpdateCommande(commande);
        call.enqueue(
                new Callback<Commande>() {
                    @Override
                    public void onResponse(Call<Commande> call, Response<Commande> response) {
                        if (response.isSuccessful()) {
                            startActivity(new Intent(AddCommande.this, ListCommandes.class));
                        } else {
                            Toast.makeText(AddCommande.this, "L'enregistrement des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Commande> call, Throwable t) {
                        Log.d("AddCommande", "Error "+t);
                    }
                }
        );
    }

    public String convertLocalDateToString(LocalDate localDate) {
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return localDate.format(formatter);
        }
        return null;
    }

}