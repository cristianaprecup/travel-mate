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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.app.travel_mate.mq.TripEventPublisher;


@Service
public class TravelPlanner {

    private final RestTemplate restTemplate;
    private final ItineraryBuilder itineraryBuilder;

    @Value("${search.service.url}")
    private String searchServiceUrl;

    @Value("${ranking.service.url}")
    private String rankingServiceUrl;

    private String activeStrategy = "cheapest";

    private static final int MAX_TRANSPORTS_TO_CONSIDER = 5;
    private static final int MAX_STAYS_TO_CONSIDER = 5;

    private final TripEventPublisher eventPublisher;

    @Autowired
    public TravelPlanner(RestTemplate restTemplate, ItineraryBuilder itineraryBuilder, TripEventPublisher eventPublisher) {
        this.restTemplate = restTemplate;
        this.itineraryBuilder = itineraryBuilder;
        this.eventPublisher = eventPublisher;
    }

    public void setRankingStrategy(String strategyName) {
        this.activeStrategy = strategyName;
        System.out.println("Strategy changed to: " + this.activeStrategy);
    }

    public Itinerary planTrip(TripQuery query) {
        System.out.println("\n=================================================");
        System.out.println("--- Trip Service: Orchestrating Plan START ---");
        System.out.println("Query received for: " + query.getOrigin() + " -> " + query.getDestination());

        int budget = (query.getMaxBudget() != null) ? query.getMaxBudget() : Integer.MAX_VALUE;

        SearchRequestDto searchRequest = new SearchRequestDto(
                query.getOrigin(),
                query.getDestination(),
                query.getDepartDate().toString(),
                query.getReturnDate().toString(),
                budget
        );
        System.out.println("Step 1: Created SearchRequestDto with budget: " + budget);

        System.out.println("Step 2: Calling Unified Search Service at: " + searchServiceUrl);

        SearchResultDto searchResult;
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
            throw new RuntimeException("No transport options found!");
        }

        transports = transports != null ? transports : Collections.emptyList();
        stays = stays != null ? stays : Collections.emptyList();
        activities = activities != null ? activities : Collections.emptyList();

        List<TransportOption> candidateTransports = transports.stream()
                .sorted(Comparator.comparingInt(TransportOption::getPriceAmount))
                .limit(MAX_TRANSPORTS_TO_CONSIDER)
                .collect(Collectors.toList());

        List<StayOption> candidateStays = stays.stream()
                .sorted(Comparator.comparingInt(StayOption::getPriceAmount))
                .limit(MAX_STAYS_TO_CONSIDER)
                .collect(Collectors.toList());

        List<ActivityOption> candidateActivities = activities;

        System.out.println("Step 4: Evaluating combinations with Ranking Service...");
        System.out.println(" - Using up to " + candidateTransports.size() + " transports");
        System.out.println(" - Using up to " + candidateStays.size() + " stays");
        System.out.println(" - Activities per candidate: " + candidateActivities.size());

        TripCandidate bestCandidate = null;
        int bestScore = Integer.MIN_VALUE;

        if (candidateStays.isEmpty()) {
            System.out.println("Step 4: No stays available. Falling back to cheapest transport only.");
            TransportOption cheapest = candidateTransports.stream()
                    .min(Comparator.comparingInt(TransportOption::getPriceAmount))
                    .orElse(candidateTransports.get(0));

            bestCandidate = new TripCandidate();
            bestCandidate.transport = cheapest;
            bestCandidate.stay = null;
            bestCandidate.activities = candidateActivities;
        } else {
            int combinationsTried = 0;

            for (TransportOption t : candidateTransports) {
                for (StayOption s : candidateStays) {

                    int totalPrice = calculateTotalPrice(t, s, candidateActivities);
                    if (totalPrice > budget) {
                        System.out.println(" - Skipping combo (budget exceeded): "
                                + t.getId() + " + " + s.getId()
                                + " totalPrice=" + totalPrice);
                        continue;
                    }

                    TripCandidate candidate = new TripCandidate();
                    candidate.transport = t;
                    candidate.stay = s;
                    candidate.activities = candidateActivities;

                    Integer score = null;
                    try {
                        score = restTemplate.postForObject(
                                rankingServiceUrl + "/" + activeStrategy,
                                candidate,
                                Integer.class
                        );
                        combinationsTried++;
                        System.out.println("   Combo #" + combinationsTried + " "
                                + t.getId() + " + " + s.getId()
                                + " -> score=" + score);
                    } catch (Exception e) {
                        System.err.println("Step 4 WARNING: Ranking service failed for combo "
                                + t.getId() + " + " + s.getId()
                                + ". Error: " + e.getMessage());
                    }

                    if (score != null && score > bestScore) {
                        bestScore = score;
                        bestCandidate = candidate;
                    }
                }
            }

            if (bestCandidate == null) {
                System.out.println("Step 4: No valid combination scored by ranking. Falling back to cheapest combo.");
                bestCandidate = pickCheapestCombination(candidateTransports, candidateStays, candidateActivities, budget);
            } else {
                System.out.println("Step 4: Best score found: " + bestScore);
            }
        }

        System.out.println("Step 5: Building final Itinerary...");
        itineraryBuilder.reset();

        if (bestCandidate != null) {
            if (bestCandidate.transport != null) {
                itineraryBuilder.addTransport(bestCandidate.transport);
            }
            if (bestCandidate.stay != null) {
                itineraryBuilder.addStay(bestCandidate.stay);
            }
            if (bestCandidate.activities != null) {
                for (ActivityOption a : bestCandidate.activities) {
                    itineraryBuilder.addActivity(a);
                }
            }
        }

        Itinerary result = itineraryBuilder.getResult();
        System.out.println("--- Trip Service: Orchestrating Plan COMPLETE ---");
        System.out.println(result);
        System.out.println("=================================================\n");

        return result;
    }

    private TripCandidate pickCheapestCombination(List<TransportOption> transports,
                                                  List<StayOption> stays,
                                                  List<ActivityOption> activities,
                                                  int budget) {
        TripCandidate best = null;
        int bestPrice = Integer.MAX_VALUE;

        for (TransportOption t : transports) {
            for (StayOption s : stays) {
                int total = calculateTotalPrice(t, s, activities);
                if (total <= budget && total < bestPrice) {
                    bestPrice = total;
                    TripCandidate c = new TripCandidate();
                    c.transport = t;
                    c.stay = s;
                    c.activities = activities;
                    best = c;
                }
            }
        }

        // dacă niciuna nu intră în buget, ia cea mai ieftină oricum
        if (best == null && !transports.isEmpty() && !stays.isEmpty()) {
            TransportOption cheapestT = transports.stream()
                    .min(Comparator.comparingInt(TransportOption::getPriceAmount))
                    .orElse(transports.get(0));
            StayOption cheapestS = stays.stream()
                    .min(Comparator.comparingInt(StayOption::getPriceAmount))
                    .orElse(stays.get(0));

            best = new TripCandidate();
            best.transport = cheapestT;
            best.stay = cheapestS;
            best.activities = activities;
        }

        return best;
    }

    private int calculateTotalPrice(TransportOption t, StayOption s, List<ActivityOption> acts) {
        int transportPrice = t != null ? t.getPriceAmount() : 0;
        int stayPrice = s != null ? s.getPriceAmount() : 0;
        int activitiesPrice = acts != null
                ? acts.stream().mapToInt(ActivityOption::getPriceAmount).sum()
                : 0;

        return transportPrice + stayPrice + activitiesPrice;
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
