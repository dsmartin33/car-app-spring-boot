package com.martin.car.model;

/**
 * Drive configuration for an {@link SUV} or {@link Truck}.
 * <p>
 * Java identifiers can't start with a digit, so four-wheel drive is {@link #FOUR_WD};
 * it is typed and displayed as {@code 4WD}.
 */
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
