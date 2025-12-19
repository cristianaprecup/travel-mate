package com.app.travel_mate.search_service.domain.model;

import java.time.LocalDateTime;

public record StayOption(
        String id,
        String name,
        String type,
        String address,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        int priceAmount,
        int distanceMeters
) {}
