package com.app.travel_mate.domain.model.queries;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@ToString
@Getter
@EqualsAndHashCode
public final class LuggageQuery {

    private final String near;
    private final LocalDate date;
    private final LocalTime timeFrom;
    private final LocalTime timeUntil;
    private final int maxDistanceMeters;
    private final int maxPricePerHour;

    public LuggageQuery(String near, LocalDate date, LocalTime timeFrom,
                        LocalTime timeUntil, int maxDistanceMeters, int maxPricePerHour) {
        this.near = near;
        this.date = date;
        this.timeFrom = timeFrom;
        this.timeUntil = timeUntil;
        this.maxDistanceMeters = maxDistanceMeters;
        this.maxPricePerHour = maxPricePerHour;
    }
}