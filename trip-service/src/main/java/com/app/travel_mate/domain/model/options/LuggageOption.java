package com.app.travel_mate.domain.model.options;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@ToString
public final class LuggageOption {

    private final String id;
    private final String name;
    private final String address;
    private final String near;
    private final LocalDateTime availableFrom;
    private final LocalDateTime availableUntil;
    private final int pricePerHour;
    private final int distanceMeters;

    public LuggageOption(String id, String name, String address, String near,
                         LocalDateTime availableFrom, LocalDateTime availableUntil,
                         int pricePerHour, int distanceMeters) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.near = near;
        this.availableFrom = availableFrom;
        this.availableUntil = availableUntil;
        this.pricePerHour = pricePerHour;
        this.distanceMeters = distanceMeters;
    }
}