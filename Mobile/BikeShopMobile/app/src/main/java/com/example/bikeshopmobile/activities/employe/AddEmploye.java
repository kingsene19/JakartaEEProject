package com.example.bikeshopmobile.activities.employe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.example.bikeshopmobile.R;
import com.example.bikeshopmobile.entites.Adresse;
import com.example.bikeshopmobile.entites.Magasin;
import com.example.bikeshopmobile.entites.Employe;
import com.example.bikeshopmobile.services.ApiService;
import com.example.bikeshopmobile.utils.ApiInterface;
import com.example.bikeshopmobile.utils.CustomSpinnerAdapter;
import com.example.bikeshopmobile.utils.RetrofitClient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmploye extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;
    List<Magasin> magasinList;
    List<Employe> employeList;
    List<String> magasinListString =  new ArrayList<>();
    List<String> employeListString =  new ArrayList<>();
    ApiInterface apiInterface;
    EditText edNom, edPrenom, edEmail, edTel, edCode, edVille, edEtat, edAdresse, edActif;
    Button btn1;
    ImageButton btn2;
    int selectedMagasinPosition, selectedEmployePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employe);

        apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Employe employe = (Employe) getIntent().getSerializableExtra("employe");
        edNom = findViewById(R.id.nomEditText);
        edPrenom = findViewById(R.id.prenomEditText);
        edEmail = findViewById(R.id.emailEditText);
        edTel = findViewById(R.id.telephoneEditText);
        edCode = findViewById(R.id.codeEditText);
        edVille = findViewById(R.id.villeEditText);
        edEtat = findViewById(R.id.etatEditText);
        edAdresse = findViewById(R.id.adresseEditText);
        edActif = findViewById(R.id.actifEditText);
        btn1 = findViewById(R.id.addButton);
        btn2 = findViewById(R.id.backButton);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent != null && "ACTION_MAGASINS_LOADED".equals(intent.getAction())) {
                    List<Magasin> data = (List<Magasin>) intent.getSerializableExtra("magasinList");
                    if (data != null) {
                        magasinList = data;
                        for (Magasin m: magasinList) {
                            magasinListString.add(m.getNom());
                        }
                        Spinner magasinSpinner = findViewById(R.id.magasinSpinner);
                        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(AddEmploye.this, R.layout.spinner_item, magasinListString);
                        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
                        magasinSpinner.setAdapter(spinnerAdapter);
                        if (employe != null) {
                            selectedMagasinPosition = getMagasinPosition(employe.getMagasinId().getNom());
                            magasinSpinner.setSelection(selectedMagasinPosition);
                        }
                        magasinSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                View selectedItem = parent.getSelectedView();
                                if (selectedItem != null) {
                                    selectedItem.setBackgroundColor(getResources().getColor(R.color.bg_dark_subtle));
                                    String selectedMagasin = (String) parent.getItemAtPosition(position);
                                    selectedMagasinPosition= getMagasinPosition(selectedMagasin);
                                    magasinSpinner.setSelection(selectedMagasinPosition);
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
                        Spinner employeSpinner = findViewById(R.id.managerSpinner);
                        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(AddEmploye.this, R.layout.spinner_item, employeListString);
                        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
                        employeSpinner.setAdapter(spinnerAdapter);
                        if (employe != null) {
                            selectedEmployePosition= getEmployePosition(employe.getNom()+" "+employe.getPrenom());
                            employeSpinner.setSelection(selectedEmployePosition);
                        }
                        employeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                View selectedItem = parent.getSelectedView();
                                if (selectedItem != null) {
                                    selectedItem.setBackgroundColor(getResources().getColor(R.color.bg_dark_subtle));
                                    String employe = (String) parent.getItemAtPosition(position);
                                    selectedEmployePosition= getEmployePosition(employe);
                                    employeSpinner.setSelection(selectedEmployePosition);
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

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nom =edNom.getText().toString();
                String prenom=edPrenom.getText().toString();
                String email=edEmail.getText().toString();
                Magasin magasin=magasinList.get(selectedMagasinPosition);
                Short actif=Short.parseShort(edActif.getText().toString());
                if (nom==null||prenom==null||email==null||actif==null||magasin==null) {
                    Toast.makeText(AddEmploye.this,"Le nom, prenom, email, actif et le magasin sont obligatoires", Toast.LENGTH_SHORT);
                } else {
                    Employe employe = new Employe();
                    employe.setNom(nom);
                    employe.setPrenom(prenom);
                    employe.setEmail(email);
                    employe.setTelephone(edTel.getText().toString());
                    employe.setActif(actif);
                    employe.setAdresse(new Adresse(edAdresse.getText().toString(),edVille.getText().toString(),edEtat.getText().toString(),edCode.getText().toString()));
                    employe.setMagasinId(magasin);
                    employe.setManagerId(employeList.get(selectedEmployePosition));
                    addEmploye(employe);
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddEmploye.this, ListEmployes.class));
            }
        });

        IntentFilter filter1 = new IntentFilter("ACTION_MAGASINS_LOADED");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter1);
        IntentFilter filter2 = new IntentFilter("ACTION_EMPLOYES_LOADED");
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter2);
        getMagasins();
        getEmployes();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
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

    private int getEmployePosition(String s) {
        if (s != null && employeList != null) {
            for (int i = 0; i < employeList.size(); i++) {
                if (s.equals(employeList.get(i).getNom()+" "+employeList.get(i).getPrenom())) {
                    return i;
                }
            }
        }
        return 0;
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

    private void addEmploye(Employe employe) {
        Call<Employe> call = apiInterface.addOrUpdateEmploye(employe);
        call.enqueue(
                new Callback<Employe>() {
                    @Override
                    public void onResponse(Call<Employe> call, Response<Employe> response) {
                        if (response.isSuccessful()) {
                            startActivity(new Intent(AddEmploye.this, ListEmployes.class));
                        } else {
                            Toast.makeText(AddEmploye.this, "L'enregistrement des données a échoué", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Employe> call, Throwable t) {
                        Log.d("AddEmploye", "Error "+t);
                    }
                }
        );
    }
}