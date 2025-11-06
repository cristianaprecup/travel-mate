package com.app.travel_mate.application.facade;

import com.app.travel_mate.domain.builder.ItineraryBuilder;
import com.app.travel_mate.domain.model.Itinerary;
import com.app.travel_mate.domain.model.options.*;
import com.app.travel_mate.domain.model.queries.*;
import com.app.travel_mate.domain.search.SearchEngine;
import com.app.travel_mate.domain.strategy.RankingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TravelPlanner {

    private final SearchEngine searchEngine;
    private final ItineraryBuilder itineraryBuilder;
    private final Map<String, RankingStrategy> rankingStrategies;

    private RankingStrategy activeStrategy;

    private List<TransportOption> cachedTransports;
    private List<StayOption> cachedStays;
    private List<ActivityOption> cachedActivities;
    private List<LuggageOption> cachedLuggages;

    @Autowired
    public TravelPlanner(SearchEngine searchEngine,
                         ItineraryBuilder itineraryBuilder,
                         List<RankingStrategy> strategies) {

        this.searchEngine = searchEngine;
        this.itineraryBuilder = itineraryBuilder;

        this.rankingStrategies = strategies.stream()
                .collect(Collectors.toMap(RankingStrategy::getStrategyName, Function.identity()));

        this.activeStrategy = this.rankingStrategies.get("cheapest");
        if (this.activeStrategy == null) {
            throw new IllegalStateException("No default 'cheapest' ranking strategy found!");
        }
    }

    public void setRankingStrategy(String strategyName) {
        RankingStrategy newStrategy = rankingStrategies.get(strategyName);
        if (newStrategy != null) {
            this.activeStrategy = newStrategy;
            System.out.println("Active ranking strategy set to: " + strategyName);
        } else {
            throw new IllegalArgumentException("Unknown strategy: " + strategyName);
        }
    }

    public Itinerary planTrip(TripQuery query) {
        System.out.println("--- Starting New Trip Plan ---");

        TransportQuery tq = new TransportQuery(query.getOrigin(), query.getDestination(), query.getDepartDate(), query.getPassengers(), 9999, 9999, 10, true);
        StayQuery sq = new StayQuery(query.getDestination(), query.getDepartDate(), query.getReturnDate(), query.getPassengers(), 9999, 0, 9999);
        ActivityQuery aq = new ActivityQuery(query.getDestination(), query.getDepartDate(), query.getReturnDate(), null, 9999, 0, 9999);

        System.out.println("Searching for transports...");
        this.cachedTransports = searchEngine.searchTransports(tq);
        System.out.println("Found " + cachedTransports.size() + " transports.");

        System.out.println("Searching for stays...");
        this.cachedStays = searchEngine.searchStays(sq);
        System.out.println("Found " + cachedStays.size() + " stays.");

        System.out.println("Searching for activities...");
        this.cachedActivities = searchEngine.searchActivities(aq);
        System.out.println("Found " + cachedActivities.size() + " activities.");

        TransportOption bestTransport = null;
        StayOption bestStay = null;
        int maxScore = Integer.MIN_VALUE;

        if (cachedTransports.isEmpty() || cachedStays.isEmpty()) {
            throw new RuntimeException("Could not find a valid trip combination!");
        }

        for (TransportOption transport : cachedTransports) {
            for (StayOption stay : cachedStays) {
                int currentScore = activeStrategy.score(transport, stay, cachedActivities);

                if (currentScore > maxScore) {
                    maxScore = currentScore;
                    bestTransport = transport;
                    bestStay = stay;
                }
            }
        }

        System.out.println("Scoring complete with strategy: " + activeStrategy.getStrategyName());
        System.out.println("Best combination score: " + maxScore);


        System.out.println("Building itinerary...");
        itineraryBuilder.reset();
        itineraryBuilder.addTransport(bestTransport);
        itineraryBuilder.addStay(bestStay);
        for (ActivityOption activity : cachedActivities) {
            itineraryBuilder.addActivity(activity);
        }

        return itineraryBuilder.getResult();
    }

    public Itinerary getItineraryPreview() {
        System.out.println("--- Regenerating Itinerary Preview ---");

        if (cachedTransports == null || cachedStays == null) {
            throw new IllegalStateException("You must call planTrip() at least once before getting a preview.");
        }

        TransportOption bestTransport = null;
        StayOption bestStay = null;
        int maxScore = Integer.MIN_VALUE;

        for (TransportOption transport : cachedTransports) {
            for (StayOption stay : cachedStays) {
                int currentScore = activeStrategy.score(transport, stay, cachedActivities);
                if (currentScore > maxScore) {
                    maxScore = currentScore;
                    bestTransport = transport;
                    bestStay = stay;
                }
            }
        }

        if (bestTransport == null) {
            throw new RuntimeException("Could not find a valid trip combination!");
        }

        System.out.println("Re-scoring complete with strategy: " + activeStrategy.getStrategyName());
        System.out.println("New best combination score: " + maxScore);

        System.out.println("Building new itinerary preview...");
        itineraryBuilder.reset();
        itineraryBuilder.addTransport(bestTransport);
        itineraryBuilder.addStay(bestStay);
        for (ActivityOption activity : cachedActivities) {
            itineraryBuilder.addActivity(activity);
        }

        return itineraryBuilder.getResult();
    }
}