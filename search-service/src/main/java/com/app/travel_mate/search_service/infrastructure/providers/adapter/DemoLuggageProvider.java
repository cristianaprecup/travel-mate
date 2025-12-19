package com.app.travel_mate.search_service.infrastructure.providers.adapter;

import com.app.travel_mate.search_service.domain.model.LuggageOption;
import com.app.travel_mate.search_service.domain.model.UserPrefs;
import com.app.travel_mate.search_service.infrastructure.providers.LuggageProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DemoLuggageProvider implements LuggageProvider {

    @Override
    public List<LuggageOption> search(UserPrefs prefs) {
        return List.of(
                new LuggageOption(
                        "LUG-001",
                        "DemoStash",
                        prefs.to() + " Station",
                        "Central Station",
                        LocalDateTime.now().plusDays(3).withHour(8),
                        LocalDateTime.now().plusDays(3).withHour(22),
                        2,
                        200
                ),
                new LuggageOption(
                        "LUG-002",
                        "SafeBox",
                        prefs.to() + " Mall",
                        "Shopping Mall",
                        LocalDateTime.now().plusDays(3).withHour(9),
                        LocalDateTime.now().plusDays(3).withHour(21),
                        3,
                        600
                )
        );
    }
}
