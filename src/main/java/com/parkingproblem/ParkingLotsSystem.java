package com.parkingproblem;

import com.parkingproblem.enums.DriverType;
import com.parkingproblem.exceptions.ParkingLotException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ParkingLotsSystem {
    List<ParkingLot> parkingLotList;
    Informer informer;
    boolean addLot;

    public ParkingLotsSystem() {
        informer = new Informer();
        this.parkingLotList = new ArrayList<>();
    }

    public void addLot(ParkingLot parkingLot) {
        addLot = this.parkingLotList.add(parkingLot);
    }

    public boolean parkVehicle(Object vehicle, DriverType type) {
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

    public boolean unParkVehicle(Object vehicle) throws ParkingLotException {
        for (ParkingLot parkingLot : this.parkingLotList) {
            return parkingLot.unParkTheVehicle(vehicle);
        }
        throw new ParkingLotException("No Such Vehicle In Lo", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public int findVehicle(Object vehicle) {
        for (ParkingLot parkingLot : this.parkingLotList)
            return parkingLot.findVehicle(vehicle);
        throw new ParkingLotException("No Such Vehicle In Lot", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }
}
