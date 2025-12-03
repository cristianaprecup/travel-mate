package com.app.travel_mate.search_service.domain.model;

import java.util.List;

public record SearchResult(
        List<TransportOption> transportOptions,
        List<StayOption> stayOptions,
        List<ActivityOption> activityOptions,
        List<LuggageOption> luggageOptions,
        List<String> warnings
) {}

