package com.app.travel_mate.interfaces;

import com.app.travel_mate.application.facade.TravelPlanner;
import com.app.travel_mate.domain.model.Itinerary;
import com.app.travel_mate.domain.model.queries.TripQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trip")
public class TripController {

    @Autowired
    private TravelPlanner travelPlanner;

    @PostMapping("/plan")
    public Itinerary planTrip(@RequestBody TripQuery query) {
        System.out.println("Trip Controller: Received new plan request from client.");
        return travelPlanner.planTrip(query);
    }

    @PostMapping("/strategy/{name}")
    public void setStrategy(@PathVariable String name) {
        System.out.println("Trip Controller: Setting strategy to " + name);
        travelPlanner.setRankingStrategy(name);
    }
}