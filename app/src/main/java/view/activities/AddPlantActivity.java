package view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.SEP7_IrrigationApp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import api.MyRetrofitRecommendations;
import model.non_room_classes.Plant;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import model.room.entity.Account;
import model.non_room_classes.Recommendations;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import viewmodel.AccountRepoViewModel;

public class AddPlantActivity extends AppCompatActivity {
    public AutoCompleteTextView plantName;
    public EditText amountOfLand, waterPerYard,
            startDate, harvestAfterMonths, time, wateringFrequency;
    public Button addDataBtn, datePickerButton, datePickerButtonHarvest, timePickerButton, recommendationsButton;
    Activity activity;
    AccountRepoViewModel accountVM;
    Account acc;
    Toolbar toolbar;
    Dialog dialog;
    ArrayList<String> plantSuggestions;
    ArrayAdapter<String> adapterSuggestions;
    List<Recommendations> recommendationsList;
    private DatePickerDialog datePickerDialog;
    private DatePickerDialog datePickerDialogHarvest;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private int hour, minute;
    String formattedTime = "";
    private MyRetrofitRecommendations retrofit;

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
       // harvestAfterMonths = findViewById(R.id.harvestDate);
        datePickerButton = findViewById(R.id.datePickerButton);
        datePickerButtonHarvest = findViewById(R.id.datePickerButtonHarvest);
        timePickerButton = findViewById(R.id.timePickerButton);
        datePickerButton.setText(getTodaysDate());
        datePickerButtonHarvest.setText(getTomorrowsdate());
        recommendationsButton= findViewById(R.id.recommendationsButton);

        //time = findViewById(R.id.timeText);
        toolbar = findViewById(R.id.my_toolbar);
        //   BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_new);
        //    bottomNav.setOnItemSelectedListener(navListener);
        retrofit = new MyRetrofitRecommendations();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initDatePicker();
        initDatePickerHarvest();
        wateringFrequency = findViewById(R.id.wateringFrequencyText);
        activity = this;
        plantSuggestions = new ArrayList<>();
        adapterSuggestions = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, plantSuggestions);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<Plant> list = (ArrayList<Plant>) args.getSerializable("List");
        plantName.setAdapter(adapterSuggestions);

        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.icon_home_black));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_show_chart_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_local_florist_24));


        bottomNavigation.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                Intent intent = new Intent();
                Bundle args = new Bundle();
                switch(model.getId()){
                    case 1:
                        intent.setClass(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        break;

                    case 2:
                        intent.setClass(getApplicationContext(),GraphActivity.class);
                        args.putSerializable("List",list);
                        intent.putExtra("BUNDLE",args);
                        startActivity(intent);
                        break;

                    case 3:
                        intent.setClass(getApplicationContext(),AddPlantActivity.class);
                        args.putSerializable("List",list);
                        intent.putExtra("BUNDLE",args);
                        startActivity(intent);
                        break;
                }
                return null;
            }
        });

    

        addDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datePickerButton.getText().toString().matches("") ||
                        wateringFrequency.getText().toString().matches("") ||
                        timePickerButton.getText().toString().matches("") ||
                        waterPerYard.getText().toString().matches("") ||
                        amountOfLand.getText().toString().matches("") ||
                        datePickerButtonHarvest.getText().toString().matches("") ||
                        plantName.getText().toString().matches(""))
                {
                    Toast.makeText(activity, "You have empty fields",
                            Toast.LENGTH_LONG).show();
                } else {
                    Plant plant = new Plant(
                            datePickerButton.getText().toString(),
                            Integer.parseInt(wateringFrequency.getText().toString()),
                            timePickerButton.getText().toString(),
                            Double.parseDouble(waterPerYard.getText().toString()),
                            Double.parseDouble(amountOfLand.getText().toString()),
                            datePickerButtonHarvest.getText().toString(),
                            plantName.getText().toString());

                    reference.child(plant.getPlantName()).setValue(plant);
                    Toast.makeText(activity, plantName.getText().toString() + " was added!",
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent();
                    intent.setClass(view.getContext(), HomeActivity.class);
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

        Call<List<Recommendations>> call = retrofit.api.getRecommendations("plant");
        call.enqueue(new Callback<List<Recommendations>>() {
            @Override
            public void onResponse(Call<List<Recommendations>> call, Response<List<Recommendations>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("error: " + response.code());
                }
                        recommendationsList = response.body();
                if (recommendationsList != null) {
                    for (Recommendations recommendations : recommendationsList)
                    {
                        plantSuggestions.add(recommendations.getName());
                    }
                }
                System.out.println(plantSuggestions);
            }

            @Override
            public void onFailure(Call<List<Recommendations>> call, Throwable t) {
                System.out.println(t.getMessage() + "  ERROR HERE NO LIST");
            }
        });


        recommendationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int filteredFrequency=0;
                double filteredWaterAmount=0;
                double filteredYards=0;
                String found = "We don't have data on that plant yet";
                for (int i=0;i<recommendationsList.size();i++)
                {
                    if (plantName.getText().toString().equals(recommendationsList.get(i).getName()))
                    {
                        filteredFrequency = recommendationsList.get(i).getWaterFrequency();
                        filteredWaterAmount = recommendationsList.get(i).getWaterPerYard();
                        filteredYards = recommendationsList.get(i).getYards();
                        found = "You can change the values accordingly";
                        wateringFrequency.setText(String.valueOf(filteredFrequency));
                        amountOfLand.setText(String.valueOf(filteredYards));
                        waterPerYard.setText(String.valueOf(filteredWaterAmount));
                    }
                }
                    Toast.makeText(AddPlantActivity.this,found, Toast.LENGTH_SHORT).show();

            }
        });
    }



    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(year, month, day);
    }
    private String getTomorrowsdate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        day = day + 1;
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
        long currentTime = new Date().getTime();
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(currentTime);
    }
    private void initDatePickerHarvest() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(year, month, day);
                System.out.println(date);
                datePickerButtonHarvest.setText(date);
                String random = datePickerButtonHarvest.getText().toString();
                System.out.println(random);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        long currentTime = new Date().getTime();
        datePickerDialogHarvest = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialogHarvest.getDatePicker().setMinDate(currentTime);
    }
    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
    public void openDatePickerHarvest(View view) {datePickerDialogHarvest.show();
    }

    private String makeDateString(int year, int month, int day) {
        if (day < 10 && month < 10)
            return year + "-0" + month + "-0" + day;
        if (month < 10)
            return year + "-0" + month + "-" + day;
        if (day < 10 )
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


