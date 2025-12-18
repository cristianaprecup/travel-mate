package com.app.travel_mate.ranking_service.controller;

import com.app.travel_mate.domain.model.options.ActivityOption;
import com.app.travel_mate.domain.model.options.StayOption;
import com.app.travel_mate.domain.model.options.TransportOption;
import com.app.travel_mate.domain.strategy.RankingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rank")
public class RankingController {

    private final Map<String, RankingStrategy> strategies;

    @Autowired
    public RankingController(List<RankingStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(RankingStrategy::getStrategyName, Function.identity()));
    }

    @PostMapping("/{strategyName}")
    public int scoreTrip(@PathVariable String strategyName, @RequestBody TripCandidate candidate) {

        RankingStrategy strategy = strategies.get(strategyName);

        if (strategy == null) {
            System.out.println("Strategy not found: " + strategyName + ". Using default: cheapest.");
            strategy = strategies.get("cheapest");
        }

        if (strategy == null) {
            throw new RuntimeException("No strategies loaded! Check your bean configuration.");
        }

        System.out.println("Ranking Service: Scoring with strategy: " + strategy.getStrategyName());

        return strategy.score(candidate.transport, candidate.stay, candidate.activities);
    }

    public static class TripCandidate {
        public TransportOption transport;
        public StayOption stay;
        public List<ActivityOption> activities;
    }

    @GetMapping("/health")
    public String health() {
        return "rank-service OK";
    }
}