package com.parkingproblem;

public class AirPortSecurityStaff implements ParkingLotObserver {
    private boolean isFullCapacity;
    private boolean isSpaceAvailable;

    public void lotCapacityIsFull() {
        isFullCapacity = true;
    }

    @Override
    public void lotSpaceAvailable() {
        isSpaceAvailable = true;
    }

    public boolean isSpaceAvailable() {
        return this.isSpaceAvailable;
    }

    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }
}
