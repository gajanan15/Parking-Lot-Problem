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

    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }
}
