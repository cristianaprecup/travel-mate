package com.app.travel_mate.search_service.infrastructure.providers;

import com.app.travel_mate.search_service.domain.model.ActivityOption;
import com.app.travel_mate.search_service.domain.model.UserPrefs;

import java.util.List;

public interface ActivityProvider {
    List<ActivityOption> search(UserPrefs prefs);
}
