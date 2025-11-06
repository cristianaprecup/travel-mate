package com.app.travel_mate.infrastructure.providers.adapter.activity;

import com.app.travel_mate.domain.model.options.ActivityOption;
import com.app.travel_mate.domain.model.queries.ActivityQuery;
import com.app.travel_mate.infrastructure.providers.ActivityProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DemoGetYourGuideAdapter implements ActivityProvider {

    @Override
    public String name() {
        return "GetYourGuide Demo API";
    }

    @Override
    public List<ActivityOption> search(ActivityQuery query) {
        System.out.println("Adapter: Searching activities via " + name());
        return getMockActivityData().stream()
                .map(this::convertRawToActivityOption)
                .collect(Collectors.toList());
    }

    private ActivityOption convertRawToActivityOption(Map<String, Object> raw) {
        int durationMinutes = (Integer) raw.get("avg_duration_hours") * 60;
        int price = (Integer) raw.get("cost_per_person_eur");

        return new ActivityOption(
                (String) raw.get("tour_code"),
                (String) raw.get("activity_name"),
                (String) raw.get("activity_category"),
                (String) raw.get("venue_location"),
                (String) raw.get("daily_open"),
                (String) raw.get("daily_close"),
                durationMinutes,
                price
        );
    }

    private List<Map<String, Object>> getMockActivityData() {
        return List.of(
                Map.of("tour_code", "GYG-PRS-301", "activity_name", "Eiffel Tower Guided Climb", "activity_category", "Tour", "venue_location", "Champ de Mars, Paris", "daily_open", "09:00", "daily_close", "18:00", "avg_duration_hours", 2, "cost_per_person_eur", 60),
                Map.of("tour_code", "GYG-PRS-405", "activity_name", "Louvre Museum Skip-the-Line", "activity_category", "Museum", "venue_location", "Rue de Rivoli, Paris", "daily_open", "09:00", "daily_close", "20:00", "avg_duration_hours", 3, "cost_per_person_eur", 45)
        );
    }
}
