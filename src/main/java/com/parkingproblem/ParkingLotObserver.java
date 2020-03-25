package com.parkingproblem;

public interface ParkingLotObserver {
    void lotCapacityIsFull();

    boolean isSpaceAvailable();

    boolean infromVehicleEnterInLot();

    void setParkingTime(int time);

    int getParkingTime();
}
