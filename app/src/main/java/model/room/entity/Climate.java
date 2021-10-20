package model.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Climate implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int ID;
    @ColumnInfo(name = "Temperature")
    private Double Temperature;
    @ColumnInfo(name = "Humidity")
    private Double Humidity;
    @ColumnInfo(name = "Precipitation")
    private String Precipitation;

    public Climate(int ID, Double Temperature, Double Humidity, String Precipitation) {
        this.ID = ID;
        Temperature = Temperature;
        Humidity = Humidity;
        Precipitation = Precipitation;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Double getTemperature() {
        return Temperature;
    }

    public void setTemperature(Double temperature) {
        Temperature = temperature;
    }

    public Double getHumidity() {
        return Humidity;
    }

    public void setHumidity(Double humidity) {
        Humidity = humidity;
    }

    public String getPrecipitation() {
        return Precipitation;
    }

    public void setPrecipitation(String precipitation) {
        Precipitation = precipitation;
    }
}