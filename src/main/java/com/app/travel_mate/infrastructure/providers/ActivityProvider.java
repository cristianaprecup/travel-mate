package com.app.travel_mate.infrastructure.providers;

import com.app.travel_mate.domain.model.options.ActivityOption;
import com.app.travel_mate.domain.model.queries.ActivityQuery;
import java.util.List;

public interface ActivityProvider extends Provider {

    List<ActivityOption> search(ActivityQuery q);
}