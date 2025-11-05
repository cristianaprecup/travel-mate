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

// Implements the Facade design pattern.
// It provides a simple interface
// that hides the logic of coordinating the SearchEngine, RankingStrategy, and ItineraryBuilder
@Service
public class TravelPlanner {

    private final SearchEngine searchEngine;

    private final ItineraryBuilder itineraryBuilder;

    //  find all beans that implement the 'RankingStrategy' interface
    //  create a Map<String, RankingStrategy>
    //  put each strategy bean into the map
    //  use the bean's 'name' as the key
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


// implements the 'setRankingStrategy' method from the UC-2 diagram.
    public void setRankingStrategy(String strategyName) {
        RankingStrategy newStrategy = rankingStrategies.get(strategyName);
        if (newStrategy != null) {
            this.activeStrategy = newStrategy;
            System.out.println("Active ranking strategy set to: " + strategyName);
        } else {
            throw new IllegalArgumentException("Unknown strategy: " + strategyName);
        }
    }

    // UC-1 sequence diagram -> facade
    public Itinerary planTrip(TripQuery query) {
        System.out.println("--- Starting New Trip Plan ---");

        // convert TripQuery into specific queries
        TransportQuery tq = new TransportQuery(query.getOrigin(), query.getDestination(), query.getDepartDate(), query.getPassengers(), 9999, 9999, 10, true);
        StayQuery sq = new StayQuery(query.getDestination(), query.getDepartDate(), query.getReturnDate(), query.getPassengers(), 9999, 0, 9999);
        ActivityQuery aq = new ActivityQuery(query.getDestination(), query.getDepartDate(), query.getReturnDate(), null, 9999, 0, 9999);

        // cll SearchEngine to get all options
        System.out.println("Searching for transports...");
        this.cachedTransports = searchEngine.searchTransports(tq);
        System.out.println("Found " + cachedTransports.size() + " transports.");

        System.out.println("Searching for stays...");
        this.cachedStays = searchEngine.searchStays(sq);
        System.out.println("Found " + cachedStays.size() + " stays.");

        System.out.println("Searching for activities...");
        this.cachedActivities = searchEngine.searchActivities(aq);
        System.out.println("Found " + cachedActivities.size() + " activities.");

        // luggage search is optional

        // score and find the best combination
        TransportOption bestTransport = cachedTransports.stream().findFirst().orElse(null);
        StayOption bestStay = cachedStays.stream().findFirst().orElse(null);

        if (bestTransport == null || bestStay == null) {
            throw new RuntimeException("Could not find a valid trip combination!");
        }

        System.out.println("Scoring combination with strategy: " + activeStrategy.getStrategyName());
        int score = activeStrategy.score(bestTransport, bestStay, cachedActivities);
        System.out.println("Combination score: " + score);


        // build the Itinerary
        System.out.println("Building itinerary...");
        itineraryBuilder.reset();
        itineraryBuilder.addTransport(bestTransport);
        itineraryBuilder.addStay(bestStay);
        for (ActivityOption activity : cachedActivities) {
            itineraryBuilder.addActivity(activity);
        }

        // return the final product
        return itineraryBuilder.getResult();
    }

    // UC-2 sequence diagram.
    // re-uses the results from planTrip() but re-rank them using the current activeStrategy.
    public Itinerary getItineraryPreview() {
        System.out.println("--- Regenerating Itinerary Preview ---");

        if (cachedTransports == null || cachedStays == null) {
            throw new IllegalStateException("You must call planTrip() at least once before getting a preview.");
        }

        // re-score combinations
        TransportOption bestTransport = cachedTransports.get(0);
        StayOption bestStay = cachedStays.get(0);

        System.out.println("Re-scoring combination with strategy: " + activeStrategy.getStrategyName());
        int score = activeStrategy.score(bestTransport, bestStay, cachedActivities);
        System.out.println("New combination score: " + score);

        // build the Itinerary
        System.out.println("Building new itinerary preview...");
        itineraryBuilder.reset();
        itineraryBuilder.addTransport(bestTransport);
        itineraryBuilder.addStay(bestStay);
        for (ActivityOption activity : cachedActivities) {
            itineraryBuilder.addActivity(activity);
        }

        // return the new Itinerary
        return itineraryBuilder.getResult();
    }
}