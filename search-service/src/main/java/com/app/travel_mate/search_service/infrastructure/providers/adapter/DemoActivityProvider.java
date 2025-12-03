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
                        "DemoGetYourGuide",
                        "City tour",
                        prefs.to(),
                        30.0
                )
        );
    }
}
