package com.martin.car.core;

import com.martin.car.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarManager {
    private final CarRepository repository;

    public CarManager(CarRepository repository) {
        this.repository = repository;
    }

    public List<Car> loadCars() {
        List<Car> cars = new LinkedList<>(repository.findAll());
        carSortByType(cars);
        return cars;
    }

    public void addCar(Car car, List<Car> cars) {
        cars.add(repository.save(car));
        carSortByType(cars);
    }

    private void carSortByType(List<Car> cars) {
        cars.sort(Comparator.comparingInt(this::getTypeOrder));
    }

    private int getTypeOrder(Car car) {
        return switch(car) {
            case Sedan _ -> 0;
            case SportsCar _ -> 1;
            case ElectricCar _ -> 2;
            case SUV _ -> 3;
            case Truck _ -> 4;
            default -> throw new IllegalStateException("Unknown car type: " + car.getClass().getSimpleName());
        };
    }

    public Optional<Car> findCarByVin(Scanner scanner, CarValidator validator, List<Car> cars) {
        String prompt = "\nEnter the 17-digit VIN number: ";
        String vin = validator.vin(scanner, cars, prompt, false);

        Optional<Car> match = cars.stream()
                .filter(car -> car.getVin().equals(vin))
                .findFirst();

        match.ifPresentOrElse(
                car -> System.out.println("\nCar found: \n\n" + car),
                () -> System.out.println("\nCar with VIN " + vin + " not found.\n"));

        return match;
    }

    public void displayAllCars(List<Car> cars) {
        System.out.println("""
                
                Displaying all cars:
                """);

        if (cars.isEmpty()) {
            System.out.println("No cars found.\n");
            return;
        }

        int currentType = -1;
        for (Car car : cars) {
            int type = getTypeOrder(car);

            if (type != currentType) {
                currentType = type;

                switch(type) {
                    case 0 -> System.out.println("*** Sedans ***");
                    case 1 -> System.out.println("\n*** Sports Cars ***");
                    case 2 -> System.out.println("\n*** Electric Cars ***");
                    case 3 -> System.out.println("\n*** SUVs ***");
                    case 4 -> System.out.println("\n*** Trucks ***");
                }
            }
            System.out.println(car);
        }
    }

    public void updateCarByVin(Scanner scanner, CarValidator validator, List<Car> cars) {
        findCarByVin(scanner, validator, cars).ifPresent(car -> {
            car.updateCar(scanner, validator, cars);
            repository.save(car);
        });
    }

    public void deleteCarByVin(Scanner scanner, CarValidator validator, List<Car> cars) {
        findCarByVin(scanner, validator, cars).ifPresent(car -> {
            repository.delete(car);
            cars.remove(car);
            System.out.println("""
                    Car removed.
                    """);
        });
    }

    public void deleteAllCars(List<Car> cars) {
        System.out.println("""
                
                Deleting all cars:
                """);

        if (cars.isEmpty()) {
            System.out.println("Nothing to delete. List empty.\n");
            return;
        }

        repository.deleteAll();
        cars.clear();
        System.out.println("All cars have been removed.\n");
    }
}
