package com.parkingproblem;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotOwner implements ParkingLotObserver {
    private boolean isFullCapacity;
    List vehicleEntered;
    VehicleClass vehicleClass = new VehicleClass();

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
}
