package com.parkingproblem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {
    private static ParkingLot parkingLot;
    public static Object vehicle;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot();
        vehicle = new Object();
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldReturnTrue() {
        boolean isParked = parkingLot.parkTheVehicle(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenParkingLot_WhenVehicleAlreadyParked_ShouldReturnFalse() {
        parkingLot.parkTheVehicle(vehicle);
        boolean isParked = parkingLot.parkTheVehicle(vehicle);
        Assert.assertFalse(isParked);
    }

    @Test
    public void givenParkingLot_WhenVehicleIsUnparked_ShouldReturnTrue() {
        parkingLot.parkTheVehicle(vehicle);
        boolean isUnparked = parkingLot.unParkTheVehicle(vehicle);
        Assert.assertTrue(isUnparked);
    }
}
