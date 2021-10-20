package model.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Temperature implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int ID;

    @ColumnInfo(name = "Value")
    private Double Value;

    public Temperature(int ID, Double Value) {
        this.ID = ID;
        Value = Value;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Double getValue() {
        return Value;
    }

    public void setValue(Double value) {
        Value = value;
    }
}
