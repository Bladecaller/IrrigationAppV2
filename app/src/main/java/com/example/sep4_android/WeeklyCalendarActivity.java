package com.example.sep4_android;

import static com.example.sep4_android.CalendarUtils.daysInMonthArray;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sep4_android.RecyclerView.ViewHolderPlants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import firebase_sql_helper_classes.Plant;
import model.room.entity.Account;
import viewmodel.AccountRepoViewModel;

public class WeeklyCalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private TextView monthYearText;
    private Activity activity;
    private RecyclerView calendarRecyclerView,firebaseRecyclerView;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private LocalDate dateToCompare;
    private ArrayList<Plant> plantsInCurrentDay;
    Button addBtn;
    private AccountRepoViewModel accountVM;
    Account acc;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalendarUtils.selectedDate = LocalDate.now();
        activity = this;
        accountVM = new ViewModelProvider(this).get(AccountRepoViewModel.class);
        accountVM.getCurrentAccount();
        dateToCompare = LocalDate.now();
        setContentView(R.layout.activity_weekly_calendar);
        firebaseRecyclerView = findViewById(R.id.plantRecycleListView);
        plantsInCurrentDay = new ArrayList<>();
        addBtn = findViewById(R.id.addPlantButton);
        firebaseRecyclerView.setHasFixedSize(true);
        firebaseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(),AddPlantActivity.class);
                startActivity(intent);
            }
        });

        accountVM.getCurrentAccount().observe(this, new Observer<Account>() {
            @Override
            public void onChanged(Account account) {
                if(account!=null){
                    acc = account;
                    rootNode = FirebaseDatabase.getInstance("https://bprcalendarinfo-default-rtdb.europe-west1.firebasedatabase.app/");
                    reference = rootNode.getReference().child("users").child(acc.getUsername()).child("plantsInfo");
                    reference.orderByChild("amountOfLand");
                    initWidgets();
                    setWeeklyView();
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
                        if(LocalDate.parse(model.getStartDate()).isBefore(dateToCompare)
                        || LocalDate.parse(model.getStartDate()).isEqual(dateToCompare)) {

                            holder.setItem(activity, model.getStartDate(), model.getWateringFrequency(),
                                    model.getTime(), model.getWaterPerYards(), model.getAmountOfLand(),
                                    model.getHarvestAfterMonths(), model.getPlantName());
                            plantsInCurrentDay.add(model);

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
        setWeeklyView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        dateToCompare = CalendarUtils.selectedDate;
        setWeeklyView();
    }

    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            dateToCompare = date;
            setWeeklyView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}