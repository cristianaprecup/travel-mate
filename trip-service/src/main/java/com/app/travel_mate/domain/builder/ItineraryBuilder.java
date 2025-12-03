package com.app.travel_mate.domain.builder;

import com.app.travel_mate.domain.model.Itinerary;
import com.app.travel_mate.domain.model.options.ActivityOption;
import com.app.travel_mate.domain.model.options.LuggageOption;
import com.app.travel_mate.domain.model.options.StayOption;
import com.app.travel_mate.domain.model.options.TransportOption;

// builder, defines the steps to build Itinerary
public interface ItineraryBuilder {

    // clears the builder, called by facade before building
    void reset();

    void addTransport(TransportOption o);

    void addStay(StayOption o);

    void addActivity(ActivityOption o);

    // optional
    void addLuggage(LuggageOption o);

    Itinerary getResult();
}