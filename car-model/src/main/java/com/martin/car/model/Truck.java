package com.martin.car.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "trucks")
public final class Truck extends Car {

    @Enumerated(EnumType.STRING)
    private CabType cabType;

    private String dieselEngine;

    @Enumerated(EnumType.STRING)
    private DriveType driveType;

    private short towingCapacity;

    public Truck() {
    }

    public Truck(CarOwner owner, short year, String make, String model, String vin, CabType cabType, String dieselEngine,
                 DriveType driveType, short towingCapacity) {
        super(owner, year, make, model, vin);
        this.cabType = cabType;
        this.dieselEngine = dieselEngine;
        this.driveType = driveType;
        this.towingCapacity = towingCapacity;
    }

    @Override
    public void updateCar(Scanner scanner, CarValidator validator, List<Car> cars) {
        boolean looping = true;
        while (looping) {
            System.out.println("""
                *** Truck Update Menu ***
                
                1. Update owner's name
                2. Update owner's address
                3. Update year
                4. Update make
                5. Update model
                6. Update VIN
                7. Update cab type
                8. Update diesel engine
                9. Update drive type
                10. Update towing capacity
                11. Exit
                """);

            byte option = validator.menuMethod(scanner);

            switch(option) {
                case 1 -> updateOwnersName(scanner, validator);
                case 2 -> updateOwnersAddress(scanner, validator);
                case 3 -> updateYear(scanner, validator);
                case 4 -> updateMake(scanner, validator);
                case 5 -> updateModel(scanner, validator);
                case 6 -> updateVin(scanner, validator, cars);
                case 7 -> updateCabType(scanner, validator);
                case 8 -> updateDieselEngine(scanner, validator);
                case 9 -> updateDriveType(scanner, validator);
                case 10 -> updateTowingCapacity(scanner, validator);
                case 11 -> looping = false;
            }
        }

        System.out.println("\nReturning to main menu...\n");
    }

    public void updateCabType(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the truck's cab type (Regular/Extended/Crew): ";
        this.cabType = validator.checkCabType(scanner, prompt);

        System.out.println("The truck's cab type has been updated successfully.\n");
    }

    public void updateDieselEngine(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the truck's diesel engine (Yes/No): ";
        this.dieselEngine = validator.getYesNo(scanner, prompt);

        System.out.println("The truck's diesel engine has been updated successfully.\n");
    }

    public void updateDriveType(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the truck's drive type (FWD/RWD/AWD/4WD): ";
        this.driveType = validator.checkDriveType(scanner, prompt);

        System.out.println("The truck's drive type has been updated successfully.\n");
    }

    public void updateTowingCapacity(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the truck's towing capacity (lbs.): ";
        this.towingCapacity = validator.checkShort(scanner, prompt);

        System.out.println("The truck's towing capacity has been updated successfully.\n");
    }

    @Override
    public String toString() {
        return super.toString() + "\n\t" +
                "Cab Type: " + cabType + "\n\t" +
                "Diesel Engine: " + dieselEngine + "\n\t" +
                "Drive Type: " + driveType + "\n\t" +
                "Towing Capacity: " + towingCapacity + " lbs\n";
    }
}
