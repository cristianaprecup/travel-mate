package com.app.travel_mate.search_service.domain.model;

public record TransportOption(
        String provider,    // ex. "DemoSkyscanner"
        String from,        // departure city
        String to,          // arrival city
        String mode,        // "plane", "train", "bus"
        double price        // total cost
) {}
