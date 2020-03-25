package com.parkingproblem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParkingLotTest {
    private static ParkingLot parkingLot;
    private static ParkingSlot parkingSlot;
    public static Object vehicle, vehicle2, vehicle3, vehicle4, vehicle5;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot(2);
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
        }
        boolean isCapacityFull = owner.isCapacityFull();
        Assert.assertTrue(isCapacityFull);
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
        }
        boolean isCapacityFull = airPortSecurity.isCapacityFull();
        Assert.assertTrue(isCapacityFull);
    }

    @Test
    public void givenParkingLot_WhenSpaceAvailable_ShouldReturnTrue() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registeredParkingLotObserver(owner);
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL);
        } catch (ParkingLotException e) {
        }
        parkingLot.unParkTheVehicle(vehicle2);
        boolean spaceAvailable = owner.isCapacityFull();
        Assert.assertTrue(spaceAvailable);
    }

    //UC6
    @Test
    public void givenParkingLot_WhenParkingAttendantToParkACar_ShouldOwnerDecideWitchSlotParkTheCar() {
        ParkingSlot parkingSlot = new ParkingSlot(1);
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingSlot.setVehicleParkingSlot(vehicle);
            boolean vehicleParked = parkingLot.isVehicleParked(vehicle);
            Assert.assertTrue(vehicleParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenParkingAttendantTheParkingLotIsFull_ShouldThrowException() {
        ParkingSlot parkingSlot = new ParkingSlot(2);
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
            Assert.assertEquals(0, findVehicle);
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
    public void givenParkingLot_WhenVehicleIsParkedInParkingLot_ShouldLotOwnerChargeTheLotUser() {
        ParkingLotOwner owner = new ParkingLotOwner();
        parkingLot.registeredParkingLotObserver(owner);
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            boolean unParked = parkingLot.unParkTheVehicle(vehicle);
            Assert.assertTrue(unParked);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleParkedTimeIsSet_ShouldReturnParkingTime() {
        VehicleClass vehicleClass = new VehicleClass();
        boolean parkingTime = parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
        boolean checkTime = vehicleClass.timeCheck(parkingTime);
        assertEquals(parkingTime, checkTime);
    }

    @Test
    public void givenParkingLot_WhenParticularTimePeriodVehicleNotParked_ShouldThrowException() {
        VehicleClass vehicleClass = new VehicleClass();
        try {
            parkingLot.parkTheVehicle(null,DriverType.NORMAL);
            boolean time = vehicleClass.timeCheck(null);
        }catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND,e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParkedInParkingLot_ShouldReturnFalse() {
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
    public void givenParkingLot_WhenMultipleVehicles_ShouldParkEvenly() {
        parkingLot.setCapacity(5);
        try {
            parkingLot.parkTheVehicle(vehicle, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle2, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle3, DriverType.NORMAL);
            parkingLot.parkTheVehicle(vehicle4, DriverType.NORMAL);
            parkingLot.unParkTheVehicle(vehicle3);
            parkingLot.parkTheVehicle(vehicle5, DriverType.NORMAL);
            Object emptySlotPosition = parkingLot.getEmptyParkingSlot().get(0);
            Assert.assertEquals(2, emptySlotPosition);
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
            Assert.assertEquals(3, findVehiclePosition);
        } catch (ParkingLotException e) {
        }
    }
}
