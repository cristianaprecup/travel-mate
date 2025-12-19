package com.app.travel_mate.domain.model;

import com.app.travel_mate.domain.model.options.ActivityOption;
import com.app.travel_mate.domain.model.options.LuggageOption;
import com.app.travel_mate.domain.model.options.StayOption;
import com.app.travel_mate.domain.model.options.TransportOption;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@EqualsAndHashCode
public final class Itinerary {

    private final List<TransportOption> transports;
    private final List<StayOption> stays;
    private final List<ActivityOption> activities;
    private final List<LuggageOption> luggages;

    public Itinerary(List<TransportOption> transports, List<StayOption> stays,
                     List<ActivityOption> activities, List<LuggageOption> luggages) {
        // might wrap these in Collections.unmodifiableList() tbd
        this.transports = transports;
        this.stays = stays;
        this.activities = activities;
        this.luggages = luggages;
    }

    @Override
    public String toString() {
        String transportDesc = transports.isEmpty() ? "  None" :
                "  " + transports.stream()
                        .map(t -> t.getMode() + " with " + t.getOperator() + " (Price: " + t.getPriceAmount() + ")")
                        .collect(Collectors.joining("\n  "));

        String stayDesc = stays.isEmpty() ? "  None" :
                "  " + stays.stream()
                        .map(s -> s.getName() + " (Price: " + s.getPriceAmount() + ")")
                        .collect(Collectors.joining("\n  "));

        String activityDesc = activities.isEmpty() ? "  None" :
                "  " + activities.stream()
                        .map(a -> a.getName() + " (Price: " + a.getPriceAmount() + ")")
                        .collect(Collectors.joining("\n  "));

        return "--- FINAL ITINERARY ---" +
                "\n[TRANSPORT]" +
                "\n" + transportDesc +
                "\n[STAY]" +
                "\n" + stayDesc +
                "\n[ACTIVITIES]" +
                "\n" + activityDesc +
                "\n-------------------------";
    }
}