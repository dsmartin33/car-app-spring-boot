package com.martin.car.core;

import com.martin.car.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.martin.car.core.TestCars.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarManagerTest {
    @Mock
    private CarRepository repository;

    private final CarValidatorImpl validator = new CarValidatorImpl();
    private CarManager manager;

    @BeforeEach
    void setUp() {
        manager = new CarManager(repository);
    }

    // ---------- add ----------

    @Test
    void addCar_savesCarAndKeepsListSortedByType() {
        // The mock repository returns null from save() unless we tell it to return the car it was given.
        when(repository.save(any(Car.class))).thenAnswer(invocation -> invocation.getArgument(0));
        List<Car> cars = new LinkedList<>();
        Truck truck = newTruck(TRUCK_VIN);
        Sedan sedan = newSedan(SEDAN_VIN);

        manager.addCar(truck, cars);
        manager.addCar(sedan, cars);

        verify(repository).save(truck);
        verify(repository).save(sedan);
        assertThat(cars).containsExactly(sedan, truck); // sedans are listed before trucks
    }

    // ---------- load ----------

    @Test
    void loadCars_returnsCarsFromRepositorySortedByType() {
        when(repository.findAll()).thenReturn(List.of(newTruck(TRUCK_VIN), newSedan(SEDAN_VIN)));

        List<Car> cars = manager.loadCars();

        assertThat(cars).extracting(Car::getVin).containsExactly(SEDAN_VIN, TRUCK_VIN);
    }

    // ---------- find ----------

    @Test
    void findCarByVin_returnsTheMatchingCar() {
        Sedan sedan = newSedan(SEDAN_VIN);

        Optional<Car> found = manager.findCarByVin(scanner(SEDAN_VIN), validator, List.of(sedan));

        assertThat(found).containsSame(sedan);
    }

    @Test
    void findCarByVin_returnsEmptyWhenNoCarHasThatVin() {
        Optional<Car> found = manager.findCarByVin(scanner(OTHER_VIN), validator, List.of(newSedan(SEDAN_VIN)));

        assertThat(found).isEmpty();
    }

    // ---------- update ----------

    @Test
    void updateCarByVin_savesTheCarAfterTheUserEditsIt() {
        Sedan sedan = newSedan(SEDAN_VIN);
        List<Car> cars = new LinkedList<>(List.of(sedan));

        // VIN to look up, menu option 1 (owner's name), the new name, then menu option 11 (exit).
        manager.updateCarByVin(scanner(SEDAN_VIN, "1", "jane doe", "11"), validator, cars);

        verify(repository).save(sedan);
        assertThat(sedan.toString()).contains("Owner's Name: Jane Doe");
    }

    @Test
    void updateCarByVin_doesNotSaveAnythingWhenVinIsNotFound() {
        List<Car> cars = new LinkedList<>(List.of(newSedan(SEDAN_VIN)));

        manager.updateCarByVin(scanner(OTHER_VIN), validator, cars);

        verify(repository, never()).save(any(Car.class));
    }

    // ---------- delete ----------

    @Test
    void deleteCarByVin_removesTheCarFromTheRepositoryAndTheList() {
        Sedan sedan = newSedan(SEDAN_VIN);
        Truck truck = newTruck(TRUCK_VIN);
        List<Car> cars = new LinkedList<>(List.of(sedan, truck));

        manager.deleteCarByVin(scanner(TRUCK_VIN), validator, cars);

        verify(repository).delete(truck);
        assertThat(cars).containsExactly(sedan);
    }

    @Test
    void deleteCarByVin_doesNothingWhenVinIsNotFound() {
        Sedan sedan = newSedan(SEDAN_VIN);
        List<Car> cars = new LinkedList<>(List.of(sedan));

        manager.deleteCarByVin(scanner(OTHER_VIN), validator, cars);

        verify(repository, never()).delete(any(Car.class));
        assertThat(cars).containsExactly(sedan);
    }

    @Test
    void deleteAllCars_clearsTheRepositoryAndTheList() {
        List<Car> cars = new LinkedList<>(List.of(newSedan(SEDAN_VIN), newTruck(TRUCK_VIN)));

        manager.deleteAllCars(cars);

        verify(repository).deleteAll();
        assertThat(cars).isEmpty();
    }

    @Test
    void deleteAllCars_doesNotTouchTheRepositoryWhenTheListIsEmpty() {
        manager.deleteAllCars(new LinkedList<>());

        verify(repository, never()).deleteAll();
    }
}
