package com.app.travel_mate.infrastructure.providers.adapter.stay;

import com.app.travel_mate.domain.model.options.StayOption;
import com.app.travel_mate.domain.model.queries.StayQuery;
import com.app.travel_mate.infrastructure.providers.AccommodationProvider;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DemoBookingAdapter implements AccommodationProvider {

    @Override
    public String name() {
        return "Booking Demo API";
    }

    @Override
    public List<StayOption> search(StayQuery query) {
        System.out.println("Adapter: Searching stays via " + name());

        return getMockBookingData().stream()
                .map(this::convertRawToStayOption)
                .collect(Collectors.toList());
    }

    private StayOption convertRawToStayOption(Map<String, Object> raw) {
        int price = (Integer) raw.get("price_per_night_eur");

        LocalDateTime checkIn = LocalDateTime.now().withHour(15).withMinute(0);
        LocalDateTime checkOut = LocalDateTime.now().plusDays(1).withHour(11).withMinute(0);

        return new StayOption(
                (String) raw.get("booking_ref"),
                (String) raw.get("property_title"),
                (String) raw.get("type_of_stay"),
                (String) raw.get("street_address"),
                checkIn,
                checkOut,
                price,
                (Integer) raw.get("distance_to_center_m")
        );
    }

    private List<Map<String, Object>> getMockBookingData() {
        return List.of(
                Map.of("booking_ref", "BOOK-HOT-987", "property_title", "Grand Hotel du Louvre", "type_of_stay", "Hotel", "street_address", "12 Rue de Rivoli, Paris", "price_per_night_eur", 250, "distance_to_center_m", 500, "checkin_time", "15:00", "checkout_time", "11:00"),
                Map.of("booking_ref", "BOOK-HST-543", "property_title", "The Traveller Hostel", "type_of_stay", "Hostel", "street_address", "45 Rue Saint-André, Paris", "price_per_night_eur", 45, "distance_to_center_m", 1200, "checkin_time", "14:00", "checkout_time", "10:00")
        );
    }
}