package com.app.travel_mate.domain.model.queries;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@EqualsAndHashCode
public final class StayQuery {

    private final String city;
    private final LocalDateTime checkIn;
    private final LocalDateTime checkOut;
    private final int guests;
    private final int maxPriceAmount;
    private final int minRating;
    private final int maxDistanceMeters;

    public StayQuery(String city, LocalDateTime checkIn, LocalDateTime checkOut,
                     int guests, int maxPriceAmount, int minRating,
                     int maxDistanceMeters) {
        this.city = city;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guests = guests;
        this.maxPriceAmount = maxPriceAmount;
        this.minRating = minRating;
        this.maxDistanceMeters = maxDistanceMeters;
    }
}