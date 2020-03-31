package com.parkingproblem;

import java.util.ArrayList;
import java.util.List;

public class Informer {
    static List<ParkingLotObserver> observerList;

    public Informer() {
        observerList = new ArrayList<>();
    }

    public void notifyParkingIsFull() {
        for (ParkingLotObserver observer : observerList) {
            observer.lotCapacityIsFull();
        }
    }

    public void notifyParkingIsAvailable() {
        for (ParkingLotObserver observers : observerList)
            observers.lotSpaceAvailable();
    }

    public void registerParkingLots(ParkingLotObserver observer) {
        observerList.add(observer);
    }
}
