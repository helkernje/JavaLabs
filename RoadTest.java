package com.company;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class RoadTest {

    Road actual = new Road();

    @Test
    void getPassengersNumber() {
        Car<Man> car = new Car<>(4);
        Bus<Man> bus = new Bus<>(10);
        car.addPassenger(new Man());
        car.addPassenger(new Policeman());
        bus.addPassenger(new Man());
        bus.addPassenger(new Fireman());
        bus.addPassenger(new Man());
        actual.addVehicle(car);
        actual.addVehicle(bus);
        Assert.assertEquals(5, actual.getPassengersNumber());
    }
}
