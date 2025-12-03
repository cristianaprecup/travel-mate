package com.app.travel_mate.search_service.infrastructure.providers;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProviderRegistry {

    private final List<TransportProvider> transportProviders;
    private final List<StayProvider> stayProviders;
    private final List<ActivityProvider> activityProviders;
    private final List<LuggageProvider> luggageProviders;

    public ProviderRegistry(
            List<TransportProvider> transportProviders,
            List<StayProvider> stayProviders,
            List<ActivityProvider> activityProviders,
            List<LuggageProvider> luggageProviders
    ) {
        this.transportProviders = transportProviders;
        this.stayProviders = stayProviders;
        this.activityProviders = activityProviders;
        this.luggageProviders = luggageProviders;
    }

    public List<TransportProvider> transportProviders() { return transportProviders; }
    public List<StayProvider> stayProviders() { return stayProviders; }
    public List<ActivityProvider> activityProviders() { return activityProviders; }
    public List<LuggageProvider> luggageProviders() { return luggageProviders; }
}
