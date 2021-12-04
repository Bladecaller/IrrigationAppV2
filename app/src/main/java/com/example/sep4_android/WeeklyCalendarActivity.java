package com.example.sep4_android;

import static com.example.sep4_android.CalendarUtils.daysInMonthArray;
import static com.example.sep4_android.CalendarUtils.daysInWeekArray;
import static com.example.sep4_android.CalendarUtils.monthYearFromDate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import firebase_sql_helper_classes.Plant;

public class WeeklyCalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private ListView eventListView;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private LocalDate dateToCompare;
    private long longDate;
    private String stringDate;
    private ArrayList<Plant> listOfPlants;
    private LocalDate dateHarvest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_calendar);
        initWidgets();
        setWeeklyView();
        rootNode = FirebaseDatabase.getInstance("https://bprcalendarinfo-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference convertedDate = rootNode.getReference().child("users").child("zoro").child("plantsInfo").child("TESTDATE").child("time");
        reference = rootNode.getReference("users");
        listOfPlants = new ArrayList<Plant>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object milisecondsDate = dataSnapshot.child("users").child("zoro").child("plantsInfo").child("TESTDATE").child("convertedDate").getValue();
                System.out.println("MILISECONDS ARE HERE " + milisecondsDate);
                System.out.println("MILISECONDS ARE HERE " + milisecondsDate);
                System.out.println("MILISECONDS ARE HERE " + milisecondsDate);
                Object plants = dataSnapshot.child("users").child("zoro").child("plantsInfo").getValue();
                GenericTypeIndicator<ArrayList<Plant>> t = new GenericTypeIndicator<ArrayList<Plant>>() {};

                listOfPlants = dataSnapshot.child("users").child("zoro").child("plantsInfo").getValue(t);
                System.out.println(listOfPlants);
                System.out.println(listOfPlants);
                System.out.println(listOfPlants);
                System.out.println(listOfPlants);
                //stringDate = String.valueOf(milisecondsDate);

            }
                @Override
                public void onCancelled (@NonNull DatabaseError databaseError){
                    Log.d("ERROR IS HERE", databaseError.getMessage());

            }
        };
        reference.addListenerForSingleValueEvent(valueEventListener);
        stringDate = "1638568800000";
        longDate = Long.parseLong(stringDate);
        dateToCompare = Instant.ofEpochMilli(longDate).atZone(ZoneId.systemDefault()).toLocalDate();
        dateHarvest = LocalDate.parse("2021-12-05");
        System.out.println("HERE IS THE VALUE "+stringDate );
        System.out.println("HERE IS THE VALUE "+stringDate );
        System.out.println("HERE IS THE VALUE "+stringDate );



        System.out.println("DATE TO COMPARE IS "+dateToCompare);
        }
    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        eventListView = findViewById(R.id.eventListView);
    }

    private void setWeeklyView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
        setEventAdapter();
    }



    public void previousWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusWeeks(1);
        setWeeklyView();
    }

    public void nextWeekAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusWeeks(1);
        setWeeklyView();
    }



    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setWeeklyView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
    }


    private void setEventAdapter() {
        ArrayList<Plant> dailyEvents = Plant.plantsForDate(dateToCompare,dateHarvest,listOfPlants);
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);
        eventListView.setAdapter(eventAdapter);
    }

    public void newEventAction(View view) {
        startActivity(new Intent(this, AddPlantActivity.class));
    }
}