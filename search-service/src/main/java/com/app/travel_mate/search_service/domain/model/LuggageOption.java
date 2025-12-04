package com.app.travel_mate.search_service.domain.model;

import java.time.LocalDateTime;

public record LuggageOption(
        String id, String name,
        String address,
        String near,
        LocalDateTime availableFrom,
        LocalDateTime availableUntil,
        int pricePerHour,
        int distanceMeters
) {}
