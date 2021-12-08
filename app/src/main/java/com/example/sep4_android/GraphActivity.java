package com.example.sep4_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

    mChart = findViewById(R.id.graphData);


    //mChart.setOnChartGestureListener(GraphActivity.this);
    //mChart.setOnChartValueSelectedListener(GraphActivity.this);

    mChart.setDragEnabled(true);
    mChart.setScaleEnabled(false);

        ArrayList<Entry> yValues = new ArrayList<>();

        yValues.add(new Entry(0, 60f));
        yValues.add(new Entry(1, 20f));
        yValues.add(new Entry(2, 30f));
        yValues.add(new Entry(3, 90f));
        yValues.add(new Entry(4, 10f));
        yValues.add(new Entry(5, 100f));
        LineDataSet test1 = new LineDataSet(yValues, "FIRST SET");

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