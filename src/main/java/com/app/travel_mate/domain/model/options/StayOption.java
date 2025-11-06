package com.app.travel_mate.domain.model.options;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@ToString
public final class StayOption {

    private final String id;
    private final String name;
    private final String type;
    private final String address;
    private final LocalDateTime checkIn;
    private final LocalDateTime checkOut;
    private final int priceAmount;
    private final int distanceMeters;

    public StayOption(String id, String name, String type, String address,
                      LocalDateTime checkIn, LocalDateTime checkOut,
                      int priceAmount, int distanceMeters) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.address = address;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.priceAmount = priceAmount;
        this.distanceMeters = distanceMeters;
    }
}