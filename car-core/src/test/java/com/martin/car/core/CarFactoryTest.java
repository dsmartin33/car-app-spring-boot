package com.martin.car.core;

import com.martin.car.model.*;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static com.martin.car.core.TestCars.*;
import static org.assertj.core.api.Assertions.assertThat;

class CarFactoryTest {
    private final CarFactory factory = new CarFactory();
    private final CarValidatorImpl validator = new CarValidatorImpl();

    @Test
    void createSedan_buildsSedanFromTypedAnswers() {
        Sedan sedan = factory.createSedan(scanner(
                "john smith",         // owner's name
                "123 main st",        // owner's address
                "2020",               // year
                "toyota",             // make
                "camry",              // model
                "1hgcm82633a004352",  // VIN
                "midsize",            // car size
                "automatic",          // transmission
                "yes",                // luxury package
                "15.1"                // trunk size
        ), validator, new LinkedList<>());

        assertThat(sedan.toString()).contains(
                "Owner's Name: John Smith",
                "Owner's Address: 123 Main St",
                "Year: 2020",
                "Make: Toyota",
                "Model: Camry",
                "Car Size: MidSize",
                "Transmission Type: Automatic",
                "Luxury Trim: Yes",
                "Trunk Size: 15.1 cu ft");
    }

    @Test
    void createTruck_buildsTruckFromTypedAnswers() {
        Truck truck = factory.createTruck(scanner(
                "ann lee",            // owner's name
                "2 oak ave",          // owner's address
                "2021",               // year
                "ford",               // make
                "f-150",              // model
                "jh4ka8260mc000000",  // VIN
                "crew",               // cab type
                "no",                 // diesel engine
                "4wd",                // drive type
                "9000"                // towing capacity
        ), validator, new LinkedList<>());

        assertThat(truck.toString()).contains(
                "Owner's Name: Ann Lee",
                "Owner's Address: 2 Oak Ave",
                "Year: 2021",
                "Make: Ford",
                "Model: F-150",
                "Cab Type: Crew",
                "Diesel Engine: No",
                "Drive Type: 4WD",
                "Towing Capacity: 9000 lbs");
    }

    @Test
    void createSedan_rejectsVinThatAlreadyExistsAndAsksAgain() {
        Sedan existingSedan = newSedan();
        List<Car> cars = new LinkedList<>(List.of(existingSedan));

        Sedan newSedan = factory.createSedan(scanner(
                "a", "b", "2020", "x", "y",
                SEDAN_VIN,            // duplicate, will be rejected
                OTHER_VIN,            // valid vin
                "compact", "manual", "no", "10"
        ), validator, cars);

        assertThat(newSedan.getVin()).isEqualTo(OTHER_VIN);
    }

    @Test
    void createSedan_asksAgainWhenYearIsNotANumber() {
        Sedan sedan = factory.createSedan(scanner(
                "a", "b",
                "abc",          // string is invalid for a year, will be rejected
                "2020",        // valid year
                "x", "y", SEDAN_VIN, "compact", "manual", "no", "10"
        ), validator, new LinkedList<>());

        assertThat(sedan.toString()).contains("Year: 2020");
    }
}
