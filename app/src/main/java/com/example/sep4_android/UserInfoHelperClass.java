package com.example.sep4_android;

public class UserInfoHelperClass {
    String location;
    String luminosityLocation;
    String electricityLocation;

    public UserInfoHelperClass(String location, String luminosityLocation, String electricityLocation) {
        this.location = location;
        this.luminosityLocation = luminosityLocation;
        this.electricityLocation = electricityLocation;
    }

    public UserInfoHelperClass() {
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLuminosityLocation() {
        return luminosityLocation;
    }

    public void setLuminosityLocation(String luminosityLocation) {
        this.luminosityLocation = luminosityLocation;
    }

    public String getElectricityLocation() {
        return electricityLocation;
    }

    public void setElectricityLocation(String electricityLocation) {
        this.electricityLocation = electricityLocation;
    }
}
