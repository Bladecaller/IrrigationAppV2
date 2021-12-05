package com.example.sep4_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import firebase_sql_helper_classes.Plant;
import model.room.entity.Account;
import viewmodel.AccountRepoViewModel;

public class AddPlantActivity extends AppCompatActivity {
    public EditText plantName,amountOfLand,waterPerYard,
            startDate,harvestAfterMonths,time,wateringFrequency;
    public Button addDataBtn;
    Activity activity;
    AccountRepoViewModel accountVM;
    Account acc;
    FirebaseDatabase rootNode;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
        accountVM = new ViewModelProvider(this).get(AccountRepoViewModel.class);
        accountVM.getCurrentAccount();
        addDataBtn = findViewById(R.id.addDataButton);
        plantName = findViewById(R.id.plantNameText);
        amountOfLand = findViewById(R.id.amountOfLandText);
        waterPerYard = findViewById(R.id.waterPerYardsText);
        startDate = findViewById(R.id.startDateText);
        harvestAfterMonths = findViewById(R.id.harvestDate);
        time = findViewById(R.id.timeText);
        wateringFrequency = findViewById(R.id.wateringFrequencyText);
        activity = this;

        addDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(startDate.getText().toString().matches("")||
                wateringFrequency.getText().toString().matches("")||
                        time.getText().toString().matches("")||
                        waterPerYard.getText().toString().matches("")||
                        amountOfLand.getText().toString().matches("")||
                        harvestAfterMonths.getText().toString().matches("")||
                        plantName.getText().toString().matches("")){
                    Toast.makeText(activity, "You have empty fields",
                            Toast.LENGTH_LONG).show();
                }else{
                    Plant plant = new Plant(
                            startDate.getText().toString(),
                            Integer.parseInt(wateringFrequency.getText().toString()),
                            time.getText().toString(),
                            Integer.parseInt(waterPerYard.getText().toString()),
                            Integer.parseInt(amountOfLand.getText().toString()),
                            harvestAfterMonths.getText().toString(),
                            plantName.getText().toString());

                    reference.push().setValue(plant);
                    Toast.makeText(activity, plantName.getText().toString()+" was added!",
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent();
                    intent.setClass(view.getContext(),WeeklyCalendarActivity.class);
                    startActivity(intent);
                }
            }
        });
        accountVM.getCurrentAccount().observe(this, new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                if(account!= null){
                    acc = account;
                    rootNode = FirebaseDatabase.getInstance("https://bprcalendarinfo-default-rtdb.europe-west1.firebasedatabase.app/");
                    reference = rootNode.getReference().child("users").child(acc.getUsername()).child("plantsInfo");
                    reference.orderByChild("time");
                }
            }
        });



        ///////////////////////////////////////////////////////////////
        //This is working but it is annoying so i disabled it for now

        /*
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_new);
        bottomNav.setOnItemSelectedListener(navListener);
        }

        private NavigationBarView.OnItemSelectedListener navListener =
                new BottomNavigationView.OnItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;

                        switch (item.getItemId())
                        {
                            case R.id.bottom_nav_home:
                                selectedFragment = new HomeFragment();
                                break;
                            case R.id.bottom_nav_report:
                                selectedFragment = new ReportFragment();
                                break;
                            case R.id.bottom_nav_random:
                                selectedFragment = new RandomFragment();
                                break;
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                        return true;
                    }
                };

         */
    }
}
