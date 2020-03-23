package com.parkingproblem;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final int actualCapacity;
    private List vehicles;
    List slot;
    public VehicleClass chargeVehicle;
    private ParkingLotOwner owner;
    private List<ParkingLotObserver> observer;

    public ParkingLot(int capacity) {
        vehicles = new ArrayList();
        observer = new ArrayList();
        slot = new ArrayList();
        owner = new ParkingLotOwner();
        this.actualCapacity = capacity;
    }

    public void registeredParkingLotObserver(ParkingLotObserver observer) {
        this.observer.add(observer);
    }

    public Boolean parkTheVehicle(Object vehicle) {
        if (vehicle != null) {
            slot.add(vehicle);
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
        return true;
    }

    public boolean unParkTheVehicle(Object vehicle) {
        if (this.vehicles.contains(vehicle)) {
            owner.infromVehicleEnterInLot();
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

    public boolean findVehicle(Object vehicle) {
        if (slot.contains(vehicle))
            return true;
        throw new ParkingLotException("No Such Vehicle In Lot", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }
}
