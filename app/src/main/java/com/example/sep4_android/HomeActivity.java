package com.example.sep4_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.List;

import model.room.entity.Account;
import model.room.entity.Electricity;
import model.room.entity.Humidity;
import model.room.entity.Precipitation;
import model.room.entity.Temperature;
import model.room.repositories.ElectricityRepo;
import viewmodel.AccountRepoViewModel;
import viewmodel.HumidityViewModel;
import viewmodel.PrecipitationViewModel;
import viewmodel.TemperatureViewModel;

public class HomeActivity extends AppCompatActivity {
    private HumidityViewModel humidityViewModel;
    private TemperatureViewModel temperatureViewModel;
    private PrecipitationViewModel precipitationViewModel;
    private AccountRepoViewModel accountVM;
    private Account acc;
    private Electricity price;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    private TextView humidityDisplay;
    private TextView temperatureDisplay;
    private TextView precipitationDisplay,electricityPriceDisplay;
    private String username, location, accPrice;

    private Button settingsButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        humidityDisplay = findViewById(R.id.humidityDisplay);
        precipitationDisplay = findViewById(R.id.precipitationDisplay);
        temperatureDisplay = findViewById(R.id.temperatureDisplay);
        settingsButton = findViewById(R.id.settings_home);
        electricityPriceDisplay = findViewById(R.id.electricityPrice);
        humidityViewModel = new ViewModelProvider(this).get(HumidityViewModel.class);
        temperatureViewModel = new ViewModelProvider(this).get(TemperatureViewModel.class);
        precipitationViewModel = new ViewModelProvider(this).get(PrecipitationViewModel.class);
        accountVM = new ViewModelProvider(this).get(AccountRepoViewModel.class);
        rootNode = FirebaseDatabase.getInstance("https://bprcalendarinfo-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = rootNode.getReference("users");
        Bundle extras = getIntent().getExtras();


        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(),SettingsActivity.class);
                startActivity(intent);
            }
        });

        if (extras != null && extras.containsKey("username")) {
            username = extras.getString("username");
            acc = new Account(9999,username);
            System.out.println("ACC IS NOT NULL");
        }

        if(acc != null){
            accountVM.addAccount(acc);
            System.out.println("CALLED FOR ACCOUNT ADDITION");
        }

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    System.out.println("Username is : "+ acc.getUsername());
                    Object locationObj = dataSnapshot.child(acc.getUsername()).child("userInfo").child("location").getValue();
                    Object priceObj = dataSnapshot.child(acc.getUsername()).child("userInfo").child("electricityLocation").getValue();
                    location = String.valueOf(locationObj);
                    accPrice = String.valueOf(priceObj);
                    System.out.println("Calling price value is :"+accPrice);
                    accountVM.updateElectricityPrice(accPrice);
                    humidityViewModel.getHumidity(location);
                    temperatureViewModel.getTemperature(location);
                    precipitationViewModel.getPrecipitation(location);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR IS HERE", databaseError.getMessage());
            }
        };


        humidityViewModel.getHumidity(location).observe(this, new Observer<List<Humidity>>() {
            @Override
            public void onChanged(List<Humidity> humidities) {
                if(!humidities.isEmpty()){
                    humidityDisplay.setText("Humidity: "+String.valueOf(humidities.get(humidities.size()-1).getValue()));
                }else {
                    humidityDisplay.setText("Empty, calling for data");
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
                }
            }
        });
        accountVM.getCurrentAccount().observe(this, new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                if(account != null){
                    acc = account;
                    reference.addListenerForSingleValueEvent(valueEventListener);
                    System.out.println("Account value :"+acc);
                }
            }
        });

        accountVM.getElectricityPrice().observe(this, new Observer<Electricity>() {
            @Override
            public void onChanged(Electricity electricity) {
                if(electricity != null){
                    price = electricity;
                    electricityPriceDisplay.setText("Price mW/h :" + String.valueOf(price.getValue()));
                }
            }
        });
    }

    public void seeCalendar(View view) {
        startActivity(new Intent(this, MainCalendarActivity.class));
    }
}
