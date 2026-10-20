package com.martin.car.model;

public enum DriveType {
    FWD,
    RWD,
    AWD,
    FOUR_WD;

    @Override
    public String toString() {
        return switch (this) {
            case FWD -> "FWD";
            case RWD -> "RWD";
            case AWD -> "AWD";
            case FOUR_WD -> "4WD";
        };
    }
}
