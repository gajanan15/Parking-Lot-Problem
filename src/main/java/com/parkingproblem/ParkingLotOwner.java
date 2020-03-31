package com.parkingproblem;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotOwner implements ParkingLotObserver {
    private boolean isFullCapacity;
    private boolean isSpaceAvailable;
    List vehicleEntered;

    public ParkingLotOwner() {
        vehicleEntered = new ArrayList();
    }

    @Override
    public void lotCapacityIsFull() {
        isFullCapacity = true;
    }

    @Override
    public boolean isCapacityFull() {
        return this.isFullCapacity;
    }

    @Override
    public void lotSpaceAvailable() {
        isSpaceAvailable = false;
    }

    public boolean isSpaceAvailable() {
        return this.isSpaceAvailable;
    }
}
