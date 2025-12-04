package com.app.travel_mate.ranking_service.strategy;

import com.app.travel_mate.domain.model.options.ActivityOption;
import com.app.travel_mate.domain.model.options.StayOption;
import com.app.travel_mate.domain.model.options.TransportOption;
import com.app.travel_mate.domain.strategy.RankingStrategy; // <--- Import DOMAIN interface
import org.springframework.stereotype.Component;

import java.util.List;

@Component("fastest")
public class FastestRankingStrategy implements RankingStrategy {

    @Override
    public int score(TransportOption t, StayOption s, List<ActivityOption> acts) {
        return -t.getDurationMinutes();
    }

    @Override
    public String getStrategyName() {
        return "fastest";
    }
}