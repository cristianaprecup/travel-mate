package com.app.travel_mate.search_service.domain.model;

import com.app.travel_mate.search_service.domain.model.enums.TransportMode;
import java.time.LocalDateTime;

public record TransportOption(
        String id,
        TransportMode mode,
        String operator,
        String origin,
        String destination,
        LocalDateTime departTime,
        LocalDateTime arriveTime,
        int durationMinutes,
        int stops,
        boolean baggageIncluded,
        int priceAmount
) {}
