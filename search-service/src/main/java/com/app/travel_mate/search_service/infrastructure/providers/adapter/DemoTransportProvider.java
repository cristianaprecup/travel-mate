package com.app.travel_mate.search_service.infrastructure.providers.adapter;

import com.app.travel_mate.search_service.domain.model.TransportOption;
import com.app.travel_mate.search_service.domain.model.UserPrefs;
import com.app.travel_mate.search_service.domain.model.enums.TransportMode;
import com.app.travel_mate.search_service.infrastructure.providers.TransportProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DemoTransportProvider implements TransportProvider {

    @Override
    public List<TransportOption> search(UserPrefs prefs) {
        return List.of(
                new TransportOption(
                        "TR-FLT-001",
                        TransportMode.FLIGHT,
                        "Demo Airlines",
                        prefs.from(),
                        prefs.to(),
                        LocalDateTime.now().plusDays(3).withHour(9),
                        LocalDateTime.now().plusDays(3).withHour(12),
                        180,
                        0,
                        true,
                        150
                ),
                new TransportOption(
                        "TR-TRAIN-002",
                        TransportMode.TRAIN,
                        "Demo Rail",
                        prefs.from(),
                        prefs.to(),
                        LocalDateTime.now().plusDays(3).withHour(7),
                        LocalDateTime.now().plusDays(3).withHour(13),
                        360,
                        1,
                        false,
                        70
                ),
                new TransportOption(
                        "TR-BUS-003",
                        TransportMode.BUS,
                        "EuroBus",
                        prefs.from(),
                        prefs.to(),
                        LocalDateTime.now().plusDays(3).withHour(6),
                        LocalDateTime.now().plusDays(3).withHour(16),
                        600,
                        2,
                        false,
                        40
                ),
                new TransportOption(
                        "TR-METRO-004",
                        TransportMode.METRO,
                        "CityMetro",
                        prefs.from() + " Center",
                        prefs.to() + " Station",
                        LocalDateTime.now().withHour(10),
                        LocalDateTime.now().withHour(10).plusMinutes(25),
                        25,
                        0,
                        false,
                        5
                ),
                new TransportOption(
                        "TR-WALK-005",
                        TransportMode.WALK,
                        "On Foot",
                        prefs.from(),
                        prefs.to(),
                        LocalDateTime.now().withHour(12),
                        LocalDateTime.now().withHour(12).plusMinutes(45),
                        45,
                        0,
                        false,
                        0
                ),
                new TransportOption(
                        "TR-FLT-006",
                        TransportMode.FLIGHT,
                        "Premium Air",
                        prefs.from(),
                        prefs.to(),
                        LocalDateTime.now().plusDays(3).withHour(14),
                        LocalDateTime.now().plusDays(3).withHour(17),
                        180,
                        0,
                        true,
                        240
                ),
                new TransportOption(
                        "TR-TRAIN-007",
                        TransportMode.TRAIN,
                        "FastRail Express",
                        prefs.from(),
                        prefs.to(),
                        LocalDateTime.now().plusDays(3).withHour(8),
                        LocalDateTime.now().plusDays(3).withHour(11),
                        180,
                        0,
                        true,
                        110
                )
        );
    }
}
