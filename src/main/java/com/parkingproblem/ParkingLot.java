package com.parkingproblem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {
    ParkingLotOwner owner = new ParkingLotOwner();
    private int actualCapacity;
    private List vehicles;
    List slot;
    VehicleClass chargeVehicle = new VehicleClass();
    private List<ParkingLotObserver> observer;

    public ParkingLot(int capacity) {
        vehicles = new ArrayList();
        this.observer = new ArrayList();
        slot = new ArrayList();
        owner = new ParkingLotOwner();
        this.actualCapacity = capacity;
        setCapacity(capacity);
    }

    public void setCapacity(int capacity) {
        this.actualCapacity = capacity;
    }

    public void registeredParkingLotObserver(ParkingLotObserver observer) {
        this.observer.add(observer);
    }

    public boolean parkTheVehicle(Object vehicle, DriverType type) {
        if (vehicle != null) {
            slot.add(vehicle);
            if (this.vehicles.size() == this.actualCapacity && !vehicles.contains(null)) {
                for (ParkingLotObserver observer : observer) {
                    observer.lotCapacityIsFull();
                }
                throw new ParkingLotException("No Parking Space Available!!!", ParkingLotException.ExceptionType.PARKING_IS_FULL);
            }
            if (isVehicleParked(vehicle)) {
                throw new ParkingLotException("This Vehicle Is Already Parked", ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK);
            }
            this.vehicles.add(vehicle);
            return true;
        }
        return false;
    }


    public boolean unParkTheVehicle(Object vehicle) {
        if (this.vehicles.contains(vehicle)) {
            owner.infromVehicleEnterInLot();
            this.vehicles.set(this.vehicles.indexOf(vehicle), null);
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

    public int findVehicle(Object vehicle) {
        if (slot.contains(vehicle))
            return this.vehicles.indexOf(vehicle);
        throw new ParkingLotException("No Such Vehicle In Lot", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public ArrayList getEmptyParkingSlot() {
        ArrayList<Integer> emptyParkingSlots = new ArrayList();
        IntStream.range(0, this.actualCapacity).filter(slot -> vehicles.get(slot) == null).forEach(slot -> emptyParkingSlots.add(slot));
        return emptyParkingSlots;
    }
}
