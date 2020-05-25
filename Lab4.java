package com.company;
import java.util.ArrayList;
import java.util.List;

public class Lab4 {

    public static void main(String[] args) {

    }
}

class Man {
    Man(){}
    Man(String name_, String lastName_) {
        name = name_;
        lastName = lastName_;
    }
    private String name;
    private String lastName;
}

class Fireman extends Man {
    Fireman(){super();}
    Fireman(String name_, String lastName_){super(name_, lastName_);}
}

class Policeman extends Man {
    Policeman(){super();}
    Policeman(String name_, String lastName_){super(name_, lastName_);}
}

abstract class Vehicle<T extends Man> {
    Vehicle(int seatsNum) {
        seatsNumber = seatsNum;
        passengers = new ArrayList<T>(seatsNumber);
    }
    private int occupiedSeatsNum;
    private int seatsNumber;
    ArrayList<T> passengers;

    int getMaxSeatsNum (){return seatsNumber;}
    int getOccupiedSeatsNum (){return occupiedSeatsNum;}

    boolean addPassenger(T pass) {
        try {
            if (passengers.contains(pass))
                throw new ManIsAlreadyInTheVehicle();
            else if (occupiedSeatsNum < seatsNumber) {
                passengers.add(pass);
                occupiedSeatsNum++;
                return true;
            }
            else {
                throw new AllSeatsOccupied();
            }
        }
        catch (AllSeatsOccupied ex) {
            ex.Print();
        }
        catch (ManIsAlreadyInTheVehicle ex) {
            ex.Print();
        }
        return false;
    }

    boolean removePassenger(T pass) {
        try {
            if (passengers.contains(pass)){
                passengers.remove(pass);
                occupiedSeatsNum--;
                return true;
            }
            else {
                throw new ThisManIsNotInTheVehicle();
            }
        }
        catch (ThisManIsNotInTheVehicle ex){
            ex.Print();
        }
        return false;
    }


}

class Car<T extends Man> extends Vehicle<T> {
    Car(int seatsNum) {super(seatsNum);}
}

class Bus<T extends Man> extends Vehicle<T> {
    Bus(int seatsNum) {super(seatsNum);}

}

class Taxi<T extends Man> extends Car<T> {
    Taxi(int seatsNum) {super(seatsNum);}
}

class PoliceCar<T extends Policeman> extends Car<T> {
    PoliceCar(int seatsNum) {super(seatsNum);}

}

class FirefightersCar<T extends Fireman> extends Car<T> {
    FirefightersCar(int seatsNum) {super(seatsNum);}
}

class Road {
    ArrayList<Vehicle> vehicles = new ArrayList<>();

    void addVehicle(Vehicle vehicle) {
        if (vehicle != null)
            vehicles.add(vehicle);
        else {System.out.println("!!!");}
    }

    int getPassengersNumber() {
        int count = 0;
        for (Vehicle v: vehicles) {
            count += v.getOccupiedSeatsNum();
        }
        return count;
    }
}

class AllSeatsOccupied extends Exception {
    AllSeatsOccupied() { super(); }
    AllSeatsOccupied(String s) { super(s); }
    AllSeatsOccupied(String s, Throwable ex) { super(s, ex); }
    AllSeatsOccupied(Throwable ex) { super(ex); }
    void Print() {
        System.out.println("Все места заняты");
    }
}

class ManIsAlreadyInTheVehicle extends Exception {
    ManIsAlreadyInTheVehicle() { super(); }
    ManIsAlreadyInTheVehicle(String s) { super(s); }
    ManIsAlreadyInTheVehicle(String s, Throwable ex) { super(s, ex); }
    ManIsAlreadyInTheVehicle(Throwable ex) { super(ex); }
    void Print() {
        System.out.println("Этот человек уже находится в транспортном средстве");
    }
}

class ThisManIsNotInTheVehicle extends Exception {
    ThisManIsNotInTheVehicle() { super(); }
    ThisManIsNotInTheVehicle(String s) { super(s); }
    ThisManIsNotInTheVehicle(String s, Throwable ex) { super(s, ex); }
    ThisManIsNotInTheVehicle(Throwable ex) { super(ex); }
    void Print() {
        System.out.println("Этого человек нет в транспортном средстве");
    }
}