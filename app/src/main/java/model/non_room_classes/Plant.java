package model.non_room_classes;


import java.io.Serializable;

public class Plant implements Serializable,Comparable {
    String startDate;
    String plantName;
    int wateringFrequency;
    String time;
    double waterPerYards;
    double amountOfLand;
    String harvestDate;
    boolean watered;


    public Plant(String startDate,int wateringFrequency, String time, double waterPerYards,
                 double amountOfLand, String harevestDate, String plantName) {
        this.startDate = startDate;
        this.plantName = plantName;
        this.wateringFrequency = wateringFrequency;
        this.time = time;
        this.waterPerYards = waterPerYards;
        this.amountOfLand = amountOfLand;
        this.harvestDate = harevestDate;
        this.watered = false;
    }

    public Plant(){

    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
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

    public String getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(String harvestDate) {
        this.harvestDate = harvestDate;
    }

    public boolean isWatered() {
        return watered;
    }

    public void setWatered(boolean watered) {
        this.watered = watered;
    }

    @Override
    public int compareTo(Object o) {
        Plant e = (Plant)o;
        float timeInt = Float.parseFloat((e.getTime().replace(":",".")));
        float result = Float.parseFloat((this.getTime().replace(":","."))) - timeInt;
        return (int) result;
    }
}

