package com.app.travel_mate.infrastructure.providers.adapter.transport;

import com.app.travel_mate.domain.model.enums.TransportMode;
import com.app.travel_mate.domain.model.options.TransportOption;
import com.app.travel_mate.domain.model.queries.TransportQuery;
import com.app.travel_mate.infrastructure.providers.TransportProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DemoSkyscannerAdapter implements TransportProvider {

    private static final DateTimeFormatter ISO_FORMAT = DateTimeFormatter.ISO_INSTANT.withZone(java.time.ZoneOffset.UTC);

    @Override
    public String name() {
        return "Skyscanner Demo API";
    }

    @Override
    public List<TransportOption> search(TransportQuery query) {
        System.out.println("Adapter: Searching transport via " + name());

        return getMockSkyscannerData().stream()
                .map(this::convertRawToTransportOption)
                .filter(option -> option.getOrigin().equals(query.getOrigin()) && option.getDestination().equals(query.getDestination()))
                .collect(Collectors.toList());
    }

    private TransportOption convertRawToTransportOption(Map<String, Object> raw) {

        LocalDateTime departTime = LocalDateTime.parse((String) raw.get("departure_utc"), ISO_FORMAT);
        LocalDateTime arriveTime = LocalDateTime.parse((String) raw.get("arrival_utc"), ISO_FORMAT);
        long durationMinutes = ChronoUnit.MINUTES.between(departTime, arriveTime);
        int price = (int) Math.round((Double) raw.get("ticket_price_usd"));

        TransportMode mode = TransportMode.valueOf((String) raw.get("service_type"));

        return new TransportOption(
                (String) raw.get("api_flight_id"),
                mode,
                (String) raw.get("provider_name"),
                (String) raw.get("departure_city_code"),
                (String) raw.get("arrival_city_code"),
                departTime,
                arriveTime,
                (int) durationMinutes,
                (Integer) raw.get("total_stops"),
                (Boolean) raw.get("luggage_allowed"),
                price
        );
    }

    private List<Map<String, Object>> getMockSkyscannerData() {
        return List.of(
                Map.of("api_flight_id", "SK-FLT-7331", "provider_name", "Lufthansa", "departure_city_code", "OTP", "arrival_city_code", "CDG", "ticket_price_usd", 150.00, "total_stops", 0, "departure_utc", "2025-12-10T10:00:00Z", "arrival_utc", "2025-12-10T13:30:00Z", "luggage_allowed", true, "service_type", "FLIGHT"),
                Map.of("api_flight_id", "SK-TRN-190", "provider_name", "SNCF", "departure_city_code", "CDG", "arrival_city_code", "LYS", "ticket_price_usd", 75.50, "total_stops", 2, "departure_utc", "2025-12-11T08:00:00Z", "arrival_utc", "2025-12-11T11:00:00Z", "luggage_allowed", true, "service_type", "TRAIN")
        );
    }
}