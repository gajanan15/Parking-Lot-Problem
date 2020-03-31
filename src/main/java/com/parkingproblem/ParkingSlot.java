package com.parkingproblem;

import java.time.LocalDateTime;

public class ParkingSlot {
    protected Vehicle vehicle;
    public LocalDateTime parkingTime;
    private int slotNumber;

    public ParkingSlot(Vehicle vehicle,int slotNumber) {
        this.vehicle = vehicle;
        this.slotNumber = slotNumber;
    }

    public int getSlotNum() {
        return slotNumber;
    }

    public void setVehicleParkingSlot(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getParkingTime() {
        return parkingTime;
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
