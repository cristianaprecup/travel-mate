package com.app.travel_mate.domain.model.queries;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public final class TripQuery {

    private final String origin;
    private final String destination;
    private final LocalDateTime departDate;
    private final LocalDateTime returnDate;
    private final int passengers;
    private Integer maxBudget;
    private Integer maxDurationMinutes;
    private Integer maxStops;
    private Boolean baggageRequired;
}