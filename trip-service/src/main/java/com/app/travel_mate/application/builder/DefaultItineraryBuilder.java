package com.app.travel_mate.application.builder;

import com.app.travel_mate.domain.builder.ItineraryBuilder;
import com.app.travel_mate.domain.model.Itinerary;
import com.app.travel_mate.domain.model.options.ActivityOption;
import com.app.travel_mate.domain.model.options.LuggageOption;
import com.app.travel_mate.domain.model.options.StayOption;
import com.app.travel_mate.domain.model.options.TransportOption;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class DefaultItineraryBuilder implements ItineraryBuilder {

    private List<TransportOption> transports;
    private List<StayOption> stays;
    private List<ActivityOption> activities;
    private List<LuggageOption> luggages;

    public DefaultItineraryBuilder() {
        this.reset();
    }

    @Override
    public void reset() {
        this.transports = new ArrayList<>();
        this.stays = new ArrayList<>();
        this.activities = new ArrayList<>();
        this.luggages = new ArrayList<>();
    }

    @Override
    public void addTransport(TransportOption o) {
        this.transports.add(o);
    }

    @Override
    public void addStay(StayOption o) {
        this.stays.add(o);
    }

    @Override
    public void addActivity(ActivityOption o) {
        this.activities.add(o);
    }

    @Override
    public void addLuggage(LuggageOption o) {
        this.luggages.add(o);
    }

    @Override
    public Itinerary getResult() {
        return new Itinerary(
                List.copyOf(this.transports),
                List.copyOf(this.stays),
                List.copyOf(this.activities),
                List.copyOf(this.luggages)
        );
    }
}