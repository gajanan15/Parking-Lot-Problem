package com.parkingproblem.mockitotest;

import com.parkingproblem.*;
import com.parkingproblem.enums.ParkingType;
import com.parkingproblem.exceptions.ParkingLotException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParkingLotTestMocking {
    @Mock
    ParkingLot parkingLot;
    ParkingLotsSystem parkingLotsSystem;
    ParkingLotOwner parkingLotOwner;
    AirPortSecurityStaff airPortSecurity;
    Vehicle vehicle, vehicle2;


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        parkingLot = mock(ParkingLot.class);
        parkingLotsSystem = new ParkingLotsSystem();
        parkingLotOwner = new ParkingLotOwner();
        airPortSecurity = new AirPortSecurityStaff();
        vehicle = new Vehicle("BMW", "White", "MH-04-GV-7397", "Alex", "Oakland", ParkingType.NORMAL);
        vehicle2 = new Vehicle("Toyota", "Blue", "MH-04-GV-7397", "Alex", "Oakland", ParkingType.HANDICAP);

    }

    //Test Owner

    @Test
    public void givenParkingLot_WhenVehicleIsParkedAndCheckCapacityFullFunction_ShouldReturnTrue() {
        doAnswer(invocationOnMock -> {
            parkingLotOwner.lotCapacityIsFull();
            return null;
        }).when(parkingLot).parkTheVehicle(vehicle);
        parkingLot.parkTheVehicle(vehicle);
        Assert.assertTrue(parkingLotOwner.isCapacityFull());
    }

    @Test
    public void givenParkingLot_WhenVehicleParkedAndCheckSpaceAvailable_ShouldReturnFalse() {
        doAnswer(invocationOnMock -> {
            parkingLotOwner.lotSpaceAvailable();
            return null;
        }).when(parkingLot).parkTheVehicle(vehicle);
        parkingLot.parkTheVehicle(vehicle);
        Assert.assertFalse(parkingLotOwner.isSpaceAvailable());
    }

    @Test
    public void givenParkingLot_WhenVehicleParked_ShouldReturnTrue() {
        when(parkingLot.parkTheVehicle(vehicle)).thenReturn(true);
        boolean isParked = parkingLot.parkTheVehicle(this.vehicle);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenParkingLot_WhenVehicleIsUnparked_ShouldReturnTrue() {
        when(parkingLot.unParkTheVehicle(vehicle)).thenReturn(true);
        boolean isUnparked = parkingLot.unParkTheVehicle(this.vehicle);
        Assert.assertTrue(isUnparked);
    }

    @Test
    public void givenParkingLot_WhenWhiteColorVehicle_shouldReturnListOfVehicle() {
        parkingLot.parkTheVehicle(vehicle);
        List<String> whiteCar = new ArrayList<>();
        whiteCar.add("White");
        when(parkingLot.getFieldOfVehicle("White")).thenReturn(whiteCar);
        List<String> white = parkingLot.getFieldOfVehicle("White");
        Assert.assertEquals(1, white.size());
    }


    @Test
    public void givenParkingLot_WhenParkedHandicappedVehicle_ShouldReturnVehicleLocation() {
        parkingLot.parkTheVehicle(vehicle2);
        List<String> handicapped = new ArrayList<>();
        handicapped.add("Oakland");
        when(parkingLot.detailsOfHandicappedDriver(ParkingType.HANDICAP)).thenReturn(handicapped);
        List<String> handicappedDriver = parkingLot.detailsOfHandicappedDriver(ParkingType.HANDICAP);
        Assert.assertEquals(handicapped, handicappedDriver);
    }

    @Test
    public void testAllVehicleCountFunction() {
        List<String> allVehicle = new ArrayList<>();
        when(parkingLot.getAllVehicleCount()).thenReturn(allVehicle);
        List<String> vehicleCount = parkingLot.getAllVehicleCount();
        Assert.assertEquals(0, vehicleCount.size());
    }

    // AirPort Security Staff
    @Test
    public void givenParkingLot_TestCapacityFull_And_IsCapacityFullFunction() {
        doAnswer(invocationOnMock -> {
            airPortSecurity.lotCapacityIsFull();
            return null;
        }).when(parkingLot).parkTheVehicle(vehicle);
        parkingLot.parkTheVehicle(vehicle);
        Assert.assertTrue(airPortSecurity.isCapacityFull());
    }

    @Test
    public void givenParkingLot_TestCapacityAvailable_And_IsCapacityAvailable() {
        doAnswer(invocationOnMock -> {
            airPortSecurity.lotSpaceAvailable();
            return null;
        }).when(parkingLot).parkTheVehicle(vehicle);
        parkingLot.parkTheVehicle(vehicle);
        Assert.assertTrue(airPortSecurity.isSpaceAvailable());
    }

    //Test Exception

    @Test(expected = ParkingLotException.class)
    public void testParkingLotExceptionClass_ThrowParkingLotException_WhenCallingParkFunction() {
        doThrow(ParkingLotException.class)
                .when(parkingLot).parkTheVehicle(any());
        parkingLot.parkTheVehicle(vehicle);
    }
}
