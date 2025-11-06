package com.app.travel_mate.domain.model.queries;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@EqualsAndHashCode
public final class ActivityQuery {

    private final String city;
    private final LocalDateTime dateFrom;
    private final LocalDateTime dateTo;
    private final String category;
    private final int maxPriceAmount;
    private final int minRating;
    private final int maxDurationMinutes;

    public ActivityQuery(String city, LocalDateTime dateFrom, LocalDateTime dateTo,
                         String category, int maxPriceAmount, int minRating,
                         int maxDurationMinutes) {
        this.city = city;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.category = category;
        this.maxPriceAmount = maxPriceAmount;
        this.minRating = minRating;
        this.maxDurationMinutes = maxDurationMinutes;
    }
}