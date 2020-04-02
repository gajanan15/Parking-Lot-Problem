package com.parkingproblem;

public class Vehicle {

    private String location;
    private String attendantName;
    private String plateNumber;
    private String vehicleName;
    private String color;

    public Vehicle(String vehicleName, String color, String plateNumber, String attendantName,String location) {
        this.vehicleName = vehicleName;
        this.color = color;
        this.plateNumber = plateNumber;
        this.attendantName = attendantName;
        this.location = location;
    }

    public String getColor() {
        return color;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getAttendantName() {
        return attendantName;
    }

    public String getLocation(){
        return location;
    }
}
