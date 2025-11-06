package com.app.travel_mate.domain.model.queries;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@ToString
public final class TripQuery {

    private final String origin;
    private final String destination;
    private final LocalDateTime departDate;
    private final LocalDateTime returnDate;
    private final int passengers;

    public TripQuery(String origin, String destination, LocalDateTime departDate,
                     LocalDateTime returnDate, int passengers) {
        this.origin = origin;
        this.destination = destination;
        this.departDate = departDate;
        this.returnDate = returnDate;
        this.passengers = passengers;
    }
}