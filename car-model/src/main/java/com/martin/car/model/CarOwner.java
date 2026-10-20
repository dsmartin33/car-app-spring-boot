package com.martin.car.model;

import jakarta.persistence.Embeddable;
import org.jspecify.annotations.NonNull;

@Embeddable
public record CarOwner(String name, String address) {

    @Override
    public @NonNull String toString() {
        return "\tOwner's Name: " + name + "\n\t" +
                "Owner's Address: " + address;
    }
}
