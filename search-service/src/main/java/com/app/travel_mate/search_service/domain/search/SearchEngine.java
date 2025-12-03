package com.app.travel_mate.search_service.domain.search;

import com.app.travel_mate.search_service.domain.model.*;
import com.app.travel_mate.search_service.infrastructure.providers.ProviderRegistry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchEngine {

    private final ProviderRegistry providerRegistry;

    public SearchEngine(ProviderRegistry providerRegistry) {
        this.providerRegistry = providerRegistry;
    }

    public SearchResult search(UserPrefs prefs) {

        List<TransportOption> transport = new ArrayList<>();
        List<StayOption> stays = new ArrayList<>();
        List<ActivityOption> activities = new ArrayList<>();
        List<LuggageOption> luggage = new ArrayList<>();
        List<String> warnings = new ArrayList<>();

        providerRegistry.transportProviders().forEach(p -> {
            try {
                transport.addAll(p.search(prefs));
            } catch (Exception e) {
                warnings.add("Transport provider failed: " + p.getClass().getSimpleName());
            }
        });

        providerRegistry.stayProviders().forEach(p -> {
            try {
                stays.addAll(p.search(prefs));
            } catch (Exception e) {
                warnings.add("Stay provider failed: " + p.getClass().getSimpleName());
            }
        });

        providerRegistry.activityProviders().forEach(p -> {
            try {
                activities.addAll(p.search(prefs));
            } catch (Exception e) {
                warnings.add("Activity provider failed: " + p.getClass().getSimpleName());
            }
        });

        providerRegistry.luggageProviders().forEach(p -> {
            try {
                luggage.addAll(p.search(prefs));
            } catch (Exception e) {
                warnings.add("Luggage provider failed: " + p.getClass().getSimpleName());
            }
        });

        return new SearchResult(transport, stays, activities, luggage, warnings);
    }
}
