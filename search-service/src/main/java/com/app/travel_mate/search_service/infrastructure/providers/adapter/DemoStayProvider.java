package com.app.travel_mate.search_service.infrastructure.providers.adapter;

import com.app.travel_mate.search_service.domain.model.StayOption;
import com.app.travel_mate.search_service.domain.model.UserPrefs;
import com.app.travel_mate.search_service.infrastructure.providers.StayProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoStayProvider implements StayProvider {

    @Override
    public List<StayOption> search(UserPrefs prefs) {
        return List.of(
                new StayOption(
                        "DemoBooking",
                        "Demo Hotel",
                        prefs.to(),
                        60.0,
                        4.5
                )
        );
    }
}
