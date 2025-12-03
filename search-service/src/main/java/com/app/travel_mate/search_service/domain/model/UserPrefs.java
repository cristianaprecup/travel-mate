package com.app.travel_mate.search_service.domain.model;

public record UserPrefs(
        String from,
        String to,
        String startDate,
        String endDate,
        double maxBudget
) {}
