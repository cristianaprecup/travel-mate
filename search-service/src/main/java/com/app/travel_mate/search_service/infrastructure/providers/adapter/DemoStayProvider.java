package com.app.travel_mate.search_service.infrastructure.providers.adapter;

import com.app.travel_mate.search_service.domain.model.StayOption;
import com.app.travel_mate.search_service.domain.model.UserPrefs;
import com.app.travel_mate.search_service.infrastructure.providers.StayProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DemoStayProvider implements StayProvider {

    @Override
    public List<StayOption> search(UserPrefs prefs) {
        return List.of(
                new StayOption(
                        "HTL-001",
                        "Demo Grand Hotel",
                        "Hotel",
                        prefs.to() + " Center Street 12",
                        LocalDateTime.now().plusDays(3).withHour(14),
                        LocalDateTime.now().plusDays(7).withHour(11),
                        85,
                        500
                ),
                new StayOption(
                        "HTL-002",
                        "Cozy Budget Inn",
                        "Guest House",
                        prefs.to() + " Old Town 7",
                        LocalDateTime.now().plusDays(3).withHour(15),
                        LocalDateTime.now().plusDays(7).withHour(10),
                        45,
                        1500
                )
        );
    }
}
