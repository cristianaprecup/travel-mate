package com.app.travel_mate.application.facade;

import com.app.travel_mate.application.dto.SearchRequestDto;
import com.app.travel_mate.application.dto.SearchResultDto;
import com.app.travel_mate.domain.builder.ItineraryBuilder;
import com.app.travel_mate.domain.model.Itinerary;
import com.app.travel_mate.domain.model.options.*;
import com.app.travel_mate.domain.model.queries.TripQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class TravelPlanner {

    private final RestTemplate restTemplate;
    private final ItineraryBuilder itineraryBuilder;

    @Value("${search.service.url}")
    private String searchServiceUrl;

    @Value("${ranking.service.url}")
    private String rankingServiceUrl;

    private String activeStrategy = "cheapest";

    @Autowired
    public TravelPlanner(RestTemplate restTemplate, ItineraryBuilder itineraryBuilder) {
        this.restTemplate = restTemplate;
        this.itineraryBuilder = itineraryBuilder;
    }

    public void setRankingStrategy(String strategyName) {
        this.activeStrategy = strategyName;
        System.out.println("Strategy changed to: " + this.activeStrategy);
    }

    public Itinerary planTrip(TripQuery query) {
        System.out.println("\n=================================================");
        System.out.println("--- Trip Service: Orchestrating Plan START ---");
        System.out.println("Query received for: " + query.getOrigin() + " -> " + query.getDestination());

        double budget = (query.getMaxBudget() != null) ? query.getMaxBudget().doubleValue() : 10000.0;

        SearchRequestDto searchRequest = new SearchRequestDto(
                query.getOrigin(),
                query.getDestination(),
                query.getDepartDate().toString(),
                query.getReturnDate().toString(),
                budget
        );
        System.out.println("Step 1: Created SearchRequestDto with budget: " + budget);

        System.out.println("Step 2: Calling Unified Search Service at: " + searchServiceUrl);

        SearchResultDto searchResult = null;
        try {
            searchResult = restTemplate.postForObject(
                    searchServiceUrl,
                    searchRequest,
                    SearchResultDto.class
            );
            System.out.println("Step 2: Search Service responded successfully.");
        } catch (Exception e) {
            System.err.println("Step 2 ERROR: Failed to call search service. " + e.getMessage());
            throw new RuntimeException("Search service failure: " + e.getMessage());
        }

        if (searchResult == null) {
            throw new RuntimeException("Search service returned null response");
        }

        List<TransportOption> transports = searchResult.transport();
        List<StayOption> stays = searchResult.stays();
        List<ActivityOption> activities = searchResult.activities();

        System.out.println("Step 3: Unpacked results.");
        System.out.println(" - Transports found: " + (transports != null ? transports.size() : 0));
        System.out.println(" - Stays found: " + (stays != null ? stays.size() : 0));
        System.out.println(" - Activities found: " + (activities != null ? activities.size() : 0));

        if (transports == null || transports.isEmpty()) {
            throw new RuntimeException("No flights found!");
        }

        TransportOption candidateTransport = transports.get(0);
        StayOption candidateStay = (stays != null && !stays.isEmpty()) ? stays.get(0) : null;
        List<ActivityOption> candidateActivities = (activities != null) ? activities : Collections.emptyList();

        if (candidateStay != null) {
            TripCandidate candidate = new TripCandidate();
            candidate.transport = candidateTransport;
            candidate.stay = candidateStay;
            candidate.activities = candidateActivities;

            System.out.println("Step 4: Calling Ranking Service at: " + rankingServiceUrl + "/" + activeStrategy);
            try {
                Integer score = restTemplate.postForObject(rankingServiceUrl + "/" + activeStrategy, candidate, Integer.class);
                System.out.println("Step 4: Ranking Score received: " + score);
            } catch (Exception e) {
                System.err.println("Step 4 WARNING: Ranking service failed, continuing without score. Error: " + e.getMessage());
            }
        } else {
            System.out.println("Step 4 Skipped: No stay available to rank.");
        }

        System.out.println("Step 5: Building final Itinerary...");
        itineraryBuilder.reset();
        itineraryBuilder.addTransport(candidateTransport);
        if (candidateStay != null) {
            itineraryBuilder.addStay(candidateStay);
        }
        for (ActivityOption a : candidateActivities) {
            itineraryBuilder.addActivity(a);
        }

        Itinerary result = itineraryBuilder.getResult();
        System.out.println("--- Trip Service: Orchestrating Plan COMPLETE ---");
        System.out.println("=================================================\n");

        return result;
    }

    private static class TripCandidate {
        public TransportOption transport;
        public StayOption stay;
        public List<ActivityOption> activities;
        public TransportOption getTransport() { return transport; }
        public void setTransport(TransportOption transport) { this.transport = transport; }
        public StayOption getStay() { return stay; }
        public void setStay(StayOption stay) { this.stay = stay; }
        public List<ActivityOption> getActivities() { return activities; }
        public void setActivities(List<ActivityOption> activities) { this.activities = activities; }
    }
}