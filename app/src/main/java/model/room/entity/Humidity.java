package model.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Humidity implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int ID;

    @ColumnInfo(name = "Value")
    private double Value;

    public Humidity(int ID, double Value) {
        this.ID = ID;
        this.Value = Value;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double value) {
        Value = value;
    }
}