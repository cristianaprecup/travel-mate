package com.app.travel_mate.mq;

import java.time.LocalDateTime;
import java.util.List;

public record TripPlannedEvent(
        String eventId,
        String origin,
        String destination,
        String departDate,
        String returnDate,
        Integer maxBudget,
        String chosenTransportId,
        String chosenStayId,
        List<String> chosenActivityIds,
        LocalDateTime createdAt
) {

}
