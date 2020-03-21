package com.parkingproblem;

import org.junit.Assert;
import org.junit.Test;

public class ParkingLotTest {
    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldReturnTrue() {
        ParkingLot parkingLot = new ParkingLot();
        boolean vehicle = parkingLot.parkTheVehicle(new Object());
        Assert.assertTrue(vehicle);
    }
}
