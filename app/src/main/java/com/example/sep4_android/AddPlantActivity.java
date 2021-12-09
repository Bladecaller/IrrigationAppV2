package com.example.sep4_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

import firebase_sql_helper_classes.Plant;
import fragments.HomeFragment;
import fragments.RandomFragment;
import fragments.ReportFragment;
import model.room.entity.Account;
import viewmodel.AccountRepoViewModel;

public class AddPlantActivity extends AppCompatActivity {
    public EditText plantName, amountOfLand, waterPerYard,
            startDate, harvestAfterMonths, time, wateringFrequency;
    public Button addDataBtn, datePickerButton, timePickerButton;
    Activity activity;
    AccountRepoViewModel accountVM;
    Account acc;
    Toolbar toolbar;
    private DatePickerDialog datePickerDialog;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private int hour, minute;
    String formattedTime = "";


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
        //startDate = findViewById(R.id.startDateText);
        harvestAfterMonths = findViewById(R.id.harvestDate);
        datePickerButton = findViewById(R.id.datePickerButton);
        timePickerButton = findViewById(R.id.timePickerButton);
        datePickerButton.setText(getTodaysDate());
        //time = findViewById(R.id.timeText);
        toolbar = findViewById(R.id.my_toolbar);

        //   BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_new);
        //    bottomNav.setOnItemSelectedListener(navListener);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initDatePicker();
        wateringFrequency = findViewById(R.id.wateringFrequencyText);
        activity = this;

        addDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datePickerButton.getText().toString().matches("") ||
                        wateringFrequency.getText().toString().matches("") ||
                        timePickerButton.getText().toString().matches("") ||
                        waterPerYard.getText().toString().matches("") ||
                        amountOfLand.getText().toString().matches("") ||
                        harvestAfterMonths.getText().toString().matches("") ||
                        plantName.getText().toString().matches("")) {
                    Toast.makeText(activity, "You have empty fields",
                            Toast.LENGTH_LONG).show();
                } else {
                    Plant plant = new Plant(
                            datePickerButton.getText().toString(),
                            Integer.parseInt(wateringFrequency.getText().toString()),
                            timePickerButton.getText().toString(),
                            Integer.parseInt(waterPerYard.getText().toString()),
                            Integer.parseInt(amountOfLand.getText().toString()),
                            harvestAfterMonths.getText().toString(),
                            plantName.getText().toString());

                    reference.push().setValue(plant);
                    Toast.makeText(activity, plantName.getText().toString() + " was added!",
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent();
                    intent.setClass(view.getContext(), WeeklyCalendarActivity.class);
                    startActivity(intent);
                }
            }
        });
        accountVM.getCurrentAccount().observe(this, new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                if (account != null) {
                    acc = account;
                    rootNode = FirebaseDatabase.getInstance("https://bprcalendarinfo-default-rtdb.europe-west1.firebasedatabase.app/");
                    reference = rootNode.getReference().child("users").child(acc.getUsername()).child("plantsInfo");
                    reference.orderByChild("time");
                }
            }
        });



/*
        }

        private NavigationBarView.OnItemSelectedListener navListener =
                item -> {
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
                };

 */
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(year, month, day);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(year, month, day);
                datePickerButton.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }
    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    private String makeDateString(int year, int month, int day) {
        if (day < 10)
            return year + "-" + month + "-0" + day;
        else
            return year + "-" + month + "-" + day;
    }

    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                timePickerButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_LIGHT;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Time of watering");
        timePickerDialog.show();
        String timeToShow = makeTimeString(hour,minute);
        timePickerButton.setText(timeToShow);
    }

    public String makeTimeString(int hour, int minute) {
        String sHour;
        if (hour < 10) {
            sHour = "0" + hour;
        } else {
            sHour = String.valueOf(hour);
        }
        String sMinute;
        if (minute < 10) {
            sMinute = "0" + minute;
        } else {
            sMinute = String.valueOf(minute);
        }
        System.out.println(formattedTime);
        System.out.println(formattedTime);
        System.out.println(formattedTime);
        return formattedTime = sHour + ":" + sMinute;

    }
}


