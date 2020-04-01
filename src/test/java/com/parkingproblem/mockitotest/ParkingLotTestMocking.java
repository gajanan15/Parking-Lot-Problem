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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ParkingLotTestMocking {
    @Mock
    ParkingLot parkingLot;
    ParkingLotOwner parkingLotOwner;
    AirPortSecurityStaff airPortSecurity;
    Vehicle vehicle;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void setUp() throws Exception {
        parkingLot = mock(ParkingLot.class);
        parkingLotOwner = new ParkingLotOwner();
        airPortSecurity = new AirPortSecurityStaff();
        vehicle = new Vehicle("BMW","White","MH-04-GV-7397","Alex");
    }

    //Test Owner

    @Test
    public void givenParkingLot_WhenVehicleIsParkedAndCheckCapacityFullFunction_ShouldReturnTrue() {
        doAnswer( invocationOnMock -> {
            parkingLotOwner.lotCapacityIsFull();
            return null;
        }).when(parkingLot).parkTheVehicle(vehicle, ParkingType.NORMAL);
        parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
        Assert.assertTrue(parkingLotOwner.isCapacityFull());
    }

    @Test
    public void givenParkingLot_WhenVehicleParkedAndCheckSpaceAvailable_ShouldReturnFalse() {
        doAnswer( invocationOnMock -> {
            parkingLotOwner.lotSpaceAvailable();
            return null;
        }).when(parkingLot).parkTheVehicle(vehicle, ParkingType.NORMAL);
        parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
        Assert.assertFalse(parkingLotOwner.isSpaceAvailable());
    }

    @Test
    public void givenParkingLot_WhenVehicleParked_ShouldReturnTrue() {
        when(parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL)).thenReturn(true);
        boolean isParked = parkingLot.parkTheVehicle(this.vehicle, ParkingType.NORMAL);
        Assert.assertTrue(isParked);
    }

    @Test
    public void givenParkingLot_WhenVehicleIsUnparked_ShouldReturnTrue() {
        when(parkingLot.unParkTheVehicle(vehicle)).thenReturn(true);
        boolean isUnparked = parkingLot.unParkTheVehicle(this.vehicle);
        Assert.assertTrue(isUnparked);
    }

    //Test AirPortSecurity

    @Test
    public void givenParkingLot_TestCapacityFull_And_IsCapacityFullFunction() {
        doAnswer( invocationOnMock -> {
            airPortSecurity.lotCapacityIsFull();
            return null;
        }).when(parkingLot).parkTheVehicle(vehicle, ParkingType.NORMAL);
        parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
        Assert.assertTrue(airPortSecurity.isCapacityFull());
    }

    @Test
    public void givenParkingLot_TestCapacityAvailable_And_IsCapacityAvailable() {
        doAnswer( invocationOnMock -> {
            airPortSecurity.lotSpaceAvailable();
            return null;
        }).when(parkingLot).parkTheVehicle(vehicle, ParkingType.NORMAL);
        parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
        Assert.assertTrue(airPortSecurity.isSpaceAvailable());
    }

    //Test Exception

    @Test(expected = ParkingLotException.class)
    public void testParkingLotExceptionClass_ThrowParkingLotException_WhenCallingParkFunction() {
        doThrow(ParkingLotException.class)
                .when(parkingLot).parkTheVehicle(any(), any(ParkingType.class));
        parkingLot.parkTheVehicle(vehicle, ParkingType.NORMAL);
    }
}
