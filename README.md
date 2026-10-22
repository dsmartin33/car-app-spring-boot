# Car Management Application

A console application for managing a car inventory, built with Java and Spring Boot. Cars are saved to a relational database with Spring Data JPA, so your data is still there the next time you run the app.

## Features

- Add five types of cars: Sedan, Sports Car, Electric Car, SUV, and Truck, each with its own type-specific fields
- Search for a car by VIN
- Display all cars, grouped by type
- Update any field of a car, using a menu specific to that car type
- Delete a car by VIN, or delete all cars
- Input validation for every prompt: 17-character VINs with duplicate detection, Yes/No answers, enum choices (transmission, drive type, cab size, car size), and numeric fields
- Data persists between runs

## Tech Stack

- Java 25
- Spring Boot 4.1.1
- Spring Data JPA with Hibernate
- H2 (default) and PostgreSQL (optional)
- Maven (multi-module)

## Project Structure

| Module | Purpose |
| --- | --- |
| `car-model` | Domain classes (`Car` and its subclasses, `CarOwner`, enums) and the `CarValidator` interface |
| `car-core` | Business logic: `CarFactory`, `CarManager`, `CarValidatorImpl`, and the `CarRepository` |
| `car-runner` | Application entry point (`CarApplication`) and the console menu (`CarConsole`) |

## Running the App

### Quick start (no database setup)

By default the app uses an embedded H2 database, so there is nothing to install besides Java.

1. Install JDK 25.
2. Clone the repository:
   ```
   git clone <repository-url>
   cd car
   ```
3. Build the project:
   ```
   mvn clean install
   ```
4. Open the project in your IDE and run `CarApplication` (in `car-runner`, package `com.martin.car.runner`).

H2 stores its data in a `data/` folder in the working directory, so cars you add are still there after a restart.

### Using PostgreSQL (optional)

The app also runs against PostgreSQL through a Spring profile.

1. Install and start PostgreSQL (macOS with Homebrew shown):
   ```
   brew install postgresql@17
   brew services start postgresql@17
   ```
2. Create the database and user the profile expects:
   ```
   psql postgres -c "CREATE USER caruser WITH PASSWORD 'carpass';"
   psql postgres -c "CREATE DATABASE cardb OWNER caruser;"
   ```
3. Run `CarApplication` with the `postgres` profile active, for example by setting the environment variable `SPRING_PROFILES_ACTIVE=postgres` or the program argument `--spring.profiles.active=postgres`.

The `caruser` / `carpass` credentials are demo values for a local database only, and they are defined in `application-postgres.properties`. Hibernate creates the tables automatically on first run.

## Using the App

The main menu offers these options:

```
1. Add Sedan             7. Display all cars
2. Add Sports Car        8. Update car by VIN
3. Add Electric Car      9. Delete car by VIN
4. Add SUV              10. Delete all cars
5. Add Truck            11. Exit
6. Search car by VIN
```

Enter a number and follow the prompts. Each car is identified by its 17-character VIN, which must be unique.

## Design Notes

- **Joined-table inheritance.** `Car` is an abstract JPA entity and each car type has its own table, which avoids a single wide table full of empty columns.
- **Embedded owner.** `CarOwner` is a Java record mapped as an embeddable, so the owner's name and address are stored as columns on the car's table.
- **Breaking a circular dependency.** The `CarValidator` interface lives in the model module and its implementation lives in the core module, so the modules don't depend on each other in a cycle.
- **In-memory working copy.** The list of cars is loaded from the database at startup, and every add, update, and delete is written through to the database.

## License

[Add a license here, for example MIT, or state that all rights are reserved.]
