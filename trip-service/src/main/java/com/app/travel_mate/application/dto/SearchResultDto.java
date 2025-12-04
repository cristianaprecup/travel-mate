package com.app.travel_mate.application.dto;

import com.app.travel_mate.domain.model.options.*;
import java.util.List;

public record SearchResultDto(
        List<TransportOption> transport,
        List<StayOption> stays,
        List<ActivityOption> activities,
        List<LuggageOption> luggage,
        List<String> warnings
) {}