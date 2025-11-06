package com.app.travel_mate.application.strategy;

import com.app.travel_mate.domain.model.options.ActivityOption;
import com.app.travel_mate.domain.model.options.StayOption;
import com.app.travel_mate.domain.model.options.TransportOption;
import com.app.travel_mate.domain.strategy.RankingStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FastestStrategy implements RankingStrategy {

    private static final String STRATEGY_NAME = "fastest";

    @Override
    public int score(TransportOption t, StayOption s, List<ActivityOption> acts) {
        int totalDuration = t.getDurationMinutes();

        // higher score for lower duration
        return -totalDuration;
    }

    @Override
    public String getStrategyName() {
        return STRATEGY_NAME;
    }
}