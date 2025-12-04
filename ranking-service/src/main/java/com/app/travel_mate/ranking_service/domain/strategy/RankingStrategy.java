package com.app.travel_mate.domain.strategy;

import com.app.travel_mate.domain.model.options.ActivityOption;
import com.app.travel_mate.domain.model.options.StayOption;
import com.app.travel_mate.domain.model.options.TransportOption;
import java.util.List;

public interface RankingStrategy {
    int score(TransportOption t, StayOption s, List<ActivityOption> acts);
    String getStrategyName();
}