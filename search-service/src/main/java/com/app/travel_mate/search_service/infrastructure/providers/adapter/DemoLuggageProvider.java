package com.app.travel_mate.search_service.infrastructure.providers.adapter;

import com.app.travel_mate.search_service.domain.model.LuggageOption;
import com.app.travel_mate.search_service.domain.model.UserPrefs;
import com.app.travel_mate.search_service.infrastructure.providers.LuggageProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoLuggageProvider implements LuggageProvider {

    @Override
    public List<LuggageOption> search(UserPrefs prefs) {
        return List.of(
                new LuggageOption(
                        "DemoStasher",
                        prefs.to() + " Center",
                        8.0
                )
        );
    }
}
