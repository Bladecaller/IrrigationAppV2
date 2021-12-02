package com.example.sep4_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.room.entity.Account;
import model.room.entity.Humidity;
import model.room.entity.Precipitation;
import model.room.entity.Temperature;
import viewmodel.AccountRepoViewModel;
import viewmodel.ClimateViewModel;
import viewmodel.HumidityViewModel;
import viewmodel.PrecipitationViewModel;
import viewmodel.TemperatureViewModel;

public class SettingsActivity extends AppCompatActivity {
    EditText location;
    Spinner spinner;
    Button buttonSet;
    Button buttonBack;
    private HumidityViewModel humidityViewModel;
    private TemperatureViewModel temperatureViewModel;
    private PrecipitationViewModel precipitationViewModel;
    private AccountRepoViewModel accountVM;
    private Account acc;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        buttonSet = findViewById(R.id.location_button);
        buttonBack = findViewById(R.id.backSettings);

        rootNode = FirebaseDatabase.getInstance("https://bprcalendarinfo-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = rootNode.getReference("users");

        humidityViewModel = new ViewModelProvider(this).get(HumidityViewModel.class);
        temperatureViewModel = new ViewModelProvider(this).get(TemperatureViewModel.class);
        precipitationViewModel = new ViewModelProvider(this).get(PrecipitationViewModel.class);
        accountVM = new ViewModelProvider(this).get(AccountRepoViewModel.class);

        accountVM.getCurrentAccount().observe(this, new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                if(account != null){
                    acc = account;
                }
            }
        });

        acc = accountVM.getCurrentAccount().getValue();

        spinner = findViewById(R.id.spinner);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Horsens");
        arrayList.add("Aarhus");
        arrayList.add("Attu");
        arrayList.add("Nuuk");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);



        buttonSet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(acc.getUsername() != "Default"){
                    reference.child(acc.getUsername()).child("userInfo").child("location").setValue(spinner.getSelectedItem().toString());
                    System.out.println("Location set");
                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(),HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    public void displayToast(String texts){
        Toast.makeText(this.getApplicationContext(),texts,Toast.LENGTH_SHORT).show();
    }
}
