package com.parkingproblem;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotOwner implements ParkingLotObserver {
    private boolean isFullCapacity;
    List vehicleEntered;
    VehicleClass vehicleClass = new VehicleClass();
    private int parkingTime;

    public ParkingLotOwner() {
        vehicleEntered = new ArrayList();
    }

    @Override
    public void lotCapacityIsFull() {
        isFullCapacity = true;
    }

    @Override
    public boolean isSpaceAvailable() {
        return false;
    }

    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }

    public boolean infromVehicleEnterInLot() {
        vehicleClass.payPerUser();
        return true;
    }

    @Override
    public void setParkingTime(int parkingTime) {
        this.parkingTime=parkingTime;
    }

    @Override
    public int getParkingTime() {
        return parkingTime;
    }
}
