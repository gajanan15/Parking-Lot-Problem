package com.parkingproblem;

import com.parkingproblem.enums.ParkingType;
import com.parkingproblem.exceptions.ParkingLotException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParkingLotTest {
    private static ParkingLot parkingLot;
    private static ParkingLotsSystem parkingLotsSystem;
    private static ParkingLotOwner owner;
    private static AirPortSecurityStaff airPortSecurity;
    private static Vehicle vehicle, vehicle2, vehicle3, vehicle4, vehicle5;

    @Before
    public void setUp() throws Exception {
        parkingLot = new ParkingLot(2);
        parkingLotsSystem = new ParkingLotsSystem();
        parkingLotsSystem.addLot(parkingLot);
        owner = new ParkingLotOwner();
        parkingLotsSystem.registerParkingLots(owner);
        airPortSecurity = new AirPortSecurityStaff();
        vehicle = new Vehicle("Maruti Suzuki", "white", "MH-04-Gv-7397", "Ara", "Washington", ParkingType.NORMAL);
        vehicle2 = new Vehicle("BMW", "white", "MH-25-HG-2547", "Armando", "Kingston", ParkingType.NORMAL);
        vehicle3 = new Vehicle("Mercedes", "white", "GU-26-JH-2547", "Baptiste", "Georgetown", ParkingType.NORMAL);
        vehicle4 = new Vehicle("Toyota", "Blue", "MH-46-JT-1254", "Alex", "Kingston", ParkingType.HANDICAP);
        vehicle5 = new Vehicle("Toyota", "Blue", "MH-04-ZW-3658", "TOM", "Washington", ParkingType.NORMAL);
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldReturnTrue() {
        parkingLot.parkTheVehicle(vehicle);
        boolean isParked = parkingLot.isVehicleParked(vehicle);
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
        try {
            parkingLot.parkTheVehicle(vehicle);
            parkingLot.parkTheVehicle(vehicle2);
            parkingLot.parkTheVehicle(vehicle3);
        } catch (ParkingLotException e) {
            boolean isCapacityFull = owner.isCapacityFull();
            Assert.assertTrue(isCapacityFull);
        }
    }

    @Test
    public void givenParkingLot_WhenVehiclesAreParkedAndRemoveOneVehicleTheRemainingSpace_ShouldAddAnotherVehicle() {
        try {
            parkingLot.parkTheVehicle(vehicle);
            parkingLot.parkTheVehicle(vehicle2);
            parkingLot.unParkTheVehicle(vehicle);
            parkingLot.parkTheVehicle(vehicle3);
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
        try {
            parkingLot.parkTheVehicle(vehicle);
            parkingLot.parkTheVehicle(vehicle2);
            parkingLot.parkTheVehicle(vehicle3);
        } catch (ParkingLotException e) {
            boolean isCapacityFull = airPortSecurity.isCapacityFull();
            Assert.assertTrue(isCapacityFull);
        }
    }

    @Test
    public void givenParkingLot_WhenSpaceAvailable_ShouldReturnFalse() {
        parkingLot.parkTheVehicle(vehicle);
        parkingLot.parkTheVehicle(vehicle2);
        parkingLot.unParkTheVehicle(vehicle2);
        boolean spaceAvailable = owner.isSpaceAvailable();
        Assert.assertFalse(spaceAvailable);
    }

    //UC6
    @Test
    public void givenParkingLot_WhenParkingAttendantToParkACar_ShouldOwnerDecideWitchSlotParkTheCar() {
        ParkingSlot parkingSlot = new ParkingSlot(vehicle);
        try {
            parkingLot.setCapacity(3);
            parkingLot.parkTheVehicle(vehicle);
            parkingSlot.setVehicleParkingSlot(vehicle);
            int locationParkedVehicle = parkingLot.parkingLocation;
            Assert.assertEquals(2, locationParkedVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_IS_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenParkingAttendantTheParkingLotIsFull_ShouldThrowException() {
        try {
            parkingLot.parkTheVehicle(vehicle);
            parkingLot.parkTheVehicle(vehicle2);
            owner.isCapacityFull();
            parkingLot.parkTheVehicle(vehicle3);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_IS_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenParkingAttendantTheParkingLotIsAvailable_ShouldReturnFalse() {
        parkingLot.setCapacity(1);
        parkingLot.parkTheVehicle(vehicle);
        boolean spaceAvailable = owner.isSpaceAvailable();
        Assert.assertFalse(spaceAvailable);
    }

    //UC7
    @Test
    public void givenParkingLot_WhenVehicleIsParked_ShouldBeAbleToFindTheVehicleAtThatSlot() {
        try {
            parkingLot.parkTheVehicle(vehicle);
            int findVehicle = parkingLot.findVehicle(vehicle);
            Assert.assertEquals(1, findVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsParkedButFindAnotherVehicle_ShouldThrowException() {
        try {
            parkingLot.parkTheVehicle(vehicle);
            int findVehicle = parkingLot.findVehicle(vehicle2);
            Assert.assertEquals(1, findVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE_IN_LOT, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParkedAndTriedToFindVehicle_ShouldThrowException() {
        try {
            parkingLot.findVehicle(ParkingLotTest.vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE_IN_LOT, e.type);
        }
    }

    //UC8

    @Test
    public void givenParkingLot_WhenVehicleParked_ShouldSetParkingTime() {
        ParkingSlot parkingSlot = new ParkingSlot();
        LocalDateTime expectedParkingTime = LocalDateTime.now();
        parkingSlot.setVehicleAndTime(vehicle);
        LocalDateTime vehicleParkingTime = parkingSlot.getParkingTime();
        Assert.assertEquals(expectedParkingTime.getMinute(), vehicleParkingTime.getMinute());
    }

    @Test
    public void givenParkingLot_WhenVehicleIsNotParkedInParkingLot_ShouldThrowException() {
        try {
            parkingLot.parkTheVehicle(vehicle2);
            parkingLot.findVehicle(vehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE_IN_LOT, e.type);
        }
    }

    //UC9

    @Test
    public void givenParkingLot_WhenMultipleVehiclesParked_ShouldParkEvenly() {
        parkingLot.setCapacity(2);
        ParkingLot parkingLot1 = new ParkingLot(3);
        parkingLot1.setCapacity(2);
        parkingLotsSystem.addLot(parkingLot1);
        try {
            boolean parkedVehicle1 = parkingLotsSystem.parkVehicle(vehicle);
            boolean parkedVehicle2 = parkingLotsSystem.parkVehicle(vehicle2);
            boolean parkedVehicle3 = parkingLotsSystem.parkVehicle(vehicle3);
            boolean parkedVehicle4 = parkingLotsSystem.parkVehicle(vehicle4);
            Assert.assertTrue(parkedVehicle1 && parkedVehicle2 && parkedVehicle3 && parkedVehicle4);
        } catch (ParkingLotException e) {
        }
    }

    @Test
    public void givenParkingLotCheckCapacity_WhenLotIsFull_ShouldReturnTrue() {
        parkingLot.setCapacity(2);

        ParkingLot parkingLot1 = new ParkingLot(3);
        parkingLot1.setCapacity(2);
        parkingLotsSystem.addLot(parkingLot1);
        parkingLotsSystem.registerParkingLots(owner);
        try {
            parkingLotsSystem.parkVehicle(vehicle);
            parkingLotsSystem.parkVehicle(vehicle2);
            parkingLotsSystem.parkVehicle(vehicle3);
            parkingLotsSystem.parkVehicle(vehicle4);
            parkingLotsSystem.parkVehicle(vehicle5);
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
        try {
            parkingLotsSystem.parkVehicle(vehicle);
            parkingLotsSystem.parkVehicle(vehicle2);
            parkingLotsSystem.parkVehicle(vehicle3);
            parkingLotsSystem.parkVehicle(vehicle4);
            parkingLotsSystem.isVehicleUnparked(vehicle);
            parkingLotsSystem.parkVehicle(vehicle5);
            int locationParkedVehicle = parkingLot.parkingLocation;
            Assert.assertEquals(4, locationParkedVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_IS_FULL, e.type);
        }
    }

    //UC10
    @Test
    public void givenParkingLotVehicleWithHandicappedDriver_WhenParkTheVehicle_ShouldParkNearestLot() {
        parkingLot.setCapacity(5);
        Vehicle vehicle6 = new Vehicle("Mahindra Scorpio", "black", "LK-25-KY-4578", "Devin", "Georgetown", ParkingType.HANDICAP);
        try {
            parkingLotsSystem.parkVehicle(vehicle2);
            parkingLotsSystem.parkVehicle(vehicle3);
            parkingLotsSystem.parkVehicle(vehicle6);
            parkingLotsSystem.isVehicleUnparked(vehicle2);
            parkingLotsSystem.parkVehicle(vehicle4);
            int findVehiclePosition = parkingLotsSystem.findVehicle(vehicle4);
            Assert.assertEquals(1, findVehiclePosition);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLotVehicleWithHandicappedDriver_WhenOneVehicleUnparked_ShouldParkedAnotherHandicappedDriverVehicle() {
        parkingLot.setCapacity(5);
        try {
            parkingLotsSystem.parkVehicle(vehicle);
            parkingLotsSystem.parkVehicle(vehicle2);
            parkingLotsSystem.parkVehicle(vehicle3);
            parkingLotsSystem.isVehicleUnparked(vehicle2);
            parkingLotsSystem.parkVehicle(vehicle4);
            int findVehiclePosition = parkingLotsSystem.findVehicle(vehicle4);
            Assert.assertEquals(0, findVehiclePosition);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    //UC11

    @Test
    public void givenParkingLot_WhenLargeVehicleParked_ShouldReturnTrue() {
        parkingLot.setCapacity(5);

        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLot1.setCapacity(8);
        parkingLotsSystem.addLot(parkingLot1);

        ParkingLot parkingLot2 = new ParkingLot(2);
        parkingLot2.setCapacity(2);
        parkingLotsSystem.addLot(parkingLot2);

        parkingLotsSystem.registerParkingLots(owner);

        Vehicle vehicle6 = new Vehicle("Mahindra Scorpio", "black", "LK-25-KY-4578", "Devin", "Georgetown", ParkingType.NORMAL);
        Vehicle vehicle7 = new Vehicle("Nano", "White", "GA-12-PJ-2547", "Hugo", "Bristol", ParkingType.HANDICAP);
        Vehicle vehicle8 = new Vehicle("Refrigerator truck", "white", "GA-58-JU-6587", "Steffen", "Chester", ParkingType.LARGE);
        Vehicle vehicle9 = new Vehicle("Heavy hauler", "GRY", "Gk-88-JU-6567", "Adriano", "Salem", ParkingType.LARGE);
        Vehicle vehicle10 = new Vehicle("Bugatti Veyron", "Red", "GI-58-JU-6536", "Alexei", "Oakland", ParkingType.HANDICAP);
        Vehicle vehicle11 = new Vehicle("Tautliner", "White", "GI-58-JU-6536", "Alvaro", "Georgetown", ParkingType.LARGE);
        try {
            parkingLotsSystem.parkVehicle(vehicle);
            parkingLotsSystem.parkVehicle(vehicle2);
            parkingLotsSystem.parkVehicle(vehicle3);
            parkingLotsSystem.parkVehicle(vehicle4);
            parkingLotsSystem.parkVehicle(vehicle5);
            parkingLotsSystem.parkVehicle(vehicle6);
            parkingLotsSystem.parkVehicle(vehicle7);
            parkingLotsSystem.parkVehicle(vehicle8);
            parkingLotsSystem.parkVehicle(vehicle9);
            parkingLotsSystem.parkVehicle(vehicle10);
            parkingLotsSystem.parkVehicle(vehicle11);
            boolean isVehicleParked = parkingLotsSystem.isVehicleParked(vehicle8);
            Assert.assertTrue(isVehicleParked);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenLargeVehicleNotPresent_ShouldNormalParkingAndReturnTrue() {
        parkingLot.setCapacity(2);

        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLot1.setCapacity(2);
        parkingLotsSystem.addLot(parkingLot1);
        try {
            boolean parkedVehicle1 = parkingLotsSystem.parkVehicle(vehicle);
            boolean parkedVehicle2 = parkingLotsSystem.parkVehicle(vehicle2);
            boolean parkedVehicle3 = parkingLotsSystem.parkVehicle(vehicle3);
            boolean parkedVehicle4 = parkingLotsSystem.parkVehicle(vehicle4);
            Assert.assertTrue(parkedVehicle1 && parkedVehicle2 && parkedVehicle3 && parkedVehicle4);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.PARKING_IS_FULL, e.type);
        }
    }

    @Test
    public void givenParkingLot_WhenLargeVehicleNotFoundInLot_ShouldThrowException() {
        parkingLot.setCapacity(2);

        ParkingLot parkingLot1 = new ParkingLot(2);
        parkingLot1.setCapacity(2);
        parkingLotsSystem.addLot(parkingLot1);
        try {
            parkingLotsSystem.parkVehicle(vehicle);
            parkingLotsSystem.parkVehicle(vehicle2);
            parkingLotsSystem.parkVehicle(vehicle3);
            parkingLotsSystem.parkVehicle(vehicle4);
            int findVehicle = parkingLot.findVehicle(vehicle2);
            Assert.assertEquals(1, findVehicle);
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.NO_SUCH_VEHICLE_IN_LOT, e.type);
        }
    }

    //UC12

    @Test
    public void givenParkingLot_WhenWhiteVehicle_ShouldReturnTotalWhiteVehicleInLot() {
        parkingLot.setCapacity(10);
        parkingLotsSystem.parkVehicle(vehicle);
        parkingLotsSystem.parkVehicle(vehicle2);
        parkingLotsSystem.parkVehicle(vehicle3);
        parkingLotsSystem.parkVehicle(vehicle4);

        List<String> carSlotList = parkingLot.getFieldOfVehicle("white");
        Assert.assertEquals(3, carSlotList.size());
    }

    @Test
    public void givenParkingLot_WhenNoWhiteVehicle_ShouldReturnSizeZero() {
        parkingLot.setCapacity(3);
        parkingLotsSystem.parkVehicle(vehicle4);
        parkingLotsSystem.parkVehicle(vehicle5);
        List<String> carSlotList = parkingLot.getFieldOfVehicle("white");
        Assert.assertEquals(0, carSlotList.size());
    }

    //UC13

    @Test
    public void givenParkingLot_WhenBlueAndToyotaVehiclesAreParked_ShouldReturnLocationPlateNumberAndAttendantNameOfAllBlueToyotaCars() {
        parkingLot.setCapacity(5);
        parkingLotsSystem.parkVehicle(vehicle2);
        parkingLotsSystem.parkVehicle(vehicle4);
        parkingLotsSystem.parkVehicle(vehicle5);
        List<String> listOfVehicles = parkingLot.getLocationOfBlueToyotaCar("Blue", "Toyota");
        Assert.assertEquals(2, listOfVehicles.size());
    }

    @Test
    public void givenParkingLot_WhenColorIsBlueButNotToyotaVehicle_ShouldReturnSizeZero() {
        parkingLot.setCapacity(6);
        parkingLotsSystem.registerParkingLots(owner);
        Vehicle vehicle6 = new Vehicle("Mahindra Scorpio", "Blue", "LK-25-KY-4578", "Devin", "Georgetown", ParkingType.NORMAL);
        Vehicle vehicle7 = new Vehicle("Nano", "White", "GA-12-PJ-2547", "Hugo", "Bristol", ParkingType.NORMAL);
        Vehicle vehicle8 = new Vehicle("Refrigerator truck", "Blue", "GA-58-JU-6587", "Steffen", "Chester", ParkingType.NORMAL);
        Vehicle vehicle9 = new Vehicle("Heavy hauler", "GRY", "Gk-88-JU-6567", "Adriano", "Salem", ParkingType.NORMAL);
        parkingLotsSystem.parkVehicle(vehicle6);
        parkingLotsSystem.parkVehicle(vehicle7);
        parkingLotsSystem.parkVehicle(vehicle8);
        parkingLotsSystem.parkVehicle(vehicle9);
        List<String> listOfVehicles = parkingLot.getLocationOfBlueToyotaCar("Blue", "Toyota");
        Assert.assertEquals(0, listOfVehicles.size());
    }

    //UC14

    @Test
    public void givenParkingLot_WhenFindBMWCar_ShouldReturnTotalListOfBMWCars() {
        parkingLot.setCapacity(5);
        Vehicle vehicle6 = new Vehicle("BMW", "Blue", "LK-25-KY-4578", "Devin", "Georgetown", ParkingType.NORMAL);
        parkingLotsSystem.parkVehicle(vehicle2);
        parkingLotsSystem.parkVehicle(vehicle);
        parkingLotsSystem.parkVehicle(vehicle6);
        List<String> totalBMWCars = parkingLot.getFieldOfVehicle("BMW");
        Assert.assertEquals(2, totalBMWCars.size());
    }

    @Test
    public void givenParkingLot_WhenBMWCarNotParked_ShouldReturnSizeZero() {
        parkingLot.setCapacity(4);
        parkingLotsSystem.parkVehicle(vehicle);
        parkingLotsSystem.parkVehicle(vehicle3);
        parkingLotsSystem.parkVehicle(vehicle4);
        List<String> totalBMWCars = parkingLot.getFieldOfVehicle("BMW");
        Assert.assertEquals(0, totalBMWCars.size());
    }

    //UC15

    @Test
    public void givenParkingLot_WhenHowManyVehicleAreParkedLast30Minutes_ShouldReturnTotalVehicle() {
        parkingLot.setCapacity(7);
        parkingLotsSystem.parkVehicle(vehicle);
        parkingLotsSystem.parkVehicle(vehicle2);
        parkingLotsSystem.parkVehicle(vehicle3);
        parkingLotsSystem.parkVehicle(vehicle4);
        List<String> vehicleAreParkedLast30Minutes = parkingLot.getVehicleAreParkedLast30Minutes();
        Assert.assertEquals(4, vehicleAreParkedLast30Minutes.size());
    }

    @Test
    public void givenParkingLot_WhenNOVehicleParkedInLast30Minutes_ShouldReturnSizeZero() {
        parkingLot.setCapacity(10);
        List<String> vehicleAreParkedLast30Minutes = parkingLot.getVehicleAreParkedLast30Minutes();
        Assert.assertEquals(0, vehicleAreParkedLast30Minutes.size());
    }

    //UC16

    @Test
    public void givenParkingLot_WhenParkedHandicappedDriverCars_ShouldReturnLocationAndPlateNumber() {
        parkingLot.setCapacity(8);
        Vehicle vehicle6 = new Vehicle("Mahindra Scorpio", "black", "LK-25-KY-4578", "Devin", "Georgetown", ParkingType.HANDICAP);
        parkingLotsSystem.parkVehicle(vehicle);
        parkingLotsSystem.parkVehicle(vehicle2);
        parkingLotsSystem.parkVehicle(vehicle4);
        parkingLotsSystem.parkVehicle(vehicle6);
        List<String> handicappedDriverDetails = parkingLot.detailsOfHandicappedDriver(ParkingType.HANDICAP);
        List locationAndPlateNumber = new ArrayList();
        locationAndPlateNumber.add("Kingston MH-46-JT-1254");
        locationAndPlateNumber.add("Georgetown LK-25-KY-4578");
        Assert.assertEquals(locationAndPlateNumber, handicappedDriverDetails);
    }

    //UC17

    @Test
    public void givenParkingLot_WhenVehiclesAreParked_ShouldReturnTotalVehicle() {
        parkingLot.setCapacity(8);
        parkingLotsSystem.parkVehicle(vehicle);
        parkingLotsSystem.parkVehicle(vehicle2);
        parkingLotsSystem.parkVehicle(vehicle3);
        parkingLotsSystem.parkVehicle(vehicle4);
        List<String> allVehicleCount = parkingLot.getAllVehicleCount();
        Assert.assertEquals(4, allVehicleCount.size());
    }

    @Test
    public void givenParkingLot_WhenNoVehicleAreParked_ShouldReturnZeroVehicle() {
        parkingLot.setCapacity(10);
        List<String> allVehicleCount = parkingLot.getAllVehicleCount();
        Assert.assertEquals(0, allVehicleCount.size());
    }
}
