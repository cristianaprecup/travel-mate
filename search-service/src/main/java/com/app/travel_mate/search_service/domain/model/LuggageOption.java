package com.app.travel_mate.search_service.domain.model;

public record LuggageOption(
        String provider,     // ex. "DemoStasher"
        String location,     // luggage drop-off location
        double pricePerDay   // cost per day
) {}
