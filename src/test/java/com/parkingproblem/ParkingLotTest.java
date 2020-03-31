package com.parkingproblem;

import com.parkingproblem.enums.ParkingType;
import com.parkingproblem.exceptions.ParkingLotException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ParkingLotTest {
    private static ParkingLot parkingLot;
    private static ParkingLotsSystem parkingLotsSystem;
    private static ParkingLotOwner owner;
    private static AirPortSecurityStaff airPortSecurity;
    public static Vehicle vehicle;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot(2);
        parkingLotsSystem = new ParkingLotsSystem();
        parkingLotsSystem.addLot(parkingLot);
        owner = new ParkingLotOwner();
        parkingLotsSystem.registerParkingLots(owner);
        airPortSecurity = new AirPortSecurityStaff();
        vehicle = new Vehicle("Maruti Swift", "white");
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldReturnTrue() {
        parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
        boolean isParked = parkingLot.isVehicleParked(vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenParkingLot_WhenVehicleAlreadyParked_ShouldReturnFalse() {
        try {
            parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
            parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_IS_ALREADY_PARK, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsUnparked_ShouldReturnTrue() {
        parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
        boolean isUnparked = parkingLot.unParkTheVehicle(vehicle);
        Assert.assertTrue(isUnparked);
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParked_ShouldThrowVehicleNotFoundException() {
        try {
            parkingLot.unParkTheVehicle(vehicle);
        } catch (ParkingLotException e) {
            assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    //Parking-Lot Owner
    @Test
    public void givenParkingLot_WhenFull_ShouldInfromTheOwner() {
        Vehicle vehicle2 = new Vehicle("BMW", "white");
        Vehicle vehicle3 = new Vehicle("Mercedez", "white");
        try {
            parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
            parkingLot.parkTheVehicle(vehicle2, ParkingType.NORMAL);
            parkingLot.parkTheVehicle(vehicle3, ParkingType.NORMAL);
        } catch (ParkingLotException e) {
            boolean isCapacityFull = owner.isCapacityFull();
            Assert.assertTrue(isCapacityFull);
        }
    }

    @Test
    public void givenParkingLot_WhenVehiclesAreParkedAndRemoveOneVehicleTheRemainingSpace_ShouldAddAnotherVehicle() {
        Vehicle vehicle2 = new Vehicle("BMW", "white");
        Vehicle vehicle3 = new Vehicle("Mercedez", "white");
        try {
            parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
            parkingLot.parkTheVehicle(vehicle2, ParkingType.NORMAL);
            parkingLot.unParkTheVehicle(vehicle);
            parkingLot.parkTheVehicle(vehicle3, ParkingType.NORMAL);
            boolean isParkedVehicle = parkingLot.isVehicleParked(vehicle3);
            Assert.assertTrue(isParkedVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_IS_FULL, e.type);
        }
    }

    //AirPort Security-Staff
    @Test
    public void givenParkingLot_WhenLotIsFull_ShouldInfromAirPortSecurityStaff() {
        parkingLotsSystem.registerParkingLots(airPortSecurity);
        Vehicle vehicle2 = new Vehicle("BMW", "white");
        Vehicle vehicle3 = new Vehicle("Mercedez", "white");
        try {
            parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
            parkingLot.parkTheVehicle(vehicle2, ParkingType.NORMAL);
            parkingLot.parkTheVehicle(vehicle3, ParkingType.NORMAL);
        } catch (ParkingLotException e) {
            boolean isCapacityFull = airPortSecurity.isCapacityFull();
            Assert.assertTrue(isCapacityFull);
        }
    }

    @Test
    public void givenParkingLot_WhenSpaceAvailable_ShouldReturnFalse() {
        Vehicle vehicle2 = new Vehicle("BMW", "white");
        parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
        parkingLot.parkTheVehicle(vehicle2, ParkingType.NORMAL);
        parkingLot.unParkTheVehicle(vehicle2);
        boolean spaceAvailable = owner.isSpaceAvailable();
        Assert.assertFalse(spaceAvailable);
    }

    //UC6
    @Test
    public void givenParkingLot_WhenParkingAttendantToParkACar_ShouldOwnerDecideWitchSlotParkTheCar() {
        ParkingSlot parkingSlot = new ParkingSlot(vehicle, 2);
        try {
            parkingLot.setCapacity(3);
            parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
            parkingSlot.setVehicleParkingSlot(vehicle);
            int locationParkedVehicle = parkingLot.autoParkingLocation;
            Assert.assertEquals(2, locationParkedVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_IS_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenParkingAttendantTheParkingLotIsFull_ShouldThrowException() {
        Vehicle vehicle2 = new Vehicle("BMW", "white");
        Vehicle vehicle3 = new Vehicle("Mercedez", "white");
        try {
            parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
            parkingLot.parkTheVehicle(vehicle2, ParkingType.NORMAL);
            owner.isCapacityFull();
            parkingLot.parkTheVehicle(vehicle3, ParkingType.NORMAL);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_IS_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenParkingAttendantTheParkingLotIsAvailable_ShouldReturnFalse() {
        parkingLot.setCapacity(1);
        parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
        boolean spaceAvailable = owner.isSpaceAvailable();
        Assert.assertFalse(spaceAvailable);
    }

    //UC7
    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldBeAbleToFindTheVehicleAtThatSlot() {
        try {
            parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
            int findVehicle = parkingLot.findVehicle(vehicle);
            Assert.assertEquals(1, findVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParkedButFindAnotherVehicle_ShouldThrowException() {
        try {
            parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
            int findVehicle = parkingLot.findVehicle(new Vehicle("Nano", "white"));
            Assert.assertEquals(1, findVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
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
        ParkingSlot parkingSlot = new ParkingSlot(vehicle, 1);
        LocalDateTime expectedParkingTime = LocalDateTime.now();
        parkingSlot.setVehicleAndTime(vehicle);
        LocalDateTime vehicleParkingTime = parkingSlot.getParkingTime();
        Assert.assertEquals(expectedParkingTime, vehicleParkingTime);
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParkedInParkingLot_ShouldThrowException() {
        Vehicle vehicle2 = new Vehicle("BMW", "white");
        try {
            parkingLot.parkTheVehicle(vehicle2, ParkingType.NORMAL);
            parkingLot.findVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    //UC9

    @Test
    public void givenParkingLot_WhenMultipleVehiclesParked_ShouldParkEvenly() {
        parkingLot.setCapacity(2);
        ParkingLot parkingLot1 = new ParkingLot(3);
        parkingLot1.setCapacity(2);
        parkingLotsSystem.addLot(parkingLot1);
        Vehicle vehicle2 = new Vehicle("BMW", "white");
        Vehicle vehicle3 = new Vehicle("Mercedez", "white");
        Vehicle vehicle4 = new Vehicle("lamborgini", "white");
        try {
            boolean parkedVehicle1 = parkingLotsSystem.parkVehicle(ParkingLotTest.vehicle, ParkingType.NORMAL);
            boolean parkedVehicle2 = parkingLotsSystem.parkVehicle(vehicle2, ParkingType.NORMAL);
            boolean parkedVehicle3 = parkingLotsSystem.parkVehicle(vehicle3, ParkingType.NORMAL);
            boolean parkedVehicle4 = parkingLotsSystem.parkVehicle(vehicle4, ParkingType.NORMAL);
            Assert.assertTrue(parkedVehicle1 && parkedVehicle2 && parkedVehicle3 && parkedVehicle4);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLotCheckCapacity_WhenLotIsFull_ShouldReturnTrue() {
        parkingLot.setCapacity(3);

        ParkingLot parkingLot1 = new ParkingLot(3);
        parkingLot1.setCapacity(2);
        parkingLotsSystem.addLot(parkingLot1);
        parkingLotsSystem.registerParkingLots(owner);
        Vehicle vehicle2 = new Vehicle("BMW", "white");
        Vehicle vehicle3 = new Vehicle("Mercedez", "white");
        Vehicle vehicle4 = new Vehicle("lamborgini", "white");
        Vehicle vehicle5 = new Vehicle("I20", "white");
        try {
            parkingLotsSystem.parkVehicle(vehicle, ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle2, ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle3, ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle4, ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(new Vehicle("Audi", "Black"), ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle5, ParkingType.NORMAL);
        } catch (ParkingLotException e) {
            boolean capacityFull = owner.isCapacityFull();
            Assert.assertTrue(capacityFull);
        }
    }

    @Test
    public void givenParkingLot_WhenVehiclesParkedEvenlyIfVehicleUnparkedAndAnotherVehicleParked_ShouldReturnParkedVehicleSlotNumber() {
        parkingLot.setCapacity(5);

        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLot1.setCapacity(2);
        parkingLotsSystem.addLot(parkingLot1);
        Vehicle vehicle2 = new Vehicle("BMW", "white");
        Vehicle vehicle3 = new Vehicle("Mercedez", "white");
        Vehicle vehicle4 = new Vehicle("lamborgini", "white");
        Vehicle vehicle5 = new Vehicle("I20", "white");
        try {
            parkingLotsSystem.parkVehicle(vehicle, ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle2, ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle3, ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle4, ParkingType.NORMAL);
            parkingLotsSystem.unParkVehicle(vehicle);
            parkingLotsSystem.parkVehicle(vehicle5, ParkingType.NORMAL);
            int locationParkedVehicle = parkingLot.autoParkingLocation;
            Assert.assertEquals(4, locationParkedVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_IS_FULL, e.type);
        }
    }

    //UC10
    @Test
    public void givenParkingLotVehicleWithHandicappedDriver_WhenParkTheVehicle_ShouldParkNearestLot() {
        parkingLot.setCapacity(5);
        Vehicle vehicle2 = new Vehicle("BMW", "white");
        Vehicle vehicle3 = new Vehicle("Mercedez", "white");
        Vehicle vehicle4 = new Vehicle("lamborgini", "white");
        try {
            parkingLotsSystem.parkVehicle(vehicle2, ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle3, ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle, ParkingType.HANDICAP);
            parkingLotsSystem.unParkVehicle(vehicle2);
            parkingLotsSystem.parkVehicle(vehicle4, ParkingType.HANDICAP);
            int findVehiclePosition = parkingLotsSystem.findVehicle(vehicle4);
            Assert.assertEquals(1, findVehiclePosition);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLotVehicleWithHandicappedDriver_WhenOneVehicleUnparked_ShouldParkedAnotherHandicappedDriverVehicle() {
        parkingLot.setCapacity(5);
        Vehicle vehicle2 = new Vehicle("BMW", "white");
        Vehicle vehicle3 = new Vehicle("Mercedez", "white");
        Vehicle vehicle4 = new Vehicle("lamborgini", "white");
        try {
            parkingLotsSystem.parkVehicle(vehicle, ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle2, ParkingType.HANDICAP);
            parkingLotsSystem.parkVehicle(vehicle3, ParkingType.NORMAL);
            parkingLotsSystem.unParkVehicle(vehicle2);
            parkingLotsSystem.parkVehicle(vehicle4, ParkingType.HANDICAP);
            int findVehiclePosition = parkingLotsSystem.findVehicle(vehicle4);
            Assert.assertEquals(0, findVehiclePosition);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    //UC11

    @Test
    public void givenParkingLot_WhenLargeVehicleParked_ShouldReturnTrue() {
        parkingLot.setCapacity(10);

        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLot1.setCapacity(10);
        parkingLotsSystem.addLot(parkingLot1);

        ParkingLot parkingLot2 = new ParkingLot(2);
        parkingLot2.setCapacity(10);
        parkingLotsSystem.addLot(parkingLot2);

        Vehicle vehicle2 = new Vehicle("BMW", "white");
        Vehicle vehicle3 = new Vehicle("Mercedez", "white");
        Vehicle vehicle4 = new Vehicle("lamborgini", "white");
        Vehicle vehicle5 = new Vehicle("I20", "white");
        Vehicle vehicle6 = new Vehicle("Wagnor", "black");
        try {
            parkingLotsSystem.parkVehicle(vehicle, ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle2, ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle3, ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle4, ParkingType.HANDICAP);
            parkingLotsSystem.parkVehicle(vehicle5, ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(new Vehicle("Nano", "White"), ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(new Vehicle("Audi", "white"), ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle6, ParkingType.LARGE);
            boolean isVehicleParked = parkingLotsSystem.isVehicleParked(vehicle6);
            Assert.assertTrue(isVehicleParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    //UC12

    @Test
    public void givenParkingLot_WhenNoWhiteVehicle_ShouldReturnListOfLocationsWhiteVehicle() {
        parkingLot.setCapacity(3);
        Vehicle vehicle2 = new Vehicle("BMW", "Black");
        Vehicle vehicle3 = new Vehicle("Mercedez", "Red");
        Vehicle vehicle4 = new Vehicle("Jaguar", "Black");
        try {
            parkingLotsSystem.parkVehicle(vehicle2, ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle3, ParkingType.NORMAL);
            parkingLotsSystem.parkVehicle(vehicle4, ParkingType.NORMAL);
            ArrayList<Integer> carSlotList = parkingLot.getSlotOfWhiteColorVehicle("white");
            Assert.assertEquals(0, carSlotList.size());
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_VEHICLE_IN_LOT, e.type);
        }
    }
}
