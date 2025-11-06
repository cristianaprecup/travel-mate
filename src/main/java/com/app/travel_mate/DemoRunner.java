package com.app.travel_mate;

import com.app.travel_mate.application.facade.TravelPlanner;
import com.app.travel_mate.domain.model.Itinerary;
import com.app.travel_mate.domain.model.queries.TripQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DemoRunner implements CommandLineRunner {

    @Autowired
    private TravelPlanner travelPlanner;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("TRAVELMATE DEMO:");

        TripQuery query = new TripQuery(
                "OTP", // Origin
                "CDG", // Destination
                LocalDateTime.of(2025, 12, 10, 10, 0),
                LocalDateTime.of(2025, 12, 15, 18, 0),
                1
        );

        System.out.println("\n--- planTrip() with cheapest strategy ---");
        Itinerary cheapItinerary = null;
        try {
            cheapItinerary = travelPlanner.planTrip(query);


            System.out.println("SUCCESS: Generated Itinerary:");

            System.out.println(cheapItinerary);

        } catch (Exception e) {
            System.err.println("Error during planTrip: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n--- setRankingStrategy('fastest') ---");

        travelPlanner.setRankingStrategy("fastest");
        Itinerary fastItinerary = null;

        try {
            fastItinerary = travelPlanner.getItineraryPreview();

            System.out.println("SUCCESS: Generated Itinerary");

            System.out.println(fastItinerary);

        } catch (Exception e) {
            System.err.println("Error during getItineraryPreview: " + e.getMessage());
            e.printStackTrace();
        }
    }
}