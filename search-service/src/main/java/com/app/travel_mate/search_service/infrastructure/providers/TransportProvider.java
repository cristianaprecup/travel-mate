package com.app.travel_mate.search_service.infrastructure.providers;

import com.app.travel_mate.search_service.domain.model.TransportOption;
import com.app.travel_mate.search_service.domain.model.UserPrefs;

import java.util.List;

public interface TransportProvider {
    List<TransportOption> search(UserPrefs prefs);
}
