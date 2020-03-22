package com.parkingproblem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParkingLotTest {
    private static ParkingLot parkingLot;
    public static Object vehicle, vehicle2, vehicle3;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot(2);
        vehicle = new Object();
        vehicle2 = new Object();
        vehicle3 = new Object();
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
            parkingLot.parkTheVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsUnparked_ShouldReturnTrue() {
        parkingLot.parkTheVehicle(vehicle);
        boolean isUnparked = parkingLot.unParkTheVehicle(vehicle);
        Assert.assertTrue(isUnparked);
    }

    //Parking-Lot Owner
    @Test
    public void givenParkingLot_WhenFull_ShouldInfromTheOwner() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registredParkingLotObserver(owner);
        try {
            parkingLot.parkTheVehicle(vehicle);
            parkingLot.parkTheVehicle(vehicle2);
            parkingLot.parkTheVehicle(vehicle3);
        } catch (ParkingLotException e) {
        }
        boolean isCapacityFull = owner.isCapacityFull();
        Assert.assertTrue(isCapacityFull);
    }

    @Test
    public void givenParkingLot_WhenVehiclesAreParkedAndRemoveOneVehicleTheRemainingSpace_ShouldAddAnotherVehicle() {
        try {
            parkingLot.parkTheVehicle(vehicle);
            parkingLot.parkTheVehicle(vehicle2);
            parkingLot.unParkTheVehicle(vehicle);
            boolean isParkedVehicle = parkingLot.parkTheVehicle(vehicle3);
            Assert.assertTrue(isParkedVehicle);
        } catch (ParkingLotException e) {
        }
    }

    //AirPort Security-Staff
    @Test
    public void givenParkingLot_WhenLotIsFull_ShouldInfromAirPortSecurityStaff() {
        AirPortSecurityStaff airPortSecurity = new AirPortSecurityStaff();
        parkingLot.registredParkingLotObserver(airPortSecurity);
        try {
            parkingLot.parkTheVehicle(vehicle);
            parkingLot.parkTheVehicle(vehicle2);
            parkingLot.parkTheVehicle(vehicle3);
        } catch (ParkingLotException e) {
        }
        boolean isCapacityFull = airPortSecurity.isCapacityFull();
        Assert.assertTrue(isCapacityFull);
    }

    @Test
    public void givenParkingLot_WhenSpaceAvailable_ShouldReturnTrue() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registredParkingLotObserver(owner);
        try {
            parkingLot.parkTheVehicle(vehicle);
            parkingLot.parkTheVehicle(vehicle2);
            parkingLot.parkTheVehicle(vehicle3);
        } catch (ParkingLotException e) {
        }
        parkingLot.unParkTheVehicle(vehicle2);
        boolean spaceAvailable = owner.isCapacityFull();
        Assert.assertTrue(spaceAvailable);
    }
}
