package com.example.paymentservice.kafka;

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
public class PaymentProcessor {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(
            topics = Topics.NEW_ORDERS,
            groupId = "payment-service",
            concurrency = "${app.kafka.concurrency:3}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onNewOrder(ConsumerRecord<String, OrderEvent> record) {
        OrderEvent event = record.value();
        String key = record.key();
        log.info("Payment received new order {} from partition {}", key, record.partition());

        // Демонстрация обработки/валидации
        if (event.getTotal() == null || event.getTotal() <= 0) {
            throw new IllegalArgumentException("Invalid total for order " + key);
        }

        // Имитация обработки
        try { Thread.sleep(100); } catch (InterruptedException ignored) {}

        event.setStatus(OrderStatus.PAYED);
        event.setUpdatedAt(Instant.now());

        kafkaTemplate.send(Topics.PAYED_ORDERS, key, event);
        log.info("Order {} payed -> sent to {}", key, Topics.PAYED_ORDERS);
    }
}

