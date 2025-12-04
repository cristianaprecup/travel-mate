package com.app.travel_mate.application.dto;

public record SearchRequestDto(
        String from,
        String to,
        String startDate,
        String endDate,
        double maxBudget
) {}