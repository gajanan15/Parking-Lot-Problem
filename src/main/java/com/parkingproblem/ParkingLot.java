package com.parkingproblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {
    ParkingLotOwner owner;
    private int actualCapacity;
    private List vehicles;
    private List<ParkingLotObserver> observer;

    public ParkingLot(int capacity) {
        this.observer = new ArrayList();
        owner = new ParkingLotOwner();
        setCapacity(capacity);
    }

    public void setCapacity(int capacity) {
        this.actualCapacity = capacity;
        initializeParkingLot();
    }

    public void initializeParkingLot() {
        vehicles = new ArrayList();
        for (int slot = 0; slot < actualCapacity; slot++) {
            vehicles.add(slot, null);
        }
    }

    public void registeredParkingLotObserver(ParkingLotObserver observer) {
        this.observer.add(observer);
    }

    public boolean parkTheVehicle(Object vehicle, DriverType type) {
        if (vehicle != null) {
            if (this.vehicles.size() == this.actualCapacity && !vehicles.contains(null)) {
                for (ParkingLotObserver observer : observer) {
                    observer.lotCapacityIsFull();
                }
                throw new ParkingLotException("No Parking Space Available!!!", ParkingLotException.ExceptionType.PARKING_IS_FULL);
            }
            if (isVehicleParked(vehicle)) {
                throw new ParkingLotException("This Vehicle Is Already Parked", ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK);
            }
            getAutoParkingLocation(vehicle, type);
            return true;
        }
        return false;
    }

    public void getAutoParkingLocation(Object vehicle, DriverType type) {
        int autoParkingLocation = (int) parkingAttender(type).get(0);
        this.vehicles.set(autoParkingLocation, vehicle);
    }

    public List parkingAttender(DriverType driverType) {
        List emptyParkingSlotList = getEmptyParkingSlot();
        if (DriverType.HANDICAP.equals(driverType))
            Collections.sort(emptyParkingSlotList);
        else if (DriverType.NORMAL.equals(driverType))
            Collections.sort(emptyParkingSlotList, Collections.reverseOrder());
        return emptyParkingSlotList;
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
        if (vehicles.contains(vehicle))
            return this.vehicles.indexOf(vehicle);
        throw new ParkingLotException("No Such Vehicle In Lot", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public List getEmptyParkingSlot() {
        ArrayList<Integer> emptyParkingSlots = new ArrayList();
        IntStream.range(0, this.actualCapacity).filter(slot -> vehicles.get(slot) == null).forEach(slot -> emptyParkingSlots.add(slot));
        return emptyParkingSlots;
    }
}
