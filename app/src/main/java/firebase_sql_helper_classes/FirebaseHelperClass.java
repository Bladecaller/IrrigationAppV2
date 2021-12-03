package firebase_sql_helper_classes;


import java.util.ArrayList;

public class FirebaseHelperClass {

    String username;
    String plantName;
    int wateringFrequency;
    String time;
    double waterPerYards;
    double amountOfLand;
    double harvestAfterMonths;

    public static ArrayList<FirebaseHelperClass>plantsList = new ArrayList<>();


    public FirebaseHelperClass(int wateringFrequency, String time, double waterPerYards, double amountOfLand, double harvestAfterMonths) {
        this.plantName = plantName;
        this.wateringFrequency = wateringFrequency;
        this.time = time;
        this.waterPerYards = waterPerYards;
        this.amountOfLand = amountOfLand;
        this.harvestAfterMonths = harvestAfterMonths;
    }


    public FirebaseHelperClass() {
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

}
