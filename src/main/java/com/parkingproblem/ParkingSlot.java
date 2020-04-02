package com.parkingproblem;

import java.time.LocalDateTime;

public class ParkingSlot {
    protected Vehicle vehicle;
    public LocalDateTime parkingTime;
    public int slotNumber;

    public ParkingSlot(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingSlot() {
    }

    public void setSlot(int slotNumber) {
        this.slotNumber = slotNumber;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public void setVehicleParkingSlot(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getParkingTime() {
        return this.parkingTime;
    }

    public void setVehicleAndTime(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.parkingTime = LocalDateTime.now();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ParkingSlot that = (ParkingSlot) obj;
        return vehicle.equals(that.vehicle);
    }
}
