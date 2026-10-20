package com.martin.car.runner;

import com.martin.car.core.CarFactory;
import com.martin.car.core.CarManager;
import com.martin.car.model.Car;
import com.martin.car.model.CarValidator;

import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class CarConsole implements CommandLineRunner {
    private final CarManager manager;
    private final CarFactory factory;
    private final CarValidator validator;

    public CarConsole(CarManager manager, CarFactory factory, CarValidator validator) {
        this.manager = manager;
        this.factory = factory;
        this.validator = validator;
    }

    @Override
    public void run(String @NonNull ... args) {
        final Scanner scanner = new Scanner(System.in);
        final List<Car> cars = manager.loadCars();

        System.out.println("""
                
                Welcome to the Car Main Menu.
                Please pick an option below.
                """);

        boolean running = true;
        while (running) {
            System.out.println("""
                    *** Main Menu ***
                    
                    1. Add Sedan
                    2. Add Sports Car
                    3. Add Electric Car
                    4. Add SUV
                    5. Add Truck
                    6. Search car by VIN
                    7. Display all cars
                    8. Update car by VIN
                    9. Delete car by VIN
                    10. Delete all cars
                    11. Exit
                    """);

            byte option = validator.menuMethod(scanner);

            switch (option) {
                case 1 -> manager.addCar(factory.createSedan(scanner, validator, cars), cars);
                case 2 -> manager.addCar(factory.createSportsCar(scanner, validator, cars), cars);
                case 3 -> manager.addCar(factory.createElectricCar(scanner, validator, cars), cars);
                case 4 -> manager.addCar(factory.createSUV(scanner, validator, cars), cars);
                case 5 -> manager.addCar(factory.createTruck(scanner, validator, cars), cars);
                case 6 -> manager.findCarByVin(scanner, validator, cars);
                case 7 -> manager.displayAllCars(cars);
                case 8 -> manager.updateCarByVin(scanner, validator, cars);
                case 9 -> manager.deleteCarByVin(scanner, validator, cars);
                case 10 -> manager.deleteAllCars(cars);
                case 11 -> running = false;
            }
        }
    }
}
