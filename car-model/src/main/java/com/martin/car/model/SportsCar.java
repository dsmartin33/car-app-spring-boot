package com.martin.car.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "sports_cars")
public final class SportsCar extends Car {

    private short horsepower;
    private double zeroToSixtyTime;
    private short topSpeed;
    private String turbocharged;

    public SportsCar() {
    }

    public SportsCar(CarOwner owner, short year, String make, String model, String vin,  short horsepower,
                     double zeroToSixtyTime, short topSpeed, String turbocharged) {
        super(owner,year, make, model, vin);
        this.horsepower = horsepower;
        this.zeroToSixtyTime = zeroToSixtyTime;
        this.topSpeed = topSpeed;
        this.turbocharged = turbocharged;
    }

    @Override
    public void updateCar(Scanner scanner, CarValidator validator, List<Car> cars) {
        boolean looping = true;
        while (looping) {
            System.out.println("""
                *** Sports Car Update Menu ***
                
                1. Update owner's name
                2. Update owner's address
                3. Update year
                4. Update make
                5. Update model
                6. Update VIN
                7. Update horsepower
                8. Update 0-60 time
                9. Update top speed
                10. Update turbocharged option
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
                case 7 -> updateHorsepower(scanner, validator);
                case 8 -> updateZeroToSixtyTime(scanner, validator);
                case 9 -> updateTopSpeed(scanner, validator);
                case 10 -> updateTurbocharged(scanner, validator);
                case 11 -> looping = false;
            }
        }

        System.out.println("\nReturning to main menu...\n");
    }

    public void updateHorsepower(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the car's horsepower: ";
        this.horsepower = validator.checkShort(scanner, prompt);

        System.out.println("The car's horsepower has been updated successfully.\n");
    }

    public void updateZeroToSixtyTime(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the car's 0-60 time (seconds): ";
        this.zeroToSixtyTime = validator.checkDouble(scanner, prompt);

        System.out.println("The car's 0-60 time has been updated successfully.\n");
    }

    public void updateTopSpeed(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the car's top speed (MPH): ";
        this.topSpeed = validator.checkShort(scanner, prompt);

        System.out.println("The car's top speed has been updated successfully.\n");
    }

    public void updateTurbocharged(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the car's turbocharged option (Yes/No): ";
        this.turbocharged = validator.getYesNo(scanner, prompt);

        System.out.println("The car's turbocharged option has been updated successfully.\n");
    }

    @Override
    public String toString() {
        return super.toString() + "\n\t" +
                "Horsepower: " + horsepower + "\n\t" +
                "0-60 Time: " + zeroToSixtyTime + " seconds\n\t" +
                "Top Speed: " + topSpeed + " MPH\n\t" +
                "Turbocharged: " + turbocharged + "\n";
    }
}