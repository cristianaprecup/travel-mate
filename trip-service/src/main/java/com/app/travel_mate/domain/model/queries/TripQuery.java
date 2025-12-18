package com.app.travel_mate.domain.model.queries;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@Setter
@NoArgsConstructor
public final class TripQuery {

    private String origin;
    private String destination;
    private LocalDateTime departDate;
    private LocalDateTime returnDate;
    private int passengers;
    private Integer maxBudget;
    private Integer maxDurationMinutes;
    private Integer maxStops;
    private Boolean baggageRequired;
}