package com.martin.car.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Scanner;

@Entity
@Table(name = "cars")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "owner_name", nullable = false)),
            @AttributeOverride(name = "address", column = @Column(name = "owner_address", nullable = false))
    })
    private CarOwner owner;

    @Column(name = "car_year", nullable = false)
    private short year;

    private String make;
    private String model;

    @Column(unique = true, nullable = false, length = 17)
    private String vin;

    protected Car() {
    }

    public Car(CarOwner owner, short year, String make, String model, String vin) {
        this.owner = owner;
        this.year = year;
        this.make = make;
        this.model = model;
        this.vin = vin;
    }

    public String getVin() {
        return vin;
    }

    public abstract void updateCar(Scanner scanner, CarValidator validator, List<Car> cars);

    public void updateOwnersName(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate owner's name: ";
        String name = validator.checkCapitalization(scanner, prompt);

        this.owner = new CarOwner(name, owner.address());

        System.out.println("The owner's name has been updated successfully.\n");
    }

    public void updateOwnersAddress(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate owner's address: ";
        String address = validator.checkCapitalization(scanner, prompt);

        this.owner = new CarOwner(owner.name(), address);

        System.out.println("The owner's address has been updated successfully.\n");
    }

    public void updateYear(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate car year: ";
        this.year = validator.checkShort(scanner, prompt);

        System.out.println("The car's year has been updated successfully.\n");
    }

    public void updateMake(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate car make: ";
        this.make = validator.checkCapitalization(scanner, prompt);

        System.out.println("The car's make has been updated successfully.\n");
    }

    public void updateModel(Scanner scanner, CarValidator validator) {
        String prompt = "\nUpdate car model: ";
        this.model = validator.modelMethod(scanner, prompt);

        System.out.println("The car's model has been updated successfully.\n");
    }

    public void updateVin(Scanner scanner, CarValidator validator, List<Car> cars) {
        this.vin = validator.vin(scanner, cars, "\nEnter the 17-digit VIN number: ", true);
        System.out.println("The car's vin has been updated successfully.\n");
    }

    @Override
    public String toString() {
        return owner + "\n\t" +
                "Year: " + year + "\n\t" +
                "Make: " + make + "\n\t" +
                "Model: " + model + "\n\t" +
                "VIN: " + vin;
    }
}
