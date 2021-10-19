package model.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Account implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "UserID")
    private int UserID;

    @ColumnInfo(name = "Username")
    private String Username;

    @ColumnInfo(name = "Password")
    private String Password;

    @ColumnInfo(name = "Email")
    private String Email;

    @ColumnInfo(name = "Schedule")
    private String Schedule;

    @ColumnInfo(name = "Location")
    private String Location;

    @ColumnInfo(name = "Electricity")
    private String Electricity;

    public Account(@NonNull int UserID, String Username, String Password, String Email
    , String Schedule, String Location, String Electricity){
        this.UserID = UserID;
        this.Username = Username;
        this.Password = Password;
        this.Email = Email;
        this.Schedule = Schedule;
        this.Location = Location;
        this.Electricity = Electricity;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getEmail() {
        return Email;
    }

    public String getSchedule() {
        return Schedule;
    }

    public void setSchedule(String schedule) {
        Schedule = schedule;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getElectricity() {
        return Electricity;
    }

    public void setElectricity(String electricity) {
        Electricity = electricity;
    }
}