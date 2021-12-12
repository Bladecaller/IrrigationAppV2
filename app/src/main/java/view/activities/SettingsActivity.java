package view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.SEP7_IrrigationApp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import model.room.entity.Account;
import viewmodel.AccountRepoViewModel;
import viewmodel.HumidityViewModel;
import viewmodel.PrecipitationViewModel;
import viewmodel.TemperatureViewModel;

public class SettingsActivity extends AppCompatActivity {
    EditText location;
    Spinner spinner,spinnerPrice,spinnerLum;
    Button buttonSet,buttonSetPrice,buttonSetLuminosity;
    Button buttonBack;
    Toolbar toolbar;
    private HumidityViewModel humidityViewModel;
    private TemperatureViewModel temperatureViewModel;
    private PrecipitationViewModel precipitationViewModel;
    private AccountRepoViewModel accountVM;
    private Account acc;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        buttonSet = findViewById(R.id.location_button);
        buttonBack = findViewById(R.id.backSettings);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rootNode = FirebaseDatabase.getInstance("https://bprcalendarinfo-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = rootNode.getReference("users");
        humidityViewModel = new ViewModelProvider(this).get(HumidityViewModel.class);
        temperatureViewModel = new ViewModelProvider(this).get(TemperatureViewModel.class);
        precipitationViewModel = new ViewModelProvider(this).get(PrecipitationViewModel.class);
        accountVM = new ViewModelProvider(this).get(AccountRepoViewModel.class);
        buttonSetPrice = findViewById(R.id.price_button);

        spinner = findViewById(R.id.spinnerLocation);
        ArrayList<String> arrayListLocation = new ArrayList<>();
        arrayListLocation.add("Horsens");
        arrayListLocation.add("Aarhus");
        arrayListLocation.add("Attu");
        arrayListLocation.add("Nuuk");
        ArrayAdapter<String> arrayAdapterLocation = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListLocation);
        arrayAdapterLocation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapterLocation);

        spinnerPrice = findViewById(R.id.spinnerPrice);
        ArrayList<String> arrayListPrice = new ArrayList<>();
        arrayListPrice.add("West");
        arrayListPrice.add("East");
        ArrayAdapter<String> arrayAdapterPrice = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayListPrice);
        arrayAdapterPrice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPrice.setAdapter(arrayAdapterPrice);

        accountVM.getCurrentAccount();

        accountVM.getCurrentAccount().observe(this, new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                if(account != null){
                    acc = account;
                    System.out.println("Account value observe:"+acc);
                }
            }
        });

        buttonSet.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(!acc.getUsername().equals("Default")){
                    reference.child(acc.getUsername()).child("userInfo").child("location").setValue(spinner.getSelectedItem().toString());
                    System.out.println("Location set");
                    displayToast("Location is set");
                }
            }
        });

        buttonSetPrice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                if(!acc.getUsername().equals("Default")){
                    reference.child(acc.getUsername()).child("userInfo").child("electricityLocation").setValue(spinnerPrice.getSelectedItem().toString());
                    System.out.println("Price set");
                    displayToast("Price is set");
                }
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void displayToast(String texts){
        Toast.makeText(this.getApplicationContext(),texts,Toast.LENGTH_SHORT).show();
    }
}
