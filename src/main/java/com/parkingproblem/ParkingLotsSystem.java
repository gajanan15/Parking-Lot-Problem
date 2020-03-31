package com.parkingproblem;

import com.parkingproblem.enums.ParkingType;
import com.parkingproblem.exceptions.ParkingLotException;

import java.util.*;
import java.util.stream.Collectors;

public class ParkingLotsSystem {
    List<ParkingLot> parkingLotList;
    Informer informer;
    boolean addLots;

    public ParkingLotsSystem() {
        informer = new Informer();
        this.parkingLotList = new ArrayList<>();
    }

    public void addLot(ParkingLot parkingLot) {
        addLots = this.parkingLotList.add(parkingLot);
    }

    public boolean parkVehicle(Vehicle vehicle, ParkingType type) {
        ParkingLot lot = getParkingLotAvailableSpace();
        boolean parkedVehicle = lot.parkTheVehicle(vehicle, type);
        return parkedVehicle;
    }

    public ParkingLot getParkingLotAvailableSpace() {
        return parkingLotList.stream().sorted(Comparator.comparing(parkingLotList -> parkingLotList.getEmptyParkingSlot().size(),
                Comparator.reverseOrder())).collect(Collectors.toList()).get(0);
    }

    public void registerParkingLots(ParkingLotObserver observer) {
        informer.registerParkingLots(observer);
    }

    public boolean isVehicleParked(Vehicle vehicle) {
        for (ParkingLot parkingLots : this.parkingLotList) {
            if (parkingLots.isVehicleParked(vehicle))
                return true;
        }
        throw new ParkingLotException("No Such Vehicle In Lot", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public boolean unParkVehicle(Vehicle vehicle) throws ParkingLotException {
        for (ParkingLot parkingLot : this.parkingLotList) {
            return parkingLot.unParkTheVehicle(vehicle);
        }
        throw new ParkingLotException("No Such Vehicle In Lot", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public int findVehicle(Vehicle vehicle) {
        for (ParkingLot parkingLot : this.parkingLotList)
            return parkingLot.findVehicle(vehicle);
        throw new ParkingLotException("No Such Vehicle In Lot", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }
}
