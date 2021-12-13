package view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.SEP7_IrrigationApp.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Collections;

import model.non_room_classes.Plant;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

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