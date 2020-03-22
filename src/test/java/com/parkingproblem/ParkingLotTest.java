package com.parkingproblem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {
    private static ParkingLot parkingLot;
    public static Object vehicle;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot(1);
        vehicle = new Object();
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldReturnTrue() {
        boolean isParked = parkingLot.parkTheVehicle(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenParkingLot_WhenVehicleAlreadyParked_ShouldReturnFalse() {
        try {
            parkingLot.parkTheVehicle(vehicle);
            parkingLot.isVehicleParked(vehicle);
        }
        catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK,e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsUnparked_ShouldReturnTrue() {
        parkingLot.parkTheVehicle(vehicle);
        boolean isUnparked = parkingLot.unParkTheVehicle(vehicle);
        Assert.assertTrue(isUnparked);
    }

    @Test
    public void givenParkingLot_WhenFull_ShouldInfromTheOwner() {
        Object vehicle2 = new Object();
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registredOwner(owner);
        try {
            parkingLot.parkTheVehicle(vehicle);
            parkingLot.parkTheVehicle(vehicle2);
        }catch (ParkingLotException e){
            boolean isCapacityFull = owner.isCapacityFull();
            Assert.assertTrue(isCapacityFull);
        }
    }
}
