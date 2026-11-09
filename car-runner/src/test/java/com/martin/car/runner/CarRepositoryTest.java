package com.martin.car.runner;

import com.martin.car.core.CarRepository;
import com.martin.car.core.CarValidatorImpl;
import com.martin.car.model.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Runs against a temporary in-memory database, never your real H2 file or PostgreSQL.
 * Each test rolls back when it finishes.
 */
@DataJpaTest
class CarRepositoryTest {
    private static final String SEDAN_VIN = "1HGCM82633A004352";
    private static final String TRUCK_VIN = "1FTFW1ET5DFC10312";

    @Autowired
    private CarRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void savesEachCarTypeAndLoadsItBackWithItsOwnFields() {
        repository.save(newSedan(SEDAN_VIN));
        repository.save(newTruck(TRUCK_VIN));
        flushAndClear();

        List<Car> cars = repository.findAll();

        assertThat(cars).hasSize(2);
        Car sedan = findByVin(cars, SEDAN_VIN);
        assertThat(sedan).isInstanceOf(Sedan.class);
        assertThat(sedan.toString()).contains(
                "Owner's Name: John Smith",
                "Owner's Address: 123 Main St",
                "Make: Toyota",
                "Car Size: MidSize",
                "Transmission Type: Automatic");
        Car truck = findByVin(cars, TRUCK_VIN);
        assertThat(truck).isInstanceOf(Truck.class);
        assertThat(truck.toString()).contains(
                "Owner's Name: Ann Lee",
                "Owner's Address: 2 Oak Ave",
                "Make: Ford",
                "Cab Type: Crew",
                "Drive Type: 4WD",
                "Towing Capacity: 9000 lbs");
    }

    @Test
    void rejectsTwoCarsWithTheSameVin() {
        repository.saveAndFlush(newSedan(SEDAN_VIN));

        assertThatThrownBy(() -> repository.saveAndFlush(newTruck(SEDAN_VIN)))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void persistsChangesMadeToAnExistingCar() {
        Car car = repository.saveAndFlush(newSedan(SEDAN_VIN));

        car.updateOwnersName(new Scanner("jane doe\n"), new CarValidatorImpl());
        repository.saveAndFlush(car);
        flushAndClear();

        assertThat(repository.findAll().getFirst().toString()).contains("Owner's Name: Jane Doe");
    }

    @Test
    void deletesCars() {
        Car car = repository.saveAndFlush(newSedan(SEDAN_VIN));

        repository.delete(car);
        repository.flush();

        assertThat(repository.count()).isZero();
    }

    @Test
    void deleteAllRemovesEveryCar() {
        repository.save(newSedan(SEDAN_VIN));
        repository.save(newTruck(TRUCK_VIN));
        repository.flush();

        repository.deleteAll();
        repository.flush();

        assertThat(repository.count()).isZero();
    }

    /**
     * Forces pending changes to be written to the database, then empties the
     * persistence context.
     * <p>
     * {@code flush()} pushes pending changes to the database.
     * {@code clear()} empties Hibernate's first-level cache, so a later
     * {@code findAll()} must read rows from the database instead of from memory.
     */
    private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * Finds the car with the given VIN in a list of cars.
     * <p>
     * Turns the list into a stream, a sequence of items you can run operations
     * on. A lambda filters the stream to cars whose VIN matches. {@code findFirst()}
     * returns the first match as an {@code Optional<Car>}, and {@code orElseThrow()}
     * opens the {@code Optional} and returns the car, or throws if it is empty.
     *
     * @param cars the cars to search
     * @param vin  the VIN to look for
     * @return the first car whose VIN equals {@code vin}
     * @throws java.util.NoSuchElementException if no car in the list has that VIN
     */
    private static Car findByVin(List<Car> cars, String vin) {
        return cars.stream()
                .filter(car -> car.getVin().equals(vin))
                .findFirst()
                .orElseThrow();
    }

    private static Sedan newSedan(String vin) {
        return new Sedan(new CarOwner("John Smith", "123 Main St"), (short) 2020, "Toyota",
                "Camry", vin, CarSize.MidSize, TransmissionType.Automatic, "No", 15.1);
    }

    private static Truck newTruck(String vin) {
        return new Truck(new CarOwner("Ann Lee", "2 Oak Ave"), (short) 2021, "Ford", "F-150",
                vin, CabType.Crew, "No", DriveType.FOUR_WD, (short) 9000);
    }
}
