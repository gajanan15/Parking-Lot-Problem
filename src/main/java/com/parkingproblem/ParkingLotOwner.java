package com.parkingproblem;

public class ParkingLotOwner implements ParkingLotObserver {
    private boolean isFullCapacity;

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
}
