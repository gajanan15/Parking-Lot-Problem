package com.parkingproblem;

public class AirPortSecurityStaff implements ParkingLotObserver {
    private boolean isFullCapacity;

    public void lotCapacityIsFull() {
        isFullCapacity = true;
    }

    @Override
    public boolean isSpaceAvailable() {
        return false;
    }

    @Override
    public boolean infromVehicleEnterInLot() {
        return true;
    }

    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }
}
