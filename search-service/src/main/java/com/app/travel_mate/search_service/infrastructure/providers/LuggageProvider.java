package com.app.travel_mate.search_service.infrastructure.providers;

import com.app.travel_mate.search_service.domain.model.LuggageOption;
import com.app.travel_mate.search_service.domain.model.UserPrefs;

import java.util.List;

public interface LuggageProvider {
    List<LuggageOption> search(UserPrefs prefs);
}
