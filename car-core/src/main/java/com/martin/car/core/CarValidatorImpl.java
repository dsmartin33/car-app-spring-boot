package com.martin.car.core;

import com.martin.car.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Service
public class CarValidatorImpl implements CarValidator {
    private final Map<String, String> carModels = Map.<String, String>ofEntries(
            Map.entry("cx-3", "CX-3"),
            Map.entry("cx-5", "CX-5"),
            Map.entry("cx-30", "CX-30"),
            Map.entry("cx-50", "CX-50"),
            Map.entry("cx-70", "CX-70"),
            Map.entry("cx-90", "CX-90"),
            Map.entry("mx-5", "MX-5"),
            Map.entry("mx-30", "MX-30"),
            Map.entry("gt-r", "GT-R"),
            Map.entry("nsx", "NSX"),
            Map.entry("rx-7", "RX-7"),
            Map.entry("rx-8", "RX-8"),
            Map.entry("es", "ES"),
            Map.entry("gs", "GS"),
            Map.entry("gx", "GX"),
            Map.entry("is", "IS"),
            Map.entry("lc", "LC"),
            Map.entry("ls", "LS"),
            Map.entry("lx", "LX"),
            Map.entry("rc", "RC"),
            Map.entry("ux", "UX"),
            Map.entry("tlx", "TLX"),
            Map.entry("rlx", "RLX"),
            Map.entry("mdx", "MDX"),
            Map.entry("rdx", "RDX"),
            Map.entry("ilx", "ILX"),
            Map.entry("qx50", "QX50"),
            Map.entry("qx60", "QX60"),
            Map.entry("qx80", "QX80"),
            Map.entry("db11", "DB11"),
            Map.entry("db12", "DB12"),
            Map.entry("dbx", "DBX"),
            Map.entry("mc12", "MC12"),
            Map.entry("mc20", "MC20"),
            Map.entry("rav-4", "RAV-4"),
            Map.entry("cr-v", "CR-V"),
            Map.entry("hr-v", "HR-V"),
            Map.entry("cr-v e:fcev", "CR-V e:FCEV"),
            Map.entry("cr-v hybrid", "CR-V Hybrid"),
            Map.entry("gr86", "GR86"),
            Map.entry("model y", "Model Y"),
            Map.entry("model s", "Model S"),
            Map.entry("model x", "Model X"),
            Map.entry("i4", "i4"),
            Map.entry("i5", "i5"),
            Map.entry("i7", "i7"),
            Map.entry("ix", "iX"),
            Map.entry("ix3", "iX3"),
            Map.entry("xm", "XM"),
            Map.entry("bz", "bZ"),
            Map.entry("c-hr", "C-HR"),
            Map.entry("gr corolla", "GR Corolla"),
            Map.entry("gr supra", "GR Supra"),
            Map.entry("bz4x", "bZ4X"),
            Map.entry("q4 e-tron", "Q4 e-tron"),
            Map.entry("q4 sportback e-tron", "Q4 Sportback e-tron"),
            Map.entry("q6 e-tron", "Q6 e-tron"),
            Map.entry("sq6 e-tron", "SQ6 e-tron"),
            Map.entry("q6 sportback e-tron", "Q6 Sportback e-tron"),
            Map.entry("sq6 sportback e-tron", "SQ6 Sportback e-tron"),
            Map.entry("a6 sportback e-tron", "A6 Sportback e-tron"),
            Map.entry("s6 sportback e-tron", "S6 Sportback e-tron"),
            Map.entry("s e-tron gt", "S e-tron GT"),
            Map.entry("rs e-tron gt", "RS e-tron GT")
    );

    private String checkEmptyString(Scanner scanner, String prompt) {
        while(true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Invalid input. This cannot be left blank.");
        }
    }

    @Override
    public String getYesNo(Scanner scanner, String prompt) {
        while(true) {
            String input = checkEmptyString(scanner, prompt);

            if (input.equalsIgnoreCase("Yes") || input.equalsIgnoreCase("No")) {
                input = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
                return input;
            }
            System.out.println("Invalid input. Please enter 'Yes' or 'No'.");
        }
    }

