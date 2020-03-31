package com.parkingproblem;

import com.parkingproblem.enums.ParkingType;
import com.parkingproblem.exceptions.ParkingLotException;

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

    int slot;
    public void initializeParkingLot() {
        vehicles = new ArrayList<>();
        for ( slot = 0; slot < actualCapacity; slot++) {
            vehicles.add(slot, null);
        }
    }

    public boolean parkTheVehicle(Vehicle vehicle, ParkingType type) {
        parkingSlot = new ParkingSlot(vehicle,slot);
        if (this.vehicles.size() == this.actualCapacity && !vehicles.contains(null)) {
            informer.notifyParkingIsFull();
            throw new ParkingLotException("No Parking Space Available!!!", ParkingLotException.ExceptionType.PARKING_IS_FULL);
        }
        if (isVehicleParked(vehicle)) {
            throw new ParkingLotException("This Vehicle Is Already Parked", ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK);
        }
        setParkingLocation(type);
        return true;
    }

    public void setParkingLocation(ParkingType type) {
        autoParkingLocation = (int) parkingLotAttender(type);
        this.vehicles.set(autoParkingLocation, parkingSlot);
    }

    public Integer parkingLotAttender(ParkingType type){
        if(type.HANDICAP.equals(type))
            return getEmptyParkingSlot().stream().sorted().collect(Collectors.toList()).get(0);
        return getEmptyParkingSlot().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(0);
    }


    public boolean unParkTheVehicle(Vehicle vehicle) {
        if (isVehicleParked(vehicle)) {
            this.vehicles.set(this.vehicles.indexOf(parkingSlot), null);
            informer.notifyParkingIsAvailable();
            return true;
        }
        throw new ParkingLotException("VEHICLE IS NOT AVAILABLE", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        parkingSlot = new ParkingSlot(vehicle,slot);
        return this.vehicles.contains(parkingSlot);
    }

    public int findVehicle(Vehicle vehicle) {
        if (isVehicleParked(vehicle))
            return this.vehicles.indexOf(parkingSlot);
        throw new ParkingLotException("No Such Vehicle In Lot", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public ArrayList<Integer> getEmptyParkingSlot() {
        ArrayList<Integer> emptyParkingSlots = new ArrayList();
        IntStream.range(0, this.actualCapacity).filter(slot -> vehicles.get(slot) == null).forEach(slot -> emptyParkingSlots.add(slot));
        return emptyParkingSlots;
    }

    public ArrayList<Integer> getSlotOfWhiteColorVehicle(String findByColor) {
        ArrayList<Integer> collectSlot = vehicles.stream().filter(slot -> slot.getVehicle() != null)
                .filter(slot -> slot.getVehicle().getColor().equals(findByColor))
                .map(parkingSlot -> parkingSlot.getSlotNum()).collect(Collectors.toCollection(ArrayList::new));
        collectSlot.forEach(System.out::println);
        return collectSlot;
    }
}
