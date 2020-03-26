package com.parkingproblem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParkingSlot implements ParkingLotObserver {
    private int availableSlotNumber;
    private Object vehicle;
    private boolean slotIsFull;
    List slotNumberList;
    int slotCount;
    private LocalDateTime time;

    public ParkingSlot(int availableSlotNumber) {
        this.availableSlotNumber = availableSlotNumber;
        slotNumberList = new ArrayList();
    }

    public ParkingSlot(Object vehicle) {
        this.vehicle = vehicle;
    }

    public void setVehicleParkingSlot(Object vehicle) {
        this.vehicle = vehicle;
        slotCount++;
    }

    public int slotNumberIsAvailable(int availableSlotNumber) {
        return availableSlotNumber;
    }

    public void setParkingTimeOfVehicle(Object vehicle) {
        this.vehicle = vehicle;
        this.time = LocalDateTime.now();

    }

    public Object getVehicle() {
        return vehicle;
    }

    public LocalDateTime getTimeOfParking() {
        return time;
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
