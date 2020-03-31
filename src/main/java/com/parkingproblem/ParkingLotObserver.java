package com.parkingproblem;

public interface ParkingLotObserver {
    void lotCapacityIsFull();

    boolean isCapacityFull();

    void lotSpaceAvailable();
}
