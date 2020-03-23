package com.parkingproblem;

public class ParkingSlot implements ParkingLotObserver {
    private int availableSlotNumber;
    private Object vehicle;
    private boolean slotIsFull;
    private int slotNumber;
    int slotCount;

    public ParkingSlot(int availableSlotNumber) {
        this.availableSlotNumber = availableSlotNumber;
    }

    public void setVehicleParkingSlot(Object vehicle, int slotNumber) {
        this.vehicle = vehicle;
        this.slotNumber = slotNumber;
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

    public boolean isSlotFull() {
        return this.slotIsFull;
    }

    public boolean isSlotAvailable() {
        if (availableSlotNumber == slotCount)
            throw new ParkingLotException("Slot Is Full", ParkingLotException.ExceptionType.SLOT_IS_FULL);
        return true;
    }
}
