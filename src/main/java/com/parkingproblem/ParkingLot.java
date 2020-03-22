package com.parkingproblem;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final int actualCapacity;
    private List vehicles;
    public ParkingLotOwner owner;

    public ParkingLot(int capacity) {
        vehicles = new ArrayList();
        this.actualCapacity = capacity;
    }

    public boolean parkTheVehicle(Object vehicle) {
        if (this.vehicles.size() == this.actualCapacity) {
            owner.lotCapacityIsFull();
            throw new ParkingLotException("No Parking Space Available!!!", ParkingLotException.ExceptionType.PARKING_IS_FULL);
        }
        this.vehicles.add(vehicle);
        return true;
    }

    public boolean unParkTheVehicle(Object vehicle) {
        if (this.vehicles.contains(vehicle)) {
            this.vehicles.remove(vehicle);
            return true;
        }
        return false;
    }

    public boolean isVehicleParked(Object vehicle) {
        if (this.vehicles.contains(vehicle)) {
            throw new ParkingLotException("This Vehicle Is Already Parked", ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK);
        }
        return false;
    }

    public void registredOwner(ParkingLotOwner owner) {
        this.owner = owner;
    }
}
