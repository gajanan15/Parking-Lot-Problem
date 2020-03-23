package com.parkingproblem;

public class ParkingLotException extends RuntimeException {
    public ExceptionType type;

    public enum ExceptionType {
        PARKING_IS_FULL, VEHICLE_IS_ALREADY_PARK, SLOT_IS_FULL, VEHICLE_NOT_FOUND;
    }

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
