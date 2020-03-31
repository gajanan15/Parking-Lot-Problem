package com.parkingproblem;

public class AirPortSecurityStaff implements ParkingLotObserver {
    private boolean isFullCapacity;
    private boolean isSpaceAvailable;


    @Override
    public void lotCapacityIsFull() {
        this.isFullCapacity = true;
    }

    @Override
    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }

    @Override
    public void lotSpaceAvailable() {
        isSpaceAvailable = true;
    }

    public boolean isSpaceAvailable() {
        return this.isSpaceAvailable;
    }

}
