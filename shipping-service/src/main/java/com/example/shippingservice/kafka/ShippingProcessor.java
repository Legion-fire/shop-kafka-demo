package com.example.shippingservice.kafka;

import com.example.common.model.OrderEvent;
import com.example.common.model.OrderStatus;
import com.example.common.Topics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShippingProcessor {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(
            topics = Topics.PAYED_ORDERS,
            groupId = "shipping-service",
            concurrency = "${app.kafka.concurrency:3}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onPayed(ConsumerRecord<String, OrderEvent> record) {
        OrderEvent event = record.value();
        String key = record.key();
        log.info("Shipping received payed order {} from partition {}", key, record.partition());

        // Имитация упаковки/отгрузки
        try { Thread.sleep(150); } catch (InterruptedException ignored) {}

        event.setStatus(OrderStatus.SHIPPED);
        event.setUpdatedAt(Instant.now());
        kafkaTemplate.send(Topics.SENT_ORDERS, key, event);
        log.info("Order {} shipped -> sent to {}", key, Topics.SENT_ORDERS);
    }
}
