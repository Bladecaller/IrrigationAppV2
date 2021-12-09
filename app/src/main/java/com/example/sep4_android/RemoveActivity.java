package com.example.sep4_android;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RemoveActivity extends AppCompatActivity {
    Button btnRemove,btnBack;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remove_layout);
        btnBack = findViewById(R.id.buttonBack);
        btnRemove = findViewById(R.id.buttonRemove);
        Bundle extras = getIntent().getExtras();
        System.out.println("EXTRA VALUE "+extras.get("username").toString() + extras.get("plantName").toString());
        rootNode = FirebaseDatabase.getInstance("https://bprcalendarinfo-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = rootNode.getReference().child("users").child(extras.get("username").toString()).child("plantsInfo");

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(extras.get("plantName").toString()).removeValue();
            }
        });
    }
}
