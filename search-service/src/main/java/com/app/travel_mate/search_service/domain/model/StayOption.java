package com.app.travel_mate.search_service.domain.model;

public record StayOption(
        String provider,       // ex. "DemoBooking"
        String hotelName,      // name of the stay location
        String city,           // city of the accommodation
        double pricePerNight,  // cost per night
        double rating          // user rating (1-5)
) {}