    @Override
    public CarSize checkCarSize(Scanner scanner, String prompt) {
        while(true) {
            String input = checkEmptyString(scanner, prompt);

            for (CarSize type : CarSize.values()) {
                if (type.name().equalsIgnoreCase(input)) {
                    return type;
                }
            }
            System.out.println("Invalid input. Please enter the car's size (Compact/MidSize/FullSize).");
        }
    }

    @Override
    public DriveType checkDriveType(Scanner scanner, String prompt) {
        while(true) {
            String input = checkEmptyString(scanner,prompt);

            if (input.equalsIgnoreCase("4WD")) {
                input = "FOUR_WD";
            }

            for (DriveType type : DriveType.values()) {
                if (type.name().equalsIgnoreCase(input)) {
                    return type;
                }
            }
            System.out.println("Invalid input. Please enter the SUV's drive type (FWD/RWD/AWD/4WD).");
        }
    }

    @Override
    public CabType checkCabType(Scanner scanner, String prompt) {
        while(true) {
            String input = checkEmptyString(scanner, prompt);

            for (CabType type : CabType.values()) {
                if (type.name().equalsIgnoreCase(input)) {
                    return type;
                }
            }
            System.out.println("Invalid input. Please enter the truck's size (Regular/Extended/Crew).");
        }
    }

    @Override
    public TransmissionType checkTransmissionType(Scanner scanner, String prompt) {
        while(true) {
            String input = checkEmptyString(scanner, prompt);

            for (TransmissionType type : TransmissionType.values()){
                if (type.name().equalsIgnoreCase(input)) {
                    return type;
                }
            }
            System.out.println("Invalid input. Please enter Automatic, Manual, CVT, or DCT.");
        }
    }

    @Override
    public String checkCapitalization(Scanner scanner, String prompt) {
        String input = checkEmptyString(scanner, prompt);
        return capitalizeWords(input);
    }

    private String capitalizeWords(String input) {
        String[] words = input.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            result.append(word.substring(0,1).toUpperCase())
                    .append(word.substring(1).toLowerCase())
                    .append(" ");
        }
        return result.toString().trim();
    }

    @Override
    public String modelMethod(Scanner scanner, String prompt) {
        String input = checkEmptyString(scanner, prompt);

        if (carModels.containsKey(input.toLowerCase())) {
            return carModels.get(input.toLowerCase());
        }
        else {
            return capitalizeWords(input);
        }
    }

    @Override
    public String vin(Scanner scanner, List<Car> cars, String prompt, boolean checkDuplicate) {
        while(true) {
            String input = checkEmptyString(scanner, prompt);

            if (input.length() != 17) {
                System.out.println("Invalid input. VIN numbers are 17 characters long, please re-enter.");
                continue;
            }
            if (checkDuplicate && cars.stream().anyMatch(car -> car.getVin().equalsIgnoreCase(input))) {
                System.out.println("You have entered a duplicate VIN. Please re-enter.");
                continue;
            }
            return input.toUpperCase();
        }
    }

    @Override
    public Double checkDouble(Scanner scanner, String prompt) {
        while(true) {
            String input = checkEmptyString(scanner, prompt);

            try {
                return Double.parseDouble(input);
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numerical value.");
            }
        }
    }

    @Override
    public Short checkShort(Scanner scanner, String prompt) {
        while(true) {
            String input = checkEmptyString(scanner, prompt);

            try {
                return Short.parseShort(input);
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    @Override
    public Byte menuMethod(Scanner scanner) {
        while(true) {
            String input = checkEmptyString(scanner, "Enter Option: ");
            byte option;

            try {
                option = Byte.parseByte(input);
                if (option >= 1 && option <= 11) {
                    return option;
                }
                else {
                    System.out.println("Invalid input. Please enter a number between 1-11.");
                }
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1-11.");
            }
        }
    }
}
