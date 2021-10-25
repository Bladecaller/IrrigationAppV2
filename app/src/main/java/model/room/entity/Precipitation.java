package model.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Precipitation implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "ID")
    private int ID;

    @ColumnInfo(name = "Value")
    private String Value;

    public Precipitation(int ID, String Value) {
        this.ID = ID;
        this.Value = Value;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
