package com.example.paymentservice.config;

import com.example.common.Topics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {
    @Bean
    public NewTopic newOrders(@Value("${app.kafka.partitions}") int partitions) {
        return TopicBuilder.name(Topics.NEW_ORDERS).partitions(partitions).replicas(1).build();
    }
    @Bean
    public NewTopic payedOrders(@Value("${app.kafka.partitions}") int partitions) {
        return TopicBuilder.name(Topics.PAYED_ORDERS).partitions(partitions).replicas(1).build();
    }
    @Bean
    public NewTopic newOrdersDlt(@Value("${app.kafka.partitions}") int partitions) {
        return TopicBuilder.name(Topics.dlt(Topics.NEW_ORDERS)).partitions(partitions).replicas(1).build();
    }
    @Bean
    public NewTopic payedOrdersDlt(@Value("${app.kafka.partitions}") int partitions) {
        return TopicBuilder.name(Topics.dlt(Topics.PAYED_ORDERS)).partitions(partitions).replicas(1).build();
    }
}
