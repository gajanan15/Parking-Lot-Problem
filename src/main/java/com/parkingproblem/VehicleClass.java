package com.parkingproblem;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class VehicleClass {

    public int amount = 100;

    public boolean timeCheck(Object vehicle) {
        if (vehicle != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            String time = formatter.format(date);
            return true;
        }
        throw new ParkingLotException("No Such Vehicle Found", ParkingLotException.ExceptionType.VEHICLE_NOT_FOUND);
    }

    public int payPerUser() {
        int pay = amount;
//        System.out.println(pay);
        return pay;
    }
}
