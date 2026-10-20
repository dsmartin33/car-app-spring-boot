package com.martin.car.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "suvs")
public final class SUV extends Car {

    private short seatingCapacity;
    private double cargoCapacity;

    @Enumerated(EnumType.STRING)
    private DriveType driveType;

    private String offRoadPackage;

    public SUV() {
    }

    public SUV(CarOwner owner, short year, String make, String model, String vin, short seatingCapacity,
               double cargoCapacity, DriveType driveType, String offRoadPackage) {
        super(owner, year, make, model, vin);
        this.seatingCapacity = seatingCapacity;
        this.cargoCapacity = cargoCapacity;
        this.driveType = driveType;
        this.offRoadPackage = offRoadPackage;
    }

    @Override
    public void updateCar(Scanner scanner, CarValidator validator, List<Car> cars) {
        boolean looping = true;
        while (looping) {
            System.out.println("""
                *** SUV Update Menu ***
                
                1. Update owner's name
                2. Update owner's address
                3. Update year
                4. Update make
                5. Update model
                6. Update VIN
                7. Update seating capacity
                8. Update cargo capacity
                9. Update drive type
                10. Update off-road package option
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
                case 7 -> updateSeatingCapacity(scanner, validator);
                case 8 -> updateCargoCapacity(scanner, validator);
                case 9 -> updateDriveType(scanner, validator);
                case 10 -> updateOffRoadPackage(scanner, validator);
                case 11 -> looping = false;
            }
        }

        System.out.println("\nReturning to main menu...\n");
    }

    public void updateSeatingCapacity(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the SUV's seating capacity: ";
        this.seatingCapacity = validator.checkShort(scanner, prompt);

        System.out.println("The SUV's seating capacity has been updated successfully.\n");
    }

    public void updateCargoCapacity(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the SUV's cargo capacity (cu ft): ";
        this.cargoCapacity = validator.checkDouble(scanner, prompt);

        System.out.println("The SUV's cargo capacity has been updated successfully.\n");
    }

    public void updateDriveType(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the SUV's drive type (FWD/RWD/AWD/4WD): ";
        this.driveType = validator.checkDriveType(scanner, prompt);

        System.out.println("The SUV's drive type has been updated successfully.\n");
    }

    public void updateOffRoadPackage(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the SUV's off-road package option (Yes/No): ";
        this.offRoadPackage = validator.getYesNo(scanner, prompt);

        System.out.println("The SUV's off-road package option has been updated successfully.\n");
    }

    @Override
    public String toString() {
        return super.toString() + "\n\t" +
                "Seating Capacity: " + seatingCapacity + "\n\t" +
                "Cargo Capacity: " + cargoCapacity + " cu ft\n\t" +
                "Drive Type: " + driveType + "\n\t" +
                "Off-Road Package: " + offRoadPackage + " \n";
    }
}
