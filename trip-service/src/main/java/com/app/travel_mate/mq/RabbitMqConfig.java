package com.app.travel_mate.mq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${travelmate.exchange.trip}")
    private String tripExchange;

    @Value("${travelmate.queue.trip-planned}")
    private String tripPlannedQueue;

    @Value("${travelmate.routing.trip-planned}")
    private String tripPlannedRoutingKey;

    @Bean
    public TopicExchange tripTopicExchange() {
        return new TopicExchange(tripExchange);
    }

    @Bean
    public Queue tripPlannedQueue() {
        return QueueBuilder.durable(tripPlannedQueue).build();
    }   //Crucial for Fault Tolerance!!!

    @Bean
    public Binding tripPlannedBinding(Queue tripPlannedQueue, TopicExchange tripTopicExchange) {
        return BindingBuilder.bind(tripPlannedQueue).to(tripTopicExchange).with(tripPlannedRoutingKey);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}