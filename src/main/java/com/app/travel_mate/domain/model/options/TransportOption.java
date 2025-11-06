package com.app.travel_mate.domain.model.options;

import com.app.travel_mate.domain.model.enums.TransportMode;
import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@EqualsAndHashCode
@Getter
@ToString
public final class TransportOption {

    private final String id;
    private final TransportMode mode;
    private final String operator;
    private final String origin;
    private final String destination;
    private final LocalDateTime departTime;
    private final LocalDateTime arriveTime;
    private final int durationMinutes;
    private final int stops;
    private final boolean baggageIncluded;
    private final int priceAmount;

    public TransportOption(String id, TransportMode mode, String operator,
                           String origin, String destination,
                           LocalDateTime departTime, LocalDateTime arriveTime,
                           int durationMinutes, int stops, boolean baggageIncluded,
                           int priceAmount) {
        this.id = id;
        this.mode = mode;
        this.operator = operator;
        this.origin = origin;
        this.destination = destination;
        this.departTime = departTime;
        this.arriveTime = arriveTime;
        this.durationMinutes = durationMinutes;
        this.stops = stops;
        this.baggageIncluded = baggageIncluded;
        this.priceAmount = priceAmount;
    }
}