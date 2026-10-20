package com.martin.car.core;

import com.martin.car.model.*;

import java.util.Scanner;

/**
 * Small helpers shared by the car-core tests.
 */
final class TestCars {
    static final String SEDAN_VIN = "1HGCM82633A004352";
    static final String TRUCK_VIN = "1FTFW1ET5DFC10312";
    static final String OTHER_VIN = "JH4KA8260MC000000";

    private TestCars() {
    }

    static Sedan newSedan(String vin) {
        return new Sedan(new CarOwner("John Smith", "123 Main St"), (short) 2020, "Toyota", "Camry", vin,
                CarSize.MidSize, TransmissionType.Automatic, "No", 15.1);
    }

    static Truck newTruck(String vin) {
        return new Truck(new CarOwner("Ann Lee", "2 Oak Ave"), (short) 2021, "Ford", "F-150", vin,
                CabType.Crew, "No", DriveType.FOUR_WD, (short) 9000);
    }

    /**
     * Builds a Scanner that "types" each line, so a test can script what the user would enter.
     */
    static Scanner scanner(String... lines) {
        return new Scanner(String.join("\n", lines) + "\n");
    }
}
