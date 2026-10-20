package com.martin.car.model;

import java.util.List;
import java.util.Scanner;

public interface CarValidator {
    String checkCapitalization(Scanner scanner, String prompt);
    String getYesNo(Scanner scanner, String prompt);
    String modelMethod(Scanner scanner, String prompt);
    String vin(Scanner scanner, List<Car> cars, String prompt, boolean checkDuplicate);
    CarSize checkCarSize(Scanner scanner, String prompt);
    TransmissionType checkTransmissionType(Scanner scanner, String prompt);
    DriveType checkDriveType(Scanner scanner, String prompt);
    CabType checkCabType(Scanner scanner, String prompt);
    Double checkDouble(Scanner scanner, String prompt);
    Short checkShort(Scanner scanner, String prompt);
    Byte menuMethod(Scanner scanner);
}
