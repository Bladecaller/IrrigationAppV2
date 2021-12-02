package com.example.sep4_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

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
    private AccountRepoViewModel accountRepoViewModel;
    private PrecipitationViewModel precipitationViewModel;
    List<Account> accs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        buttonSet = findViewById(R.id.location_button);
        buttonBack = findViewById(R.id.backSettings);
        accountRepoViewModel = new ViewModelProvider(this).get(AccountRepoViewModel.class);
        humidityViewModel = new ViewModelProvider(this).get(HumidityViewModel.class);
        temperatureViewModel = new ViewModelProvider(this).get(TemperatureViewModel.class);
        precipitationViewModel = new ViewModelProvider(this).get(PrecipitationViewModel.class);
        spinner = findViewById(R.id.spinner);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Horsens");
        arrayList.add("Aarhus");
        arrayList.add("Attu");
        arrayList.add("Nuuk");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,                         android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        buttonSet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(accs.isEmpty()){
                    accountRepoViewModel.addAccount(new Account(1,"user1","userpass",
                            "userMail","sch","Aarhus","West"));
                }
                accs.get(accs.size()-1).setLocation(spinner.getSelectedItem().toString());
                accountRepoViewModel.addAccount(accs.get(accs.size()-1));
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //humidityViewModel.getHumidity(accs.get(accs.size()-1).getLocation());
                //temperatureViewModel.getTemperature(accs.get(accs.size()-1).getLocation());
                //precipitationViewModel.getPrecipitation(accs.get(accs.size()-1).getLocation());
                Intent intent = new Intent();
                intent.setClass(v.getContext(),HomeActivity.class);
                startActivity(intent);
            }
        });

        accountRepoViewModel.getCurrentAccount().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                if(!accounts.isEmpty()) {
                    accs = accounts;
                    displayToast("Location set to : " + accs.get(accs.size()-1).getLocation());
                }
            }
        });
    }
    public void displayToast(String texts){
        Toast.makeText(this.getApplicationContext(),texts,Toast.LENGTH_SHORT).show();
    }
}
