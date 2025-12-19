package com.app.travel_mate.mq;

import com.app.travel_mate.mq.TripPlannedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TripEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${travelmate.exchange.trip}")
    private String tripExchange;

    @Value("${travelmate.routing.trip-planned}")
    private String tripPlannedRoutingKey;

    public TripEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishTripPlanned(TripPlannedEvent event) {
        rabbitTemplate.convertAndSend(tripExchange, tripPlannedRoutingKey, event);
        System.out.println("[TRIP] Published TripPlannedEvent async: " + event.eventId());
    }
}
