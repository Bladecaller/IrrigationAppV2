package com.example.sep4_android;

import static com.example.sep4_android.CalendarUtils.daysInWeekArray;
import static com.example.sep4_android.CalendarUtils.monthYearFromDate;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.sep4_android.ViewHolders.ViewHolderPlants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import firebase_sql_helper_classes.Plant;
import fragments.HomeFragment;
import fragments.RandomFragment;
import fragments.ReportFragment;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
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

    private TextView monthYearText,currentUser, currentLocation;
    private ImageView precipitationImage;
    private Activity activity;
    private RecyclerView calendarRecyclerView,firebaseRecyclerView;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference,referenceUsers;
    private LocalDate dateToCompare;
    private ArrayList<Plant> plantsInCurrentDay;
    private Button addBtn;
    private Toolbar toolbar;
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
    private Button graphButton;
    private double waterUse=0;

    private final int ID_HOME = 1;
    private final int ID_GRAPH = 2;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        humidityDisplay = findViewById(R.id.humidityDisplay);
        precipitationDisplay = findViewById(R.id.precipitationDisplay);
        temperatureDisplay = findViewById(R.id.temperatureDisplay);
        graphButton = findViewById(R.id.settings_home);
        currentUser = findViewById(R.id.userDisplay);
        currentLocation = findViewById(R.id.locationDisplay);
        electricityPriceDisplay = findViewById(R.id.electricityPrice);
        dailyWaterUsage = findViewById(R.id.dailyWaterUsageText);
        precipitationImage = findViewById(R.id.precipitationImage);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        humidityViewModel = new ViewModelProvider(this).get(HumidityViewModel.class);
        temperatureViewModel = new ViewModelProvider(this).get(TemperatureViewModel.class);
        precipitationViewModel = new ViewModelProvider(this).get(PrecipitationViewModel.class);
        accountVM = new ViewModelProvider(this).get(AccountRepoViewModel.class);
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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("irrigation","irrigation", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.icon_home_black));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_show_chart_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.temperature_v1));


        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch(model.getId()){
                    case 1:
                        replace(new HomeFragment());
                        break;

                    case 2:
                        replace(new ReportFragment());
                        break;

                    case 3:
                        replace(new RandomFragment());
                        break;
                }
                return null;
            }
        });

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
                currentUser.setText(acc.getUsername());
                currentLocation.setText(location);
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

        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(v.getContext(),GraphActivity.class);
                Bundle args = new Bundle();
                args.putSerializable("List",plantsInCurrentDay);
                intent.putExtra("BUNDLE",args);
                startActivity(intent);
            }
        });

        humidityViewModel.getHumidity(location).observe(this, new Observer<List<Humidity>>() {
            @Override
            public void onChanged(List<Humidity> humidities) {
                if(!humidities.isEmpty()){
                    humidityDisplay.setText(String.valueOf(humidities.get(humidities.size()-1).getValue()) + " %");
                }else {
                    humidityDisplay.setText("Empty, calling for data");
                }
            }
        });
        temperatureViewModel.getTemperature(location).observe(this, new Observer<List<Temperature>>() {
            @Override
            public void onChanged(List<Temperature> temperatures) {
                if(!temperatures.isEmpty()){
                    temperatureDisplay.setText(String.valueOf(temperatures.get(temperatures.size()-1).getValue()) + " C");

                }else {
                    temperatureDisplay.setText("Empty, calling for data");
                }
            }
        });

        precipitationViewModel.getPrecipitation(location).observe(this, new Observer<List<Precipitation>>() {
            @Override
            public void onChanged(List<Precipitation> precipitations) {
                if(!precipitations.isEmpty()){
                    precipitationDisplay.setText(String.valueOf(precipitations.get(precipitations.size()-1).getValue()));
                    switchImage(String.valueOf(precipitations.get(precipitations.size()-1).getValue()));
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
                    electricityPriceDisplay.setText(String.valueOf(price.getValue())+"\nDKK per MWh");
                }
            }
        });
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                DateFormat formatter = new SimpleDateFormat("HH:mm");
                if(!plantsInCurrentDay.isEmpty()){
                    for (Plant plant:plantsInCurrentDay
                         ) {
                        System.out.println(LocalTime.now());
                        System.out.println(LocalTime.parse(plant.getTime()));
                        if(LocalTime.now().isAfter(LocalTime.parse(plant.getTime())) && !plant.isWatered()){
                            NotificationCompat.Builder builder = new NotificationCompat.Builder(WeeklyCalendarActivity.this,"irrigation");
                            builder.setContentTitle("Irrigation notification");
                            builder.setSmallIcon(R.drawable.humidity_icon);
                            builder.setContentText("It is time to water "+ plant.getPlantName());

                            NotificationManagerCompat manager = NotificationManagerCompat.from(WeeklyCalendarActivity.this);
                            manager.notify(1,builder.build());
                            System.out.println("NOTIFICATION"+ LocalDateTime.now());
                        }
                    }
                }
            }
        }, 0, 10000);//put here time 1000 milliseconds=1 second
        }

    private void replace(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
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
                                &&LocalDate.parse(model.getHarvestDate()).isAfter(dateToCompare)
                                &&isItWateringDay(LocalDate.parse(model.getStartDate()),dateToCompare,model.getWateringFrequency())) {
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
                                    }
                                }
                                dailyWaterUsage.setText(String.valueOf(waterUse)+" L");
                            }

                        }else{
                            holder.destroy();
                        }

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!holder.clicked){
                                    for (Plant plant :plantsInCurrentDay
                                         ) {
                                        if(plant==model){
                                            plant.setWatered(true);
                                        }
                                    }
                                    holder.itemView.setBackgroundColor(0xFF00FF00);
                                    holder.clicked = true;
                                }
                            }
                        });
                        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                Intent intent = new Intent();
                                intent.setClass(getApplicationContext(),RemoveActivity.class);
                                intent.putExtra("username", acc.getUsername());
                                intent.putExtra("plantName", model.getPlantName());
                                startActivity(intent);
                                return false;
                            }
                        });
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
    public void switchImage(String precipitation) {
        switch (precipitation) {
            case "Dissolving Clouds":
                precipitationImage.setImageResource(R.drawable.dissolving_clouds_v1);
                break;
            case "Mist":
                precipitationImage.setImageResource(R.drawable.mist_v1);
                break;
            case "Small Precipitation":
                precipitationImage.setImageResource(R.drawable.small_precipitation);
                break;
            case "Snow":
            case "Drifting Snow":
                precipitationImage.setImageResource(R.drawable.snow_v2);
                break;
            case "Duststorm/Sandstorm":
                precipitationImage.setImageResource(R.drawable.sandstorm_v1);
                break;
            case "Fog":
                precipitationImage.setImageResource(R.drawable.fog_v1);
                break;
            case "Drizzle":
                precipitationImage.setImageResource(R.drawable.drizzle_v2);
                break;
            case "Rain":
            case "Solid Precipitation":
            case "Rain Shower":
                precipitationImage.setImageResource(R.drawable.rain_v1);
                break;
            case "Thunderstorm":
                precipitationImage.setImageResource(R.drawable.thunderstorm_v2);
                break;
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_button, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(),SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public boolean isItWateringDay(LocalDate start, LocalDate current, int frequency){
    boolean result;
        Instant startIn = start.atStartOfDay(ZoneId.systemDefault()).toInstant();
        long startMilli = startIn.toEpochMilli();
        Instant currentIn = current.atStartOfDay(ZoneId.systemDefault()).toInstant();
        long currentMilli = currentIn.toEpochMilli();
        long periodToCheck = currentMilli - startMilli;
        long amountOfDays = frequency * 86400000;

        if(periodToCheck % amountOfDays == 0 || currentMilli == startMilli){
            result = true;
        }else{
            result= false;
        }

    return result;
    }


}