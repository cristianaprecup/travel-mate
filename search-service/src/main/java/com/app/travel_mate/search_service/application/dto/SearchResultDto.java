package com.app.travel_mate.search_service.application.dto;

import com.app.travel_mate.search_service.domain.model.ActivityOption;
import com.app.travel_mate.search_service.domain.model.LuggageOption;
import com.app.travel_mate.search_service.domain.model.SearchResult;
import com.app.travel_mate.search_service.domain.model.StayOption;
import com.app.travel_mate.search_service.domain.model.TransportOption;

import java.util.List;

public record SearchResultDto(
        List<TransportOption> transport,
        List<StayOption> stays,
        List<ActivityOption> activities,
        List<LuggageOption> luggage,
        List<String> warnings
) {
    public static SearchResultDto fromDomain(SearchResult result) {
        return new SearchResultDto(
                result.transportOptions(),
                result.stayOptions(),
                result.activityOptions(),
                result.luggageOptions(),
                result.warnings()
        );
    }
}


