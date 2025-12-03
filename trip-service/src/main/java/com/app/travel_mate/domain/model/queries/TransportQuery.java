package com.app.travel_mate.domain.model.queries;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@ToString
public final class TransportQuery {

    private final String origin;
    private final String destination;
    private final LocalDateTime departDate;
    private final int passengers;
    private final int maxPriceAmount;
    private final int maxDurationMinutes;
    private final int maxStops;
    private final boolean baggageRequired;

    public TransportQuery(String origin, String destination, LocalDateTime departDate,
                          int passengers, int maxPriceAmount, int maxDurationMinutes,
                          int maxStops, boolean baggageRequired) {
        this.origin = origin;
        this.destination = destination;
        this.departDate = departDate;
        this.passengers = passengers;
        this.maxPriceAmount = maxPriceAmount;
        this.maxDurationMinutes = maxDurationMinutes;
        this.maxStops = maxStops;
        this.baggageRequired = baggageRequired;
    }
}