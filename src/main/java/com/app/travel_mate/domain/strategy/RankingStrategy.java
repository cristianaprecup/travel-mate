package com.app.travel_mate.domain.strategy;

import com.app.travel_mate.domain.model.options.ActivityOption;
import com.app.travel_mate.domain.model.options.StayOption;
import com.app.travel_mate.domain.model.options.TransportOption;
import java.util.List;

public interface RankingStrategy {

    /**
     * calculates a score for a given combination of trip components.
     * a higher score is considered better.
     *
     * @param t    the transport option.
     * @param s    the stay option.
     * @param acts the list of activity options.
     * @return An integer score.
     */
    int score(TransportOption t, StayOption s, List<ActivityOption> acts);

    /**
     * returns the unique name of this strategy (cheapest, fastest).
     * this is used by the Facade to select the active strategy.
     *
     * @return the strategy's name.
     */
    String getStrategyName();
}