package com.app.travel_mate.search_service.domain.model;

public record ActivityOption(
        String provider,     // ex. "DemoGetYourGuide"
        String title,        // activity name
        String city,         // location
        double price         // activity cost
) {}