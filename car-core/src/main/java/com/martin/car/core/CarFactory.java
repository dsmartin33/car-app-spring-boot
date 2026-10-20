package com.martin.car.core;

import com.martin.car.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class CarFactory {
    public Sedan createSedan(Scanner scanner, CarValidator validator, List<Car> cars) {
        String name = validator.checkCapitalization(scanner, "\nOwner's name: ");

        String address = validator.checkCapitalization(scanner, "Owner's address: ");

        CarOwner owner = new CarOwner(name, address);

        short year = validator.checkShort(scanner, "Sedan year: ");

        String make = validator.checkCapitalization(scanner, "Sedan make: ");

        String model = validator.modelMethod(scanner, "Sedan model: ");

        String vin = validator.vin(scanner, cars, "Enter the 17-digit VIN number: ", true);

        CarSize carSize = validator.checkCarSize(scanner, "What size car is this? (Compact/MidSize/FullSize): ");

        TransmissionType transmissionType = validator.checkTransmissionType(scanner, "What kind of transmission does this car have? (Automatic/Manual/CVT/DCT): ");

        String luxuryTrim = validator.getYesNo(scanner, "Does this car have the luxury package? (Yes/No): ");

        double trunkSize = validator.checkDouble(scanner, "Trunk size (cu ft): ");

        System.out.println("""
                
                Sedan car added.
                """);

        return new Sedan(owner, year, make, model, vin, carSize, transmissionType, luxuryTrim, trunkSize);
    }

    public SportsCar createSportsCar(Scanner scanner, CarValidator validator, List<Car> cars) {
        String name = validator.checkCapitalization(scanner, "\nOwner's name: ");

        String address = validator.checkCapitalization(scanner, "Owner's address: ");

        CarOwner owner = new CarOwner(name, address);

        short year = validator.checkShort(scanner, "Sports car year: ");

        String make = validator.checkCapitalization(scanner, "Sports car make: ");

        String model = validator.modelMethod(scanner, "Sports car model: ");

        String vin = validator.vin(scanner, cars, "Enter the 17-digit VIN number: ", true);

        short horsepower = validator.checkShort(scanner, "Horsepower: ");

        double zeroToSixtyTime = validator.checkDouble(scanner, "0-60 time (seconds): ");

        short topSpeed = validator.checkShort(scanner, "Top speed (MPH): ");

        String turbocharged = validator.getYesNo(scanner, "Turbo charged? (Yes/No): ");

        System.out.println("""
                
                Sports car added.
                """);

        return new SportsCar(owner, year, make, model, vin, horsepower, zeroToSixtyTime, topSpeed, turbocharged);
    }

    public ElectricCar createElectricCar(Scanner scanner, CarValidator validator, List<Car> cars) {
        String name = validator.checkCapitalization(scanner, "\nOwner's name: ");

        String address = validator.checkCapitalization(scanner, "Owner's address: ");

        CarOwner owner = new CarOwner(name, address);

        short year = validator.checkShort(scanner, "Electric car year: ");

        String make = validator.checkCapitalization(scanner, "Electric car make: ");

        String model = validator.modelMethod(scanner, "Electric car model: ");

        String vin = validator.vin(scanner, cars, "Enter the 17-digit VIN number: ", true);

        short batteryCapacity = validator.checkShort(scanner, "Battery Capacity (kWh): ");

        short range = validator.checkShort(scanner, "Range (Miles): ");

        String fastCharging = validator.getYesNo(scanner, "Fast Charging? (Yes/No): ");

        String pluginHybrid = validator.getYesNo(scanner, "Plug-In Hybrid? (Yes/No): ");

        System.out.println("""
                
                Electric car added.
                """);

        return new ElectricCar(owner, year, make, model, vin, batteryCapacity, range, fastCharging, pluginHybrid);
    }

    public SUV createSUV(Scanner scanner, CarValidator validator, List<Car> cars) {
        String name = validator.checkCapitalization(scanner, "\nOwner's name: ");

        String address = validator.checkCapitalization(scanner, "Owner's address: ");

        CarOwner owner = new CarOwner(name, address);

        short year = validator.checkShort(scanner, "SUV year: ");

        String make = validator.checkCapitalization(scanner, "SUV make: ");

        String model = validator.modelMethod(scanner, "SUV model: ");

        String vin = validator.vin(scanner, cars, "Enter the 17-digit VIN number: ", true);

        short seatingCapacity = validator.checkShort(scanner, "Maximum seating capacity: ");

        double cargoCapacity = validator.checkDouble(scanner, "Cargo capacity (cu ft): ");

        DriveType driveType = validator.checkDriveType(scanner, "Drive Type (FWD/RWD/AWD/4WD): ");

        String offRoadPackage = validator.getYesNo(scanner, "Off-Road Package? (Yes/No): ");

        System.out.println("""
                
                SUV added.
                """);

        return new SUV(owner, year, make, model, vin, seatingCapacity, cargoCapacity, driveType, offRoadPackage);
    }

    public Truck createTruck(Scanner scanner, CarValidator validator, List<Car> cars) {
        String name = validator.checkCapitalization(scanner, "\nOwner's name: ");

        String address = validator.checkCapitalization(scanner, "Owner's address: ");

        CarOwner owner = new CarOwner(name, address);

        short year = validator.checkShort(scanner, "Truck year: ");

        String make = validator.checkCapitalization(scanner, "Truck make: ");

        String model = validator.modelMethod(scanner, "Truck model: ");

        String vin = validator.vin(scanner, cars, "Enter the 17-digit VIN number: ", true);

        CabType cabType = validator.checkCabType(scanner, "Cab size (Regular/Extended/Crew): ");

        String dieselEngine = validator.getYesNo(scanner, "Diesel Engine? (Yes/No): ");

        DriveType driveType = validator.checkDriveType(scanner, "Drive Type (FWD/RWD/AWD/4WD): ");

        short towingCapacity = validator.checkShort(scanner, "Maximum towing capacity (lbs.): ");

        System.out.println("""
                
                Truck added.
                """);

        return new Truck(owner, year, make, model, vin, cabType, dieselEngine, driveType, towingCapacity);
    }
}
