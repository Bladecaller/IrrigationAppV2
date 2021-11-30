package com.example.sep4_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main123Activity extends AppCompatActivity {
    public TextView usernameDisplay;
    public EditText plantInfo, yardsInfo, waterPerYardsInfo, plantDateInfo, harvestInfo;
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
        yardsInfo = findViewById(R.id.yardsText);
        waterPerYardsInfo = findViewById(R.id.waterPerYardsText);
        plantDateInfo = findViewById(R.id.dateeText);
        harvestInfo = findViewById(R.id.harvestText);
        addDataBtn = findViewById(R.id.addDataButton);

        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        username = prefs.getString("username", "UNKNOWN");

        Bundle extras = getIntent().getExtras();

        if (extras.containsKey("username")) {
            String username = extras.getString("username");

            usernameDisplay.setText(username);

            addDataBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     rootNode = FirebaseDatabase.getInstance("https://bprcalendarinfo-default-rtdb.europe-west1.firebasedatabase.app/");
                     reference = rootNode.getReference("users");

                     String plantName = plantInfo.getText().toString();
                     String harvest = harvestInfo.getText().toString();
                     String yards = yardsInfo.getText().toString();
                     String waterPerYards = waterPerYardsInfo.getText().toString();
                     String startingDate = plantDateInfo.getText().toString();


                     FirebaseHelperClass helperClass = new FirebaseHelperClass(username,plantName,yards,waterPerYards,startingDate,harvest);

                     reference.push().setValue(helperClass);
                }
            });

        }
    }
}