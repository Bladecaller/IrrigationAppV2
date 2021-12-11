package com.example.sep4_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Collections;

import firebase_sql_helper_classes.Plant;

public class GraphActivity extends AppCompatActivity {

    private static final String Tag = "GraphActivity";

    private LineChart mChart;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<Plant> list = (ArrayList<Plant>) args.getSerializable("List");

    mChart = findViewById(R.id.graphData);


    //mChart.setOnChartGestureListener(GraphActivity.this);
    //mChart.setOnChartValueSelectedListener(GraphActivity.this);

    mChart.setDragEnabled(true);
    mChart.setScaleEnabled(false);

        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(0, 0));
        Collections.sort(list);
        for (Plant plant: list
        ) {
            float timeFloat = Float.parseFloat(plant.getTime().replace(":","."));
            float waterFloat = (float)(plant.getWaterPerYards()*plant.getAmountOfLand());
            yValues.add(new Entry(timeFloat,waterFloat));
            System.out.println("TIME : " + timeFloat);
            System.out.println("WATER : " + (float)(plant.getWaterPerYards()*plant.getAmountOfLand()));
        }


        yValues.add(new Entry(24, 0));
        LineDataSet test1 = new LineDataSet(yValues, "Daily water usage");

        test1.setFillAlpha(110);
        test1.setColor(Color.BLUE);
        test1.setLineWidth(4f);
        test1.setValueTextSize(10f);
        test1.setValueTextColor(Color.WHITE);
        test1.setDrawFilled(true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(test1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);
    }
}