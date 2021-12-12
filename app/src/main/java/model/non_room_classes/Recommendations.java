package model.non_room_classes;

import java.io.Serializable;

public class Recommendations implements Serializable {

    private String name;

    private int waterFrequency;

    private double waterPerYard;

    private double yards;

    public Recommendations(String name, int waterFrequency, double waterPerYard, double yards) {
        this.name = name;
        this.waterFrequency = waterFrequency;
        this.waterPerYard = waterPerYard;
        this.yards = yards;


    }
    public String getName() {
        return name;
    }

    public int getWaterFrequency() {
        return waterFrequency;
    }

    public double getWaterPerYard() {
        return waterPerYard;
    }

    public double getYards() {
        return yards;
    }


}
