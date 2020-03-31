package com.parkingproblem;

import java.time.LocalDateTime;

public class ParkingSlot {
    private Object vehicle;
    public LocalDateTime parkingTime;

    public ParkingSlot(Object vehicle) {
        this.vehicle = vehicle;
    }

    public void setVehicleParkingSlot(Object vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getParkingTime() {
        return parkingTime;
    }

    public void setVehicleAndTime(Object vehicle) {
        this.vehicle = vehicle;
        this.parkingTime = LocalDateTime.now();
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
