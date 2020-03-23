package com.parkingproblem;

import java.util.ArrayList;
import java.util.List;

public class ParkingSlot implements ParkingLotObserver {
    private int availableSlotNumber;
    private Object vehicle;
    private boolean slotIsFull;
    List slotNumberList;
    int slotCount;

    public ParkingSlot(int availableSlotNumber) {
        this.availableSlotNumber = availableSlotNumber;
        slotNumberList = new ArrayList();
    }

    public void setVehicleParkingSlot(Object vehicle, int slotNumber) {
        this.vehicle = vehicle;
        slotNumberList.add(slotNumber);
        slotCount++;
    }

    public int slotNumberIsAvailable(int availableSlotNumber) {
        return availableSlotNumber;
    }

    @Override
    public void lotCapacityIsFull() {
        slotIsFull = true;
    }

    @Override
    public boolean isSpaceAvailable() {
        return false;
    }

    @Override
    public boolean infromVehicleEnterInLot() {
        return true;
    }

    public boolean isSlotFull() {
        return this.slotIsFull;
    }

    public boolean isSlotAvailable() {
        if (availableSlotNumber == slotCount)
            throw new ParkingLotException("Slot Is Full", ParkingLotException.ExceptionType.SLOT_IS_FULL);
        return true;
    }
}
