package com.sample.carmarket.entity;

import io.jmix.core.metamodel.datatype.impl.EnumClass;

import javax.annotation.Nullable;


public enum AvailabilityStatus implements EnumClass<String> {

    IN_STOCK("I"),
    SOLD("S");

    private final String id;

    AvailabilityStatus(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static AvailabilityStatus fromId(String id) {
        for (AvailabilityStatus at : AvailabilityStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}