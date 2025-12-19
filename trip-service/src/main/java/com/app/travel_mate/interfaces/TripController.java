package com.app.travel_mate.interfaces;

import com.app.travel_mate.application.facade.TravelPlanner;
import com.app.travel_mate.domain.model.Itinerary;
import com.app.travel_mate.domain.model.queries.TripQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.app.travel_mate.mq.TripEventPublisher;
import com.app.travel_mate.mq.TripPlannedEvent;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/api/trip")
public class TripController {

    @Autowired
    private TravelPlanner travelPlanner;

    @Autowired
    private TripEventPublisher tripEventPublisher;

    @PostMapping("/plan")
    public Itinerary planTrip(@RequestBody TripQuery query) {
        System.out.println("Trip Controller: Received new plan request from client.");

        Itinerary itinerary = travelPlanner.planTrip(query);

        TripPlannedEvent event = new TripPlannedEvent(
                UUID.randomUUID().toString(),
                query.getOrigin(),
                query.getDestination(),
                query.getDepartDate().toString(),
                query.getReturnDate().toString(),
                query.getMaxBudget(),
                "transport-placeholder",
                "stay-placeholder",
                Collections.emptyList(),
                LocalDateTime.now()
        );

        tripEventPublisher.publishTripPlanned(event);

        //Decoupling!!!
        return itinerary;
    }

    @PostMapping("/strategy/{name}")
    public void setStrategy(@PathVariable String name) {
        System.out.println("Trip Controller: Setting strategy to " + name);
        travelPlanner.setRankingStrategy(name);
    }
}