package com.app.travel_mate.application.facade;

import com.app.travel_mate.domain.builder.ItineraryBuilder;
import com.app.travel_mate.domain.model.Itinerary;
import com.app.travel_mate.domain.model.options.*;
import com.app.travel_mate.domain.model.queries.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class TravelPlanner {

    private final RestTemplate restTemplate;
    private final ItineraryBuilder itineraryBuilder;

    @Value("${search.service.url}")
    private String searchServiceUrl;

    @Value("${ranking.service.url}")
    private String rankingServiceUrl;

    private String activeStrategy = "cheapest"; // default

    @Autowired
    public TravelPlanner(RestTemplate restTemplate, ItineraryBuilder itineraryBuilder) {
        this.restTemplate = restTemplate;
        this.itineraryBuilder = itineraryBuilder;
    }

    public void setRankingStrategy(String strategyName) {
        this.activeStrategy = strategyName;
    }

    public Itinerary planTrip(TripQuery query) {
        System.out.println("--- Trip Service: Orchestrating Plan ---");

        TransportQuery tq = new TransportQuery(query.getOrigin(), query.getDestination(), query.getDepartDate(), query.getPassengers(), 9999, 9999, 10, true);
        StayQuery sq = new StayQuery(query.getDestination(), query.getDepartDate(), query.getReturnDate(), query.getPassengers(), 9999, 0, 9999);
        ActivityQuery aq = new ActivityQuery(query.getDestination(), query.getDepartDate(), query.getReturnDate(), null, 9999, 0, 9999);

        System.out.println("Calling Search Service at: " + searchServiceUrl);

        TransportOption[] transports = restTemplate.postForObject(searchServiceUrl + "/transport", tq, TransportOption[].class);
        StayOption[] stays = restTemplate.postForObject(searchServiceUrl + "/stay", sq, StayOption[].class);
        ActivityOption[] activities = restTemplate.postForObject(searchServiceUrl + "/activity", aq, ActivityOption[].class);

        if (transports == null || transports.length == 0) throw new RuntimeException("No flights found!");
        if (stays == null || stays.length == 0) throw new RuntimeException("No hotels found!");

        TransportOption candidateTransport = transports[0];
        StayOption candidateStay = stays[0];
        List<ActivityOption> candidateActivities = (activities != null) ? Arrays.asList(activities) : List.of();

        TripCandidate candidate = new TripCandidate();
        candidate.transport = candidateTransport;
        candidate.stay = candidateStay;
        candidate.activities = candidateActivities;

        System.out.println("Calling Ranking Service at: " + rankingServiceUrl + "/" + activeStrategy);

        Integer score = restTemplate.postForObject(rankingServiceUrl + "/" + activeStrategy, candidate, Integer.class);

        System.out.println("Trip Service: Received Score from remote service: " + score);

        itineraryBuilder.reset();
        itineraryBuilder.addTransport(candidateTransport);
        itineraryBuilder.addStay(candidateStay);
        for (ActivityOption a : candidateActivities) {
            itineraryBuilder.addActivity(a);
        }

        return itineraryBuilder.getResult();
    }

    private static class TripCandidate {
        public TransportOption transport;
        public StayOption stay;
        public List<ActivityOption> activities;
    }
}