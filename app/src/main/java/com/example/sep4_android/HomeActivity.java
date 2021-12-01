package com.example.sep4_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import model.room.entity.Account;
import model.room.entity.Climate;
import model.room.entity.Humidity;
import model.room.entity.Precipitation;
import model.room.entity.Temperature;
import viewmodel.AccountRepoViewModel;
import viewmodel.ClimateViewModel;
import viewmodel.HumidityViewModel;
import viewmodel.PrecipitationViewModel;
import viewmodel.TemperatureViewModel;

public class HomeActivity extends AppCompatActivity {
    private HumidityViewModel humidityViewModel;
    private TemperatureViewModel temperatureViewModel;
    private AccountRepoViewModel accountRepoViewModel;
    private PrecipitationViewModel precipitationViewModel;
    private String location;

    private TextView humidityDisplay;
    private TextView temperatureDisplay;
    private TextView precipitationDisplay;

    private List<Account> account;

    private Button settingsButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        humidityDisplay = findViewById(R.id.humidityDisplay);
        precipitationDisplay = findViewById(R.id.precipitationDisplay);
        temperatureDisplay = findViewById(R.id.temperatureDisplay);
        settingsButton = findViewById(R.id.settings_home);
        account = new ArrayList<>();


        humidityViewModel = new ViewModelProvider(this).get(HumidityViewModel.class);
        temperatureViewModel = new ViewModelProvider(this).get(TemperatureViewModel.class);
        precipitationViewModel = new ViewModelProvider(this).get(PrecipitationViewModel.class);
        accountRepoViewModel = new ViewModelProvider(this).get(AccountRepoViewModel.class);

        if(account.isEmpty()){
            location = "Horsens";
        }
        accountRepoViewModel.getCurrentAccount();
        //humidityViewModel.getHumidity(location);
        //temperatureViewModel.getTemperature(location);
        //precipitationViewModel.getPrecipitation(location);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(),SettingsActivity.class);
                startActivity(intent);
            }
        });

        humidityViewModel.getHumidity(location).observe(this, new Observer<List<Humidity>>() {
            @Override
            public void onChanged(List<Humidity> humidities) {
                if(!humidities.isEmpty()){
                    humidityDisplay.setText("Humidity: "+String.valueOf(humidities.get(humidities.size()-1).getValue()));

                }else {
                    humidityDisplay.setText("Empty, calling for data");
                    //humidityViewModel.getHumidity("Horsens");
                }
            }
        });


        temperatureViewModel.getTemperature(location).observe(this, new Observer<List<Temperature>>() {
            @Override
            public void onChanged(List<Temperature> temperatures) {
                if(!temperatures.isEmpty()){
                    temperatureDisplay.setText("Temperature: "+String.valueOf(temperatures.get(temperatures.size()-1).getValue()));

                }else {
                    temperatureDisplay.setText("Empty, calling for data");
                    //temperatureViewModel.getTemperature("Horsens");
                }
            }
        });

        precipitationViewModel.getPrecipitation(location).observe(this, new Observer<List<Precipitation>>() {
            @Override
            public void onChanged(List<Precipitation> precipitations) {
                if(!precipitations.isEmpty()){
                    precipitationDisplay.setText("Precipitation: "+String.valueOf(precipitations.get(precipitations.size()-1).getValue()));

                }else {
                    precipitationDisplay.setText("Empty, calling for data");
                    //precipitationViewModel.getPrecipitation("Horsens");
                }
            }
        });

        accountRepoViewModel.getCurrentAccount().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                if(!accounts.isEmpty()){
                    account = accounts;
                    location = account.get(account.size()-1).getLocation();
                    humidityViewModel.getHumidity(location);
                    temperatureViewModel.getTemperature(location);
                    precipitationViewModel.getPrecipitation(location);
                }
            }
        });
    }
}
