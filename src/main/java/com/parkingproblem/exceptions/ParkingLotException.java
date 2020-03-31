package com.parkingproblem.exceptions;

public class ParkingLotException extends RuntimeException {
    public ExceptionType type;

    public enum ExceptionType {
        PARKING_IS_FULL, VEHICLE_IS_ALREADY_PARK, VEHICLE_NOT_FOUND, NO_VEHICLE_IN_LOT;
    }

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
