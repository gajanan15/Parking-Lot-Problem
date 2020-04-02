package com.parkingproblem;

import com.parkingproblem.enums.ParkingType;

public class Vehicle {

    private ParkingType type;
    private String location;
    private String attendantName;
    private String plateNumber;
    private String vehicleName;
    private String color;

    public Vehicle(String vehicleName, String color, String plateNumber, String attendantName,String location,ParkingType type) {
        this.vehicleName = vehicleName;
        this.color = color;
        this.plateNumber = plateNumber;
        this.attendantName = attendantName;
        this.location = location;
        this.type=type;
    }

    public String getColor() {
        return color;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public ParkingType type(){
        return type;
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
