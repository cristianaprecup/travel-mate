package com.app.travel_mate.search_service.domain.model;

public record ActivityOption(
        String id,
        String name,
        String category,
        String location,
        String openTime,
        String closeTime,
        int typicalDurationMinutes,
        int priceAmount
) {}