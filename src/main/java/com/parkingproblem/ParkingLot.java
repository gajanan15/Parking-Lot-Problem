package com.parkingproblem;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final int actualCapacity;
    private List vehicles;
    private List<ParkingLotObserver> observer;

    public ParkingLot(int capacity) {
        vehicles = new ArrayList();
        observer = new ArrayList();
        this.actualCapacity = capacity;
    }

    public void registredParkingLotObserver(ParkingLotObserver observer) {
        this.observer.add(observer);
    }

    public boolean parkTheVehicle(Object vehicle) {
        if (this.vehicles.size() == this.actualCapacity) {
            for (ParkingLotObserver observer : observer) {
                observer.lotCapacityIsFull();
            }
            throw new ParkingLotException("No Parking Space Available!!!", ParkingLotException.ExceptionType.PARKING_IS_FULL);
        }
        if (isVehicleParked(vehicle))
            throw new ParkingLotException("This Vehicle Is Already Parked", ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK);
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
            return true;
        }
        return false;
    }
}
