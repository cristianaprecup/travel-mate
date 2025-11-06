package com.app.travel_mate.infrastructure.providers;

import com.app.travel_mate.domain.model.options.StayOption;
import com.app.travel_mate.domain.model.queries.StayQuery;
import java.util.List;

public interface AccommodationProvider extends Provider {

    List<StayOption> search(StayQuery q);
}