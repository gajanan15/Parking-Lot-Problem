package com.parkingproblem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParkingSlot implements ParkingLotObserver {
    private int availableSlotNumber;
    private Object vehicle;
    private boolean slotIsFull;
    private boolean isSpaceAvailable;
    List slotNumberList;
    int slotCount;
    private LocalDateTime parkingTime;

    public ParkingSlot(int availableSlotNumber) {
        this.availableSlotNumber = availableSlotNumber;
        slotNumberList = new ArrayList();
    }

    public void setVehicleParkingSlot(Object vehicle) {
        this.vehicle = vehicle;
        slotCount++;
    }

    public LocalDateTime getParkingTime() {
        return parkingTime;
    }

    public void setVehicleAndTime(Object vehicle) {
        this.vehicle = vehicle;
        this.parkingTime = LocalDateTime.now();
    }

    @Override
    public void lotCapacityIsFull() {
        slotIsFull = true;
    }

    @Override
    public void lotSpaceAvailable() {
        isSpaceAvailable = true;
    }

    public boolean isSlotAvailable() {
        if (availableSlotNumber == slotCount)
            throw new ParkingLotException("Slot Is Full", ParkingLotException.ExceptionType.SLOT_IS_FULL);
        return true;
    }
}
