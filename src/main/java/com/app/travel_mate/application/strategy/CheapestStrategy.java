package com.app.travel_mate.application.strategy;

import com.app.travel_mate.domain.model.options.ActivityOption;
import com.app.travel_mate.domain.model.options.StayOption;
import com.app.travel_mate.domain.model.options.TransportOption;
import com.app.travel_mate.domain.strategy.RankingStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CheapestStrategy implements RankingStrategy {

    private static final String STRATEGY_NAME = "cheapest";

    @Override
    public int score(TransportOption t, StayOption s, List<ActivityOption> acts) {
        int transportPrice = t.getPriceAmount();
        int stayPrice = s.getPriceAmount();
        int activitiesPrice = acts.stream()
                .mapToInt(ActivityOption::getPriceAmount)
                .sum();

        int totalPrice = transportPrice + stayPrice + activitiesPrice;

        // we want a *higher* score for a *lower* price.
        // A trip costing 300 scores -300.
        // -300 is > -500, so the cheaper trip wins.
        return -totalPrice;
    }

    @Override
    public String getStrategyName() {
        return STRATEGY_NAME;
    }
}