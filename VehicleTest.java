package com.company;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {

    Man man = new Man();
    Man man2 = new Man();
    Policeman policeman = new Policeman();
    Fireman fireman = new Fireman();

    @Test
    void getOccupiedSeatsNum() {
        Vehicle<Man> actual = new Car<>(3);
        actual.addPassenger(man);
        actual.addPassenger(policeman);
        actual.addPassenger(fireman);
        actual.addPassenger(man2);
        Assert.assertEquals(3, actual.getOccupiedSeatsNum());
    }

    @Test
    void addPassenger() {
        Vehicle<Man> actual = new Car<>(3);
        actual.addPassenger(man);
        Assert.assertFalse(actual.addPassenger(man));
    }

    @Test
    void removePassenger() {
        Vehicle<Man> actual = new Car<>(3);
        actual.addPassenger(man);
        Assert.assertFalse(actual.removePassenger(policeman));
    }
}