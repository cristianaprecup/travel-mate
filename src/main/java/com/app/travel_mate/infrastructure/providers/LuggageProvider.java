package com.app.travel_mate.infrastructure.providers;

import com.app.travel_mate.domain.model.options.LuggageOption;
import com.app.travel_mate.domain.model.queries.LuggageQuery;
import java.util.List;

public interface LuggageProvider extends Provider {

    List<LuggageOption> search(LuggageQuery q);
}