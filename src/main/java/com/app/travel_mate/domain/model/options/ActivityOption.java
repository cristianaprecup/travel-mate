package com.app.travel_mate.domain.model.options;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public final class ActivityOption {

    private final String id;
    private final String name;
    private final String category;
    private final String location;
    private final String openTime;
    private final String closeTime;
    private final int typicalDurationMinutes;
    private final int priceAmount;

    public ActivityOption(String id, String name, String category, String location,
                          String openTime, String closeTime,
                          int typicalDurationMinutes, int priceAmount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.location = location;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.typicalDurationMinutes = typicalDurationMinutes;
        this.priceAmount = priceAmount;
    }
}