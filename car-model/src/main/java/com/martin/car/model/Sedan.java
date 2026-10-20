package com.martin.car.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "sedans")
public final class Sedan extends Car {

    @Enumerated(EnumType.STRING)
    private CarSize carSize;

    @Enumerated(EnumType.STRING)
    private TransmissionType transmissionType;

    private String luxuryTrim;
    private double trunkSize;

    public Sedan() {
    }

    public Sedan(CarOwner owner, short year, String make, String model, String vin, CarSize carSize,
                 TransmissionType transmissionType, String luxuryTrim, double trunkSize) {
        super(owner, year, make, model, vin);
        this.carSize = carSize;
        this.transmissionType = transmissionType;
        this.luxuryTrim = luxuryTrim;
        this.trunkSize = trunkSize;
    }

    @Override
    public void updateCar(Scanner scanner, CarValidator validator, List<Car> cars) {
        boolean looping = true;
        while (looping) {
            System.out.println("""
                *** Sedan Update Menu ***
                
                1. Update owner's name
                2. Update owner's address
                3. Update year
                4. Update make
                5. Update model
                6. Update VIN
                7. Update car size
                8. Update transmission type
                9. Update luxury trim
                10. Update trunk size
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
                case 7 -> updateCarSize(scanner, validator);
                case 8 -> updateTransmissionType(scanner, validator);
                case 9 -> updateLuxuryTrim(scanner, validator);
                case 10 -> updateTrunkSize(scanner, validator);
                case 11 -> looping = false;
            }
        }

        System.out.println("\nReturning to main menu...\n");
    }

    public void updateCarSize(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the car's size (Compact/MidSize/FullSize): ";
        this.carSize = validator.checkCarSize(scanner, prompt);

        System.out.println("The car's size has been updated successfully.\n");
    }

    public void updateTransmissionType(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the car's transmission type (Automatic/Manual/CVT/DCT): ";
        this.transmissionType = validator.checkTransmissionType(scanner, prompt);

        System.out.println("The car's transmission type has been updated successfully.\n");
    }

    public void updateLuxuryTrim(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the car's luxury package option (Yes/No): ";
        this.luxuryTrim = validator.getYesNo(scanner, prompt);

        System.out.println("The car's luxury trim option has been updated successfully.\n");
    }

    public void updateTrunkSize(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the car's trunk size (cubic feet): ";
        this.trunkSize = validator.checkDouble(scanner, prompt);

        System.out.println("The car's trunk size has been updated successfully.\n");
    }

    @Override
    public String toString() {
        return super.toString() + "\n\t" +
                "Car Size: " + carSize + "\n\t" +
                "Transmission Type: " + transmissionType + "\n\t" +
                "Luxury Trim: " + luxuryTrim + "\n\t" +
                "Trunk Size: " + trunkSize + " cu ft\n";
    }
}