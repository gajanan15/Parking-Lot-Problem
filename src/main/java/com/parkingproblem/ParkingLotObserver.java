package com.parkingproblem;

public interface ParkingLotObserver {
    void lotCapacityIsFull();

    boolean isSpaceAvailable();

    boolean infromVehicleEnterInLot();

}
