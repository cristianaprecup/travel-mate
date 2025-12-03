package com.app.travel_mate.search_service.infrastructure.providers.adapter;

import com.app.travel_mate.search_service.domain.model.TransportOption;
import com.app.travel_mate.search_service.domain.model.UserPrefs;
import com.app.travel_mate.search_service.infrastructure.providers.TransportProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoTransportProvider implements TransportProvider {

    @Override
    public List<TransportOption> search(UserPrefs prefs) {
        return List.of(
                new TransportOption(
                        "DemoSkyscanner",
                        prefs.from(),
                        prefs.to(),
                        "plane",
                        120.0
                )
        );
    }
}
