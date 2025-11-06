package com.app.travel_mate.infrastructure.providers.adapter.luggage;

import com.app.travel_mate.domain.model.options.LuggageOption;
import com.app.travel_mate.domain.model.queries.LuggageQuery;
import com.app.travel_mate.infrastructure.providers.LuggageProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DemoStasherAdapter implements LuggageProvider {

    @Override
    public String name() {
        return "Stasher Demo API";
    }

    @Override
    public List<LuggageOption> search(LuggageQuery query) {
        System.out.println("Adapter: Searching luggage spots via " + name());

        return getMockLuggageData().stream()
                .map(this::convertRawToLuggageOption)
                .collect(Collectors.toList());
    }

    private LuggageOption convertRawToLuggageOption(Map<String, Object> raw) {
        int price = (Integer) raw.get("price_per_hour_usd");

        LocalDateTime dateFrom = LocalDateTime.now().withHour(8).withMinute(0);
        LocalDateTime dateUntil = LocalDateTime.now().withHour(20).withMinute(0);

        return new LuggageOption(
                (String) raw.get("stash_id"),
                (String) raw.get("location_name"),
                (String) raw.get("location_address"),
                (String) raw.get("near_poi"),
                dateFrom,
                dateUntil,
                price,
                (Integer) raw.get("distance_from_query_m")
        );
    }

    private List<Map<String, Object>> getMockLuggageData() {
        return List.of(
                Map.of("stash_id", "ST-LOC-A82", "location_name", "Near CDG Train Station", "location_address", "50 Rue de la Gare, Roissy", "near_poi", "CDG Train Hub", "available_start_time", "06:00", "available_end_time", "23:00", "price_per_hour_usd", 5, "distance_from_query_m", 150),
                Map.of("stash_id", "ST-LOC-B91", "location_name", "Châtelet Laundry Mat", "location_address", "10 Blvd. Sébastopol, Paris", "near_poi", "Châtelet-Les Halles Metro", "available_start_time", "08:00", "available_end_time", "20:00", "price_per_hour_usd", 4, "distance_from_query_m", 50)
        );
    }
}