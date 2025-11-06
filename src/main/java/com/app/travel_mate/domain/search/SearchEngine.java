package com.app.travel_mate.domain.search;

import com.app.travel_mate.domain.model.options.*;
import com.app.travel_mate.domain.model.queries.*;
import com.app.travel_mate.infrastructure.providers.*;
import com.app.travel_mate.application.registry.ProviderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchEngine {

    private final ProviderRegistry registry;

    @Autowired
    public SearchEngine(ProviderRegistry registry) {
        this.registry = registry;
    }

    // UC-1 seq diageam -> get all TransportProviders, call search on each one, collect the result in a list
    public List<TransportOption> searchTransports(TransportQuery q) {
        List<TransportProvider> providers = registry.getTransportProviders();

        return providers.stream()
                .map(provider -> provider.search(q))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<StayOption> searchStays(StayQuery q) {
        return registry.getAccommodationProviders().stream()
                .map(provider -> provider.search(q))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<ActivityOption> searchActivities(ActivityQuery q) {
        return registry.getActivityProviders().stream()
                .map(provider -> provider.search(q))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<LuggageOption> searchLuggage(LuggageQuery q) {
        return registry.getLuggageProviders().stream()
                .map(provider -> provider.search(q))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }
}