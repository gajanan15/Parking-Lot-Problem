package com.parkingproblem;

import com.parkingproblem.enums.DriverType;
import com.parkingproblem.exceptions.ParkingLotException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParkingLot {
    private int actualCapacity;
    private ParkingSlot parkingSlot;
    private List<ParkingSlot> vehicles;
    public int autoParkingLocation;
    Informer informer;

    public ParkingLot(int capacity) {
        informer = new Informer();
        setCapacity(capacity);
    }

    public void setCapacity(int capacity) {
        this.actualCapacity = capacity;
        initializeParkingLot();
    }

    public void initializeParkingLot() {
        vehicles = new ArrayList<>();
        for (int slot = 0; slot < actualCapacity; slot++) {
            vehicles.add(slot, null);
        }
    }

    public boolean parkTheVehicle(Object vehicle, DriverType type) {
        parkingSlot = new ParkingSlot(vehicle);
        if (this.vehicles.size() == this.actualCapacity && !vehicles.contains(null)) {
            informer.notifyParkingIsFull();
            throw new ParkingLotException("No Parking Space Available!!!", ParkingLotException.ExceptionType.PARKING_IS_FULL);
        }
        if (isVehicleParked(vehicle)) {
            throw new ParkingLotException("This Vehicle Is Already Parked", ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK);
        }
        setParkingLocation(vehicle, type);
        return true;
    }

    public void setParkingLocation(Object vehicle, DriverType type) {
        autoParkingLocation = (int) parkingLotAttender(type);
        this.vehicles.set(autoParkingLocation, parkingSlot);
    }

    public Integer parkingLotAttender(DriverType type){
        if(type.HANDICAP.equals(type))
            return getEmptyParkingSlot().stream().sorted().collect(Collectors.toList()).get(0);
        return getEmptyParkingSlot().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(0);
    }


    public boolean unParkTheVehicle(Object vehicle) {
        if (isVehicleParked(vehicle)) {
            this.vehicles.set(this.vehicles.indexOf(parkingSlot), null);
            informer.notifyParkingIsAvailable();
            return true;
        }
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public boolean isVehicleParked(Object vehicle) {
        parkingSlot = new ParkingSlot(vehicle);
        return this.vehicles.contains(parkingSlot);
    }

    public int findVehicle(Object vehicle) {
        if (isVehicleParked(vehicle))
            return this.vehicles.indexOf(parkingSlot);
        throw new ParkingLotException("No Such Vehicle In Lot", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public ArrayList<Integer> getEmptyParkingSlot() {
        ArrayList<Integer> emptyParkingSlots = new ArrayList();
        IntStream.range(0, this.actualCapacity).filter(slot -> vehicles.get(slot) == null).forEach(slot -> emptyParkingSlots.add(slot));
        return emptyParkingSlots;
    }
}
