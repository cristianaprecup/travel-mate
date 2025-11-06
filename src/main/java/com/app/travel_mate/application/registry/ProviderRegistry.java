package com.app.travel_mate.application.registry;

import com.app.travel_mate.infrastructure.providers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderRegistry {

    private final List<TransportProvider> transports;
    private final List<AccommodationProvider> lodgings;
    private final List<ActivityProvider> activities;
    private final List<LuggageProvider> luggages;

    @Autowired
    public ProviderRegistry(
            List<TransportProvider> transportProviders,
            List<AccommodationProvider> accommodationProviders,
            List<ActivityProvider> activityProviders,
            List<LuggageProvider> luggageProviders
    ) {
        this.transports = transportProviders;
        this.lodgings = accommodationProviders;
        this.activities = activityProviders;
        this.luggages = luggageProviders;

        System.out.println("Registry Initialized: Found " + transports.size() + " Transport Adapters.");
    }

    public List<TransportProvider> getTransportProviders() {
        return this.transports;
    }

    public List<AccommodationProvider> getAccommodationProviders() {
        return this.lodgings;
    }

    public List<ActivityProvider> getActivityProviders() {
        return this.activities;
    }

    public List<LuggageProvider> getLuggageProviders() {
        return this.luggages;
    }

    public void register(Provider p) {
        System.out.println("Warning: Manual registration ignored. Adapter: " + p.name() + " already loaded by Spring DI.");
    }
}
