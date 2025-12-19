package com.app.travel_mate.ranking_service.mq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
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
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public TopicExchange tripTopicExchange() {
        return new TopicExchange(tripExchange);
    }

    @Bean
    public Queue tripPlannedQueue() {
        return QueueBuilder.durable(tripPlannedQueue).build();
    }

    @Bean
    public Binding tripPlannedBinding(Queue tripPlannedQueue, TopicExchange tripTopicExchange) {
        return BindingBuilder.bind(tripPlannedQueue).to(tripTopicExchange).with(tripPlannedRoutingKey);
    }

    @Bean
    public MessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();

        typeMapper.setTrustedPackages("com.app.travel_mate.mq");  //Security Rule!!!

        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }
}