package com.example.sep4_android;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import firebase_sql_helper_classes.Plant;

public class EventAdapter extends ArrayAdapter<Plant>
{
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private String plantEventName ;
    private String plantRefKey;

    public EventAdapter(@NonNull Context context, List<Plant> plants)
    {
        super(context, 0, plants);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        rootNode = FirebaseDatabase.getInstance("https://bprcalendarinfo-default-rtdb.europe-west1.firebasedatabase.app/");
        Plant plant = getItem(position);
       reference = rootNode.getReference("users").child("zoro").child("plantsInfo").child(plant.getPlantName());
       plantRefKey = reference.getKey();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object plantObj = dataSnapshot.child("zoro").child("plantsInfo").child(plant.getPlantName()).getValue();
                Object timeObj = dataSnapshot.child("zoro").child("plantsInfo").child(plant.getPlantName()).getValue();
                plantEventName = String.valueOf(plantObj);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ERROR IS HERE", databaseError.getMessage());
            }
        };

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);

        TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);

        String eventTitle = plantRefKey ;
        eventCellTV.setText(eventTitle);
        return convertView;
    }
}