package com.martin.car.model;

import java.util.List;
import java.util.Scanner;

/**
 * Used for console input validation. Every method prompts and reads from the {@link Scanner}. Methods
 * will re-prompt until the input is valid, so callers always get a usable value.
 */
public interface CarValidator {
    String checkCapitalization(Scanner scanner, String prompt);
    String getYesNo(Scanner scanner, String prompt);
    /**
     * Prompts for a car model. Known models get their official values
     * (ex. {@code gt-r} becomes {@code GT-R}); anything else is capitalized word by word.
     */
    String modelMethod(Scanner scanner, String prompt);
    /**
     * Prompts until a 17-character VIN is entered.
     *
     * @param checkDuplicate {@code true} Used to check if a VIN is a duplicate in {@code cars}
     * {@code false} to skip the check.
     * @return the VIN in upper case
     */
    String vin(Scanner scanner, List<Car> cars, String prompt, boolean checkDuplicate);
    CarSize checkCarSize(Scanner scanner, String prompt);
    TransmissionType checkTransmissionType(Scanner scanner, String prompt);
    DriveType checkDriveType(Scanner scanner, String prompt);
    CabType checkCabType(Scanner scanner, String prompt);
    Double checkDouble(Scanner scanner, String prompt);
    Short checkShort(Scanner scanner, String prompt);
    /**
     * Prompts until a whole number from 1 to 11 is entered.
     *
     * @return the chosen menu option
     */
    Byte menuMethod(Scanner scanner);
}
