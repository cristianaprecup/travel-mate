package com.app.travel_mate.domain.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
public final class UserPrefs {

    private final String rankingMode;
            private final int budgetAmount;
            private final int maxStops;
            private final int maxWalkMinutes;

    public UserPrefs(String rankingMode, int budgetAmount, int maxStops, int maxWalkMinutes) {
        this.rankingMode = rankingMode;
        this.budgetAmount = budgetAmount;
        this.maxStops = maxStops;
        this.maxWalkMinutes = maxWalkMinutes;
    }
}