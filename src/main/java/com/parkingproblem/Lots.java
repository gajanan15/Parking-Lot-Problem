package com.parkingproblem;

import java.util.ArrayList;
import java.util.List;

public class Lots {
    private List<ParkingLot> parkingLots;

    public Lots() {
        this.parkingLots = new ArrayList<>();
    }

    public void addLot(ParkingLot parkingLot) {
        this.parkingLots.add(parkingLot);
    }
}
