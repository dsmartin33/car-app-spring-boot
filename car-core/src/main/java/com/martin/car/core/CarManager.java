package com.martin.car.core;

import com.martin.car.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Loads, adds, searches, displays, updates and deletes vehicles. Persists all changes to the
 * database through {@link CarRepository}. The in-memory list is kept sorted by type.
 */
@Service
public class CarManager {
    private final CarRepository repository;

    public CarManager(CarRepository repository) {
        this.repository = repository;
    }

    /**
     * Loads all saved cars from the database into a new, modifiable list sorted by type.
     *
     * @return a new list of all persisted cars, sorted by type
     */
    public List<Car> loadCars() {
        List<Car> cars = new LinkedList<>(repository.findAll());
        carSortByType(cars);
        return cars;
    }

    public void addCar(Car car, List<Car> cars) {
        cars.add(repository.save(car));
        carSortByType(cars);
    }

    /**
     * Sorts the given list of cars in place by vehicle type.
     * <p>
     * Cars are ordered as follows: Sedan, SportsCar, ElectricCar, SUV, Truck.
     * Cars of the same type keep their original relative order, since
     * {@link List#sort} is a stable sort.
     *
     * @param cars the list to sort; modified in place
     * @throws IllegalStateException if the list contains a car of an unrecognized type
     */
    private void carSortByType(List<Car> cars) {
        cars.sort(Comparator.comparingInt(this::getTypeOrder));
    }

    /**
     * Returns the sort rank for a car based on its type by using an unnamed pattern variable ({@code _}).
     * <p>
     * Ranks are: Sedan = 0, SportsCar = 1, ElectricCar = 2, SUV = 3, Truck = 4.
     * Lower ranks sort first.
     *
     * @param car the car to rank
     * @return the sort key for the car's type, from 0 to 4
     * @throws IllegalStateException if the car is not one of the known types
     */
    private int getTypeOrder(Car car) {
        return switch(car) {
            case Sedan _ -> 0;
            case SportsCar _ -> 1;
            case ElectricCar _ -> 2;
            case SUV _ -> 3;
            case Truck _ -> 4;
            default -> throw new IllegalStateException("Unknown vehicle type: " + car.getClass().getSimpleName());
        };
    }

    /**
     * Prompts the user for a VIN and looks up the matching car (case-insensitive).
     * Prints the car if found, or a "not found" message otherwise.
     *
     * @param scanner   input source for the VIN prompt
     * @param validator validates the VIN format before searching
     * @param cars      the list of cars to search
     * @return the matching car, or an empty Optional if no car has that VIN
     */
    public Optional<Car> findCarByVin(Scanner scanner, CarValidator validator, List<Car> cars) {
        String prompt = "\nEnter the 17-digit VIN number: ";
        String vin = validator.vin(scanner, cars, prompt, false);

        Optional<Car> match = cars.stream()
                .filter(car -> car.getVin().equalsIgnoreCase(vin))
                .findFirst();

        match.ifPresentOrElse(
                car -> System.out.println("\nVehicle found: \n\n" + car),
                () -> System.out.println("\nVehicle with VIN " + vin + " not found.\n"));

        return match;
    }

    /**
     * Prints all cars under a heading for each type. Relies on the list already being sorted by
     * type because a new heading is printed whenever the type changes (see {@link #loadCars()}).
     *
     * @param cars the cars to display
     */
    public void displayAllCars(List<Car> cars) {
        System.out.println("""
                
                Displaying all vehicles:
                """);

        if (cars.isEmpty()) {
            System.out.println("No vehicles found.\n");
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
                    Vehicle removed.
                    """);
        });
    }

    public void deleteAllCars(List<Car> cars) {
        System.out.println("""
                
                Deleting all vehicles:
                """);

        if (cars.isEmpty()) {
            System.out.println("Nothing to delete. List empty.\n");
            return;
        }

        repository.deleteAll();
        cars.clear();
        System.out.println("All vehicles have been removed.\n");
    }
}
