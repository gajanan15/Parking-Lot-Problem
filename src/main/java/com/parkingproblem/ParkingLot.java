package com.parkingproblem;

public class ParkingLot {
    private Object vehicle;

    public boolean parkTheVehicle(Object vehicle) {
        if (this.vehicle != null)
            return false;
        this.vehicle = vehicle;
        return true;
    }

    public boolean unParkTheVehicle(Object vehicle) {
        if (this.vehicle.equals(vehicle)) {
            this.vehicle = null;
            return true;
        }
        return false;
    }
}
