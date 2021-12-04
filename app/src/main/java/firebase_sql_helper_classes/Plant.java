package firebase_sql_helper_classes;


import com.example.sep4_android.Event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Plant {
    LocalDate date;
    String username;
    String plantName;
    int wateringFrequency;
    String time;
    double waterPerYards;
    double amountOfLand;
    double harvestAfterMonths;
    String convertedDate;



    public static ArrayList<Plant> plantsForDate(LocalDate dateStart, LocalDate harvest, ArrayList<Plant> addedPlants)
    {
        ArrayList<Plant> plants = new ArrayList<>();
        for(Plant plant : addedPlants)
        {
            if(plant.getDate().isAfter(dateStart) && plant.getDate().isBefore(harvest))
                System.out.println("ADDING A PLANT");
            System.out.println("ADDING A PLANT");
            System.out.println("ADDING A PLANT");
                plants.add(plant);
        }

        return plants;
    }


    public Plant(int wateringFrequency, String time, double waterPerYards, double amountOfLand, double harvestAfterMonths,String convertedDate) {
        this.plantName = plantName;
        this.wateringFrequency = wateringFrequency;
        this.time = time;
        this.waterPerYards = waterPerYards;
        this.amountOfLand = amountOfLand;
        this.harvestAfterMonths = harvestAfterMonths;
        this.convertedDate = convertedDate;
    }

    public Plant(String plantName, String time, LocalDate date) {
        this.plantName = plantName;
        this.time = time;
        this.date= date;
    }

    public Plant() {
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public int getWateringFrequency() {
        return wateringFrequency;
    }

    public void setWateringFrequency(int wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getWaterPerYards() {
        return waterPerYards;
    }

    public void setWaterPerYards(double waterPerYards) {
        this.waterPerYards = waterPerYards;
    }

    public double getAmountOfLand() {
        return amountOfLand;
    }

    public void setAmountOfLand(double amountOfLand) {
        this.amountOfLand = amountOfLand;
    }

    public double getHarvestAfterMonths() {
        return harvestAfterMonths;
    }

    public void setHarvestAfterMonths(double harvestAfterMonths) {
        this.harvestAfterMonths = harvestAfterMonths;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getConvertedDate() {
        return convertedDate;
    }

    public void setConvertedDate(String convertedDate) {
        this.convertedDate = convertedDate;
    }
}
