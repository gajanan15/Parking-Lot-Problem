package com.parkingproblem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class ParkingLotTest {
    private static ParkingLot parkingLot;
    private static ParkingLot parkingLot1;
    private static ParkingSlot parkingSlot;
    public static Object vehicle, vehicle2, vehicle3, vehicle4, vehicle5;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot(2);
        parkingLot1 = new ParkingLot(5);
        parkingSlot = new ParkingSlot(2);
        vehicle = new Object();
        vehicle2 = new Object();
        vehicle3 = new Object();
        vehicle4 = new Object();
        vehicle5 = new Object();
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldReturnTrue() {
        parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
        boolean isParked = parkingLot.isVehicleParked(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenParkingLot_WhenVehicleAlreadyParked_ShouldReturnFalse() {
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsUnparked_ShouldReturnTrue() {
        parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
        boolean isUnparked = parkingLot.unParkTheVehicle(vehicle);
        Assert.assertTrue(isUnparked);
    }

    //Parking-Lot Owner
    @Test
    public void givenParkingLot_WhenFull_ShouldInfromTheOwner() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registeredParkingLotObserver(owner);
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL);
        } catch (ParkingLotException e) {
            boolean isCapacityFull = owner.isCapacityFull();
            Assert.assertTrue(isCapacityFull);
        }
    }

    @Test
    public void givenParkingLot_WhenVehiclesAreParkedAndRemoveOneVehicleTheRemainingSpace_ShouldAddAnotherVehicle() {
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLot.unParkTheVehicle(vehicle);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL);
            boolean isParkedVehicle = parkingLot.isVehicleParked(vehicle3);
            Assert.assertTrue(isParkedVehicle);
        } catch (ParkingLotException e) {
        }
    }

    //AirPort Security-Staff
    @Test
    public void givenParkingLot_WhenLotIsFull_ShouldInfromAirPortSecurityStaff() {
        AirPortSecurityStaff airPortSecurity = new AirPortSecurityStaff();
        parkingLot.registeredParkingLotObserver(airPortSecurity);
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL);
        } catch (ParkingLotException e) {
            boolean isCapacityFull = airPortSecurity.isCapacityFull();
            Assert.assertTrue(isCapacityFull);
        }
    }

    @Test
    public void givenParkingLot_WhenSpaceAvailable_ShouldReturnFalse() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registeredParkingLotObserver(owner);
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
        } catch (ParkingLotException e) {
        }
        parkingLot.unParkTheVehicle(vehicle2);
        boolean spaceAvailable = owner.isCapacityFull();
        Assert.assertFalse(spaceAvailable);
    }

    //UC6
    @Test
    public void givenParkingLot_WhenParkingAttendantToParkACar_ShouldOwnerDecideWitchSlotParkTheCar() {
        try {
            parkingLot.setCapacity(3);
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingSlot.setVehicleParkingSlot(vehicle);
            int locationParkedVehicle = parkingLot.autoParkingLocation;
            Assert.assertEquals(2, locationParkedVehicle);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenParkingAttendantTheParkingLotIsFull_ShouldThrowException() {
        try {
            parkingSlot.setVehicleParkingSlot(vehicle);
            parkingSlot.setVehicleParkingSlot(vehicle2);
            boolean slotAvailable = parkingSlot.isSlotAvailable();
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.SLOT_IS_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenParkingAttendantTheParkingLotSlotIsAvailable_ShouldReturnTrue() {
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingSlot.setVehicleParkingSlot(vehicle);
            boolean slotAvailable = parkingSlot.isSlotAvailable();
            Assert.assertTrue(slotAvailable);
        } catch (ParkingLotException e) {
        }
    }

    //UC7
    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldBeAbleToFindTheVehicleAtThatSlot() {
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingSlot.setVehicleParkingSlot(vehicle);
            int findVehicle = parkingLot.findVehicle(vehicle);
            Assert.assertEquals(1, findVehicle);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParkedAndTriedToFindVehicle_ShouldThrowException() {
        try {
            parkingLot.findVehicle(ParkingLotTest.vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    //UC8

    @Test
    public void givenParkingLot_WhenVehicleParked_ShouldSetParkingTime() {
        LocalDateTime expectedParkingTime = LocalDateTime.now();
        Object vehicle = new Object();
        parkingSlot.setVehicleAndTime(vehicle);
        Assert.assertEquals(expectedParkingTime, parkingSlot.getParkingTime());
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParkedInParkingLot_ShouldReturnThrowException() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registeredParkingLotObserver(owner);
        try {
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL);
            parkingLot.findVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    //UC9

    @Test
    public void givenParkingLot_WhenMultipleVehiclesParked_ShouldParkEvenly() {
        parkingLot.setCapacity(5);
        Lots lots = new Lots();
        lots.addLot(parkingLot);

        parkingLot1.setCapacity(2);
        lots.addLot(parkingLot1);
        try {
            boolean parkedVehicle1 = parkingLot.parkTheVehicle(ParkingLotTest.vehicle, DriverType.NORMAL);
            boolean parkedVehicle2 = parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
            boolean parkedVehicle3 = parkingLot1.parkTheVehicle(vehicle3, DriverType.NORMAL);
            boolean parkedVehicle4 = parkingLot1.parkTheVehicle(vehicle4, DriverType.NORMAL);
            Assert.assertTrue(parkedVehicle1 && parkedVehicle2 && parkedVehicle3 && parkedVehicle4);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenAnyLotIsFull_ShouldReturnTrue() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registeredParkingLotObserver(owner);

        parkingLot.setCapacity(5);
        Lots lots = new Lots();
        lots.addLot(parkingLot);

        parkingLot1.setCapacity(2);
        parkingLot1.registeredParkingLotObserver(owner);
        lots.addLot(parkingLot1);

        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLot1.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLot1.parkTheVehicle(vehicle3, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle4, DriverType.NORMAL);
            parkingLot.parkTheVehicle(new Object(), DriverType.NORMAL);
            parkingLot1.parkTheVehicle(vehicle5, DriverType.NORMAL);
        } catch (ParkingLotException e) {
            boolean capacityFull = owner.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void givenParkingLot_WhenVehiclesParkedEvenlyIfVehicleUnparkedAndAnotherVehicleParked_ShouldReturnParkedVehicleSlotNumber() {
        parkingLot.setCapacity(5);
        Lots lots = new Lots();
        lots.addLot(parkingLot);

        parkingLot1.setCapacity(2);
        lots.addLot(parkingLot1);
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLot1.parkTheVehicle(vehicle3, DriverType.NORMAL);
            parkingLot1.parkTheVehicle(vehicle4, DriverType.NORMAL);
            parkingLot.unParkTheVehicle(vehicle);
            parkingLot.parkTheVehicle(vehicle5, DriverType.NORMAL);
            parkingSlot.setVehicleParkingSlot(vehicle5);
            int locationParkedVehicle = parkingLot.autoParkingLocation;
            Assert.assertEquals(4, locationParkedVehicle);
        } catch (ParkingLotException e) {
        }
    }

    //UC10
    @Test
    public void givenVehicleWithHandicappedDriver_WhenParkTheVehicle_ShouldParkNearestLot() {
        parkingLot.setCapacity(5);
        try {
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle, DriverType.HANDICAP);
            parkingLot.unParkTheVehicle(vehicle2);
            parkingLot.parkTheVehicle(vehicle4, DriverType.HANDICAP);
            int findVehiclePosition = parkingLot.findVehicle(vehicle4);
            Assert.assertEquals(1, findVehiclePosition);
        } catch (ParkingLotException e) {
        }
    }
}
