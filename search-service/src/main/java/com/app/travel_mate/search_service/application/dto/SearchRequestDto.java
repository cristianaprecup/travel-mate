package com.app.travel_mate.search_service.application.dto;

public record SearchRequestDto(
        String from,
        String to,
        String startDate,
        String endDate,
        double maxBudget
) {}
