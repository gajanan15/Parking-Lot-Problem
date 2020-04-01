package com.parkingproblem;

public class Vehicle {

    private String attendantName;
    private String plateNumber;
    private String vehicleName;
    private String color;

    public Vehicle(String vehicleName, String color, String plateNumber, String attendantName) {
        this.vehicleName = vehicleName;
        this.color = color;
        this.plateNumber = plateNumber;
        this.attendantName = attendantName;
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
}
