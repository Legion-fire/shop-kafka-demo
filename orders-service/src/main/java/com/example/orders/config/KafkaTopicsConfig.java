package com.example.orders.config;

import com.example.common.Topics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicsConfig {

    @Bean
    public NewTopic newOrdersTopic(@Value("${app.kafka.partitions}") int partitions) {
        return TopicBuilder.name(Topics.NEW_ORDERS).partitions(partitions).replicas(1).build();
    }

    @Bean
    public NewTopic payedOrdersTopic(@Value("${app.kafka.partitions}") int partitions) {
        return TopicBuilder.name(Topics.PAYED_ORDERS).partitions(partitions).replicas(1).build();
    }

    @Bean
    public NewTopic sentOrdersTopic(@Value("${app.kafka.partitions}") int partitions) {
        return TopicBuilder.name(Topics.SENT_ORDERS).partitions(partitions).replicas(1).build();
    }

    @Bean
    public NewTopic newOrdersDlt(@Value("${app.kafka.partitions}") int partitions) {
        return TopicBuilder.name(Topics.dlt(Topics.NEW_ORDERS)).partitions(partitions).replicas(1).build();
    }

    @Bean
    public NewTopic payedOrdersDlt(@Value("${app.kafka.partitions}") int partitions) {
        return TopicBuilder.name(Topics.dlt(Topics.PAYED_ORDERS)).partitions(partitions).replicas(1).build();
    }

    @Bean
    public NewTopic sentOrdersDlt(@Value("${app.kafka.partitions}") int partitions) {
        return TopicBuilder.name(Topics.dlt(Topics.SENT_ORDERS)).partitions(partitions).replicas(1).build();
    }
}
