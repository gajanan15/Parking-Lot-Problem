package com.parkingproblem;

import com.parkingproblem.enums.ParkingType;
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
    public List<ParkingSlot> vehicles;
    private int vehicleCount;
    public int parkingLocation;
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

    public boolean parkTheVehicle(Vehicle vehicle) {
        parkingSlot = new ParkingSlot(vehicle);
        if (this.vehicles.size() == this.actualCapacity && !vehicles.contains(null)) {
            informer.notifyParkingIsFull();
            throw new ParkingLotException("No Parking Space Available!!!", ParkingLotException.ExceptionType.PARKING_IS_FULL);
        }
        if (isVehicleParked(vehicle)) {
            throw new ParkingLotException("This Vehicle Is Already Parked", ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK);
        }
        setParkingLocation(vehicle.type());
        vehicleCount++;
        parkingSlot.setVehicleAndTime(vehicle);
        parkingSlot.setSlot(vehicleCount);
        return true;
    }

    public void setParkingLocation(ParkingType type) {
        parkingLocation = parkingLotAttender(type);
        this.vehicles.set(parkingLocation, parkingSlot);
    }

    public Integer parkingLotAttender(ParkingType type) {
        if (type.HANDICAP.equals(type))
            return getEmptyParkingSlot().stream().sorted().collect(Collectors.toList()).get(0);
        if (type.LARGE.equals(type))
            return getLargeVehicle();
        return getEmptyParkingSlot().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(0);
    }

    private Integer getLargeVehicle() {
        if ((actualCapacity - vehicleCount) > 2) {
            if (parkingLocation == 0)
                return parkingLocation + 2;
            return parkingLocation - 2;
        }
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
        parkingSlot = new ParkingSlot(vehicle);
        return this.vehicles.contains(parkingSlot);
    }

    public int findVehicle(Vehicle vehicle) {
        if (isVehicleParked(vehicle))
            return this.vehicles.indexOf(parkingSlot);
        throw new ParkingLotException("No Such Vehicle In Lot", ParkingLotException.ExceptionType.NO_SUCH_VEHICLE_IN_LOT);
    }

    public ArrayList<Integer> getEmptyParkingSlot() {
        ArrayList<Integer> emptyParkingSlots = new ArrayList();
        IntStream.range(0, this.actualCapacity).filter(slot -> vehicles.get(slot) == null).forEach(slot -> emptyParkingSlots.add(slot));
        return emptyParkingSlots;
    }

    public List<String> getWhiteColorVehicleSlot(String carColor) {
        List<String> whiteColorSlot = new ArrayList<>();
        IntStream.range(0, this.actualCapacity).filter(slot -> vehicles.get(slot) != null)
                .filter(slot -> vehicles.get(slot).getVehicle().getColor().equals(carColor))
                .forEach(slot -> whiteColorSlot.add(vehicles.get(slot).getVehicle().getLocation()));
        return whiteColorSlot;
    }

    public List<String> getLocationOfBlueToyotaCar(String carColor, String vehicleName) {
        List<String> carColorAndName = new ArrayList<>();
        IntStream.range(0, this.actualCapacity).filter(slot -> vehicles.get(slot) != null)
                .filter(slot -> vehicles.get(slot).getVehicle().getColor().equals(carColor) &&
                        vehicles.get(slot).getVehicle().getVehicleName().equals(vehicleName))
                .forEach(slot -> carColorAndName.add(vehicles.get(slot).getVehicle().getLocation()));
        return carColorAndName;
    }

    public List<String> getTotalBMWCarsParkedInLots(String carModelName) {
        List<String> allBMWCars = new ArrayList<>();
        IntStream.range(0, this.actualCapacity).filter(slot -> vehicles.get(slot) != null)
                .filter(slot -> vehicles.get(slot).getVehicle().getVehicleName().equals(carModelName))
                .forEach(slot -> allBMWCars.add(vehicles.get(slot).getVehicle().getLocation()));
        return allBMWCars;
    }

    public List<String> getVehicleAreParkedLast30Minutes() {
        List<String> findVehicleLast30Minutes = new ArrayList<>();
        IntStream.range(0, this.actualCapacity).filter(slot -> vehicles.get(slot) != null)
                .filter(slot -> (LocalDateTime.now().getMinute() - vehicles.get(slot).getParkingTime().getMinute()) <= 30)
                .forEach(slot -> findVehicleLast30Minutes.add(vehicles.get(slot).getVehicle().getLocation()));
        return findVehicleLast30Minutes;
    }

    public List<String> detailsOfHandicappedDriver(ParkingType type) {
        List<String> driverDetails = new ArrayList<>();
        IntStream.range(0, this.actualCapacity).filter(slot -> vehicles.get(slot) != null)
                .filter(slot -> vehicles.get(slot).getVehicle().type().equals(type))
                .forEach(slot -> driverDetails.add(vehicles.get(slot).getVehicle().getLocation() + " " + vehicles.get(slot).getVehicle().getPlateNumber()));
        return driverDetails;
    }

    public List<String> getAllVehicleCount() {
        List<String> totalVehicles = new ArrayList<>();
        IntStream.range(0, this.actualCapacity).filter(slot -> vehicles.get(slot) != null)
                .forEach(slot -> totalVehicles.add(vehicles.get(slot).getVehicle().getVehicleName()));
        return totalVehicles;
    }
}
