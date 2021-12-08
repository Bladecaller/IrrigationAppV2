package com.example.sep4_android;

import static com.example.sep4_android.CalendarUtils.daysInWeekArray;
import static com.example.sep4_android.CalendarUtils.monthYearFromDate;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sep4_android.ViewHolders.ViewHolderPlants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import firebase_sql_helper_classes.Plant;
import model.room.entity.Account;
import model.room.entity.Electricity;
import model.room.entity.Humidity;
import model.room.entity.Precipitation;
import model.room.entity.Temperature;
import viewmodel.AccountRepoViewModel;
import viewmodel.HumidityViewModel;
import viewmodel.PrecipitationViewModel;
import viewmodel.TemperatureViewModel;

public class WeeklyCalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private TextView monthYearText,currentUser;
    private Activity activity;
    private RecyclerView calendarRecyclerView,firebaseRecyclerView;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference,referenceUsers;
    private LocalDate dateToCompare;
    private ArrayList<Plant> plantsInCurrentDay;
    private Button addBtn;
    private AccountRepoViewModel accountVM;
    private Account acc;
    private HumidityViewModel humidityViewModel;
    private TemperatureViewModel temperatureViewModel;
    private PrecipitationViewModel precipitationViewModel;
    private Electricity price;
    private TextView humidityDisplay, dailyWaterUsage;
    private TextView temperatureDisplay;
    private TextView precipitationDisplay,electricityPriceDisplay;
    private String username, location, accPrice;
    private Button settingsButton;
    private double waterUse=0;




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        humidityDisplay = findViewById(R.id.humidityDisplay);
        precipitationDisplay = findViewById(R.id.precipitationDisplay);
        temperatureDisplay = findViewById(R.id.temperatureDisplay);
        settingsButton = findViewById(R.id.settings_home);
        currentUser = findViewById(R.id.userDisplay);
        electricityPriceDisplay = findViewById(R.id.electricityPrice);
        dailyWaterUsage = findViewById(R.id.dailyWaterUsageText);
        humidityViewModel = new ViewModelProvider(this).get(HumidityViewModel.class);
        temperatureViewModel = new ViewModelProvider(this).get(TemperatureViewModel.class);
        precipitationViewModel = new ViewModelProvider(this).get(PrecipitationViewModel.class);
        accountVM = new ViewModelProvider(this).get(AccountRepoViewModel.class);
        //Bundle extras = getIntent().getExtras();
        CalendarUtils.selectedDate = LocalDate.now();
        activity = this;
        accountVM = new ViewModelProvider(this).get(AccountRepoViewModel.class);
        dateToCompare = LocalDate.now();
        firebaseRecyclerView = findViewById(R.id.plantRecycleListView);
        plantsInCurrentDay = new ArrayList<>();
        addBtn = findViewById(R.id.addPlantButton);
        firebaseRecyclerView.setHasFixedSize(true);
        firebaseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        accountVM.getCurrentAccount();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(),AddPlantActivity.class);
                startActivity(intent);
            }
        });

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                System.out.println("Username is : "+ acc.getUsername());
                Object locationObj = dataSnapshot.child(acc.getUsername()).child("userInfo").child("location").getValue();
                Object priceObj = dataSnapshot.child(acc.getUsername()).child("userInfo").child("electricityLocation").getValue();
                location = String.valueOf(locationObj);
                accPrice = String.valueOf(priceObj);
                currentUser.setText("Current user: "+acc.getUsername()+" with location: "+location);
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


        accountVM.getCurrentAccount().observe(this, new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                if(account!=null){
                    acc = account;
                    rootNode = FirebaseDatabase.getInstance("https://bprcalendarinfo-default-rtdb.europe-west1.firebasedatabase.app/");
                    reference = rootNode.getReference().child("users").child(acc.getUsername()).child("plantsInfo");
                    referenceUsers = rootNode.getReference().child("users");
                    referenceUsers.addListenerForSingleValueEvent(valueEventListener);
                    initWidgets();
                    setWeeklyView();
                }
            }
        });

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

        accountVM.getElectricityPrice().observe(this, new Observer<Electricity>() {
            @Override
            public void onChanged(Electricity electricity) {
                if(electricity != null){
                    price = electricity;
                    electricityPriceDisplay.setText("Price mW/h for "+accPrice +" Denmark: " + String.valueOf(price.getValue()));
                }
            }
        });
        }


    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }


    private void setWeeklyView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

        FirebaseRecyclerOptions<Plant> options = new FirebaseRecyclerOptions.Builder<Plant>()
                .setQuery(reference,Plant.class)
                .build();
        FirebaseRecyclerAdapter<Plant, ViewHolderPlants> adapter =
                new FirebaseRecyclerAdapter<Plant, ViewHolderPlants>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull @NotNull ViewHolderPlants holder, int position, @NonNull @NotNull Plant model) {
                        if((LocalDate.parse(model.getStartDate()).isBefore(dateToCompare)
                        || LocalDate.parse(model.getStartDate()).isEqual(dateToCompare))
                                &&LocalDate.parse(model.getHarvestDate()).isAfter(dateToCompare)) {

                            holder.setItem(activity, model.getStartDate(), model.getWateringFrequency(),
                                    model.getTime(), model.getWaterPerYards(), model.getAmountOfLand(),
                                    model.getHarvestDate(), model.getPlantName());

                            if(!plantsInCurrentDay.contains(model)){
                                plantsInCurrentDay.add(model);
                                for (Plant plant : plantsInCurrentDay
                                ) {
                                    if(model.getPlantName()==plant.getPlantName()){
                                        double singlePlantuse = plant.getAmountOfLand()*plant.getWaterPerYards();
                                        waterUse +=singlePlantuse;
                                        System.out.println("Adding watter :"+singlePlantuse);
                                        System.out.println("Adding watter :"+model.getPlantName());
                                    }
                                }
                                dailyWaterUsage.setText("Daily water usage: "+String.valueOf(waterUse)+" litres");
                            }

                        }else{
                            holder.destroy();
                        }
                    }
                    @NonNull
                    @NotNull
                    @Override
                    public ViewHolderPlants onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item,parent,false);
                        return  new ViewHolderPlants(view);
                    }

                };

        adapter.startListening();
        firebaseRecyclerView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        dateToCompare = CalendarUtils.selectedDate;
        waterUse=0;
        plantsInCurrentDay.clear();
        setWeeklyView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        dateToCompare = CalendarUtils.selectedDate;
        waterUse=0;
        plantsInCurrentDay.clear();
        setWeeklyView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            dateToCompare = date;
            waterUse=0;
            plantsInCurrentDay.clear();
            setWeeklyView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}