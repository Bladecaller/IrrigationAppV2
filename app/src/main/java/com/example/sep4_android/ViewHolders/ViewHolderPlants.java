package com.example.sep4_android.ViewHolders;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4_android.R;

import org.jetbrains.annotations.NotNull;

import firebase_sql_helper_classes.Plant;

public class ViewHolderPlants extends RecyclerView.ViewHolder {
    public TextView timeDis,nameDis;
    public boolean clicked;

    public ViewHolderPlants(@NonNull @NotNull View itemView) {
        super(itemView);
        clicked = false;
    }

    public void setItem(Activity activity, String startDate, int wateringFrequency, String time, double waterPerYards,
                        double amountOfLand, String harvestDate, String plantName){
        timeDis = itemView.findViewById(R.id.time_item_tv);
        nameDis = itemView.findViewById(R.id.name_item_tv);
        timeDis.setText(time);
        nameDis.setText(plantName);

    }
    public void destroy(){
        itemView.setVisibility(View.GONE);
    }
}

