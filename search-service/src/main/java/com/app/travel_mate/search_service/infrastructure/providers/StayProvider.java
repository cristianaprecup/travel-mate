package com.app.travel_mate.search_service.infrastructure.providers;

import com.app.travel_mate.search_service.domain.model.StayOption;
import com.app.travel_mate.search_service.domain.model.UserPrefs;

import java.util.List;

public interface StayProvider {
    List<StayOption> search(UserPrefs prefs);
}
