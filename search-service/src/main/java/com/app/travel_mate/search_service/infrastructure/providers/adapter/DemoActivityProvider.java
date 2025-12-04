package com.app.travel_mate.search_service.infrastructure.providers.adapter;

import com.app.travel_mate.search_service.domain.model.ActivityOption;
import com.app.travel_mate.search_service.domain.model.UserPrefs;
import com.app.travel_mate.search_service.infrastructure.providers.ActivityProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoActivityProvider implements ActivityProvider {

    @Override
    public List<ActivityOption> search(UserPrefs prefs) {
        return List.of(
                new ActivityOption(
                        "ACT-001",
                        "City Walking Tour",
                        "Tourism",
                        prefs.to(),
                        "09:00",
                        "17:00",
                        120,
                        25
                ),
                new ActivityOption(
                        "ACT-002",
                        "Art Museum Entry",
                        "Culture",
                        prefs.to(),
                        "10:00",
                        "18:00",
                        90,
                        15
                )
        );
    }
}
