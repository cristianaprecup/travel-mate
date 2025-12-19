package com.app.travel_mate.ranking_service.mq;

import com.app.travel_mate.mq.TripPlannedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TripPlannedListener {

    @RabbitListener(queues = "${travelmate.queue.trip-planned}")
    public void onTripPlanned(TripPlannedEvent event) {
        System.out.println("[RANKING] Received TripPlannedEvent async: " + event);
    }
}
