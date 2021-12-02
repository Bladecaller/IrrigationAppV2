package com.example.sep4_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fragments.HomeFragment;
import fragments.RandomFragment;
import fragments.ReportFragment;

public class Main123Activity extends AppCompatActivity {
    public TextView usernameDisplay;
    public EditText plantInfo,wateringFrequencyInfo, timeInfo, yardsInfo, waterPerYardsInfo, amountOfLandInfo, harvestAfterMonthsInfo ;
    public Button addDataBtn;
    private String username = "";
    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main123);


        usernameDisplay = findViewById(R.id.username2);
        plantInfo = findViewById(R.id.plantText);
        wateringFrequencyInfo = findViewById(R.id.wateringFrequencyText);
        timeInfo = findViewById(R.id.timeText);
        waterPerYardsInfo = findViewById(R.id.waterPerYardsText);
        amountOfLandInfo = findViewById(R.id.amountOfLandText);
        harvestAfterMonthsInfo = findViewById(R.id.harvestAfterMonthsText);
        addDataBtn = findViewById(R.id.addDataButton);


        Bundle extras = getIntent().getExtras();

        if (extras.containsKey("username")) {
                 username = extras.getString("username");

            usernameDisplay.setText(username);
        }
        addDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance("https://bprcalendarinfo-default-rtdb.europe-west1.firebasedatabase.app/");
                reference = rootNode.getReference("users");

                //All the values that needs to be pushed on FIREBASE
                String plantName = plantInfo.getText().toString();
                int wateringFrequency = Integer.parseInt(wateringFrequencyInfo.getText().toString());
                String time = timeInfo.getText().toString();
                double waterPerYards =Double.parseDouble(waterPerYardsInfo.getText().toString());
                double amountOfLand = Double.parseDouble(amountOfLandInfo.getText().toString());
                double harvestAfterMonths = Double.parseDouble(harvestAfterMonthsInfo.getText().toString());
                String locationTest = "Aarhus";
                String electricityLocationTest = "West";
                String luminosityLocationTest = "ZONE 3";

                //CODE THAT READS FROM FIREBASE
                //GOING THROUGH EACH NODE TO GET THE VALUE IN LINE 87
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            Object plant = dataSnapshot.child(username).child("plantsInfo").child(plantName).getValue();

                        //Do what you need to do with your list
                        Log.d("VALUE IS HERE", plantName + " "+String.valueOf(plant));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("ERROR IS HERE", databaseError.getMessage());
                    }
                };
                //REFERENCE TO FIREBASE MAIN NODE
                reference.addListenerForSingleValueEvent(valueEventListener);
                //FireBaseHelperClass helps with setting the values for the calendar
                //UserInfo helps with setting the values for the user
                //Both of them need to go through some nodes before creating the set
                FirebaseHelperClass plantsInfo = new FirebaseHelperClass(wateringFrequency, time, waterPerYards, amountOfLand, harvestAfterMonths);
                 UserInfoHelperClass userInfo = new UserInfoHelperClass(locationTest, luminosityLocationTest, electricityLocationTest);
                reference.child(username).child("userInfo").setValue(userInfo);
                reference.child(username).child("plantsInfo").child(plantName).setValue(plantsInfo);

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
