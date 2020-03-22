package com.parkingproblem;

public class ParkingLot {
    private int currentCapacity;
    private final int actualCapacity;
    private Object vehicle;
    public ParkingLotOwner owner;

    public ParkingLot(int capacity) {
        this.currentCapacity=0;
        this.actualCapacity=capacity;
    }

    public boolean parkTheVehicle(Object vehicle) {
        if (this.currentCapacity == this.actualCapacity) {
            owner.lotCapacityIsFull();
            throw new ParkingLotException("No Parking Space Available!!!", ParkingLotException.ExceptionType.PARKING_IS_FULL);
        }
            currentCapacity++;
        this.vehicle = vehicle;
        return true;
    }

    public boolean unParkTheVehicle(Object vehicle) {
        if (this.vehicle.equals(vehicle)) {
            this.vehicle = null;
            return true;
        }
        return false;
    }

    public boolean isVehicleParked(Object vehicle) {
        if(this.vehicle.equals(vehicle)){
            throw new ParkingLotException("This Vehicle Is Already Parked", ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK);
        }
        return false;
    }

    public void registredOwner(ParkingLotOwner owner) {
        this.owner=owner;
    }
}
