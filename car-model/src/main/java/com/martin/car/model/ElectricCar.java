package com.martin.car.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "electric_cars")
public final class ElectricCar extends Car {

    private short batteryCapacity;

    @Column(name = "range_miles")
    private short range;

    private String fastCharging;
    private String pluginHybrid;

    public ElectricCar() {
    }

    public ElectricCar(CarOwner owner, short year, String make, String model, String vin, short batteryCapacity,
                       short range, String fastCharging, String pluginHybrid) {
        super(owner, year, make, model, vin);
        this.batteryCapacity = batteryCapacity;
        this.range = range;
        this.fastCharging = fastCharging;
        this.pluginHybrid = pluginHybrid;
    }

    @Override
    public void updateCar(Scanner scanner, CarValidator validator, List<Car> cars) {
        boolean looping = true;
        while (looping) {
            System.out.println("""
                *** Electric Car Update Menu ***
                
                1. Update owner's name
                2. Update owner's address
                3. Update year
                4. Update make
                5. Update model
                6. Update VIN
                7. Update battery capacity
                8. Update range
                9. Update fast charging option
                10. Update Plug-In Hybrid option
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
                case 7 -> updateBatteryCapacity(scanner, validator);
                case 8 -> updateRange(scanner, validator);
                case 9 -> updateFastCharging(scanner, validator);
                case 10 -> updatePluginHybrid(scanner, validator);
                case 11 -> looping = false;
            }
        }

        System.out.println("\nReturning to main menu...\n");
    }

    public void updateBatteryCapacity(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the car's battery capacity: ";
        this.batteryCapacity = validator.checkShort(scanner, prompt);

        System.out.println("The car's battery capacity has been updated successfully.\n");
    }

    public void updateRange(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the car's range: ";
        this.range = validator.checkShort(scanner, prompt);

        System.out.println("The car's range has been updated successfully.\n");
    }

    public void updateFastCharging(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate the car's fast charging option (Yes/No): ";
        this.fastCharging = validator.getYesNo(scanner, prompt);

        System.out.println("The car's fast charging option has been updated successfully.\n");
    }

    public void updatePluginHybrid(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate Plug-In Hybrid option (Yes/No): ";
        this.pluginHybrid = validator.getYesNo(scanner, prompt);

        System.out.println("The car's Plug-In Hybrid option has been updated successfully.\n");
    }

    @Override
    public String toString() {
        return super.toString() + "\n\t" +
                "Battery Capacity: " + batteryCapacity + " kWh\n\t" +
                "Range: " + range + " miles\n\t" +
                "Fast Charging: " + fastCharging + "\n\t" +
                "Plug-In Hybrid: " + pluginHybrid + "\n";
    }
}