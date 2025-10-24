package com.example.order.service;

import com.example.common.model.OrderEvent;
import com.example.common.model.OrderStatus;
import com.example.common.Topics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final Map<String, OrderEvent> store = new ConcurrentHashMap<>();

    public String create(OrderEvent event) {
        String orderId = Optional.ofNullable(event.getOrderId()).orElse(UUID.randomUUID().toString());
        event.setOrderId(orderId);
        event.setStatus(OrderStatus.NEW);
        event.setCreatedAt(Instant.now());
        event.setUpdatedAt(Instant.now());
        store.put(orderId, event);

        kafkaTemplate.send(Topics.NEW_ORDERS, orderId, event);
        log.info("Order created and sent to Kafka: {}", orderId);
        return orderId;
    }

    public Optional<OrderEvent> get(String orderId) {
        return Optional.ofNullable(store.get(orderId));
    }

    public void updateFromEvent(OrderEvent incoming) {
        store.compute(incoming.getOrderId(), (id, existing) -> {
            OrderEvent toSave = existing != null ? existing : incoming;
            toSave.setStatus(incoming.getStatus());
            toSave.setUpdatedAt(Instant.now());
            return toSave;
        });
        log.info("Order {} status updated to {}", incoming.getOrderId(), incoming.getStatus());
    }

    public boolean manualUpdate(String orderId, OrderStatus status) {
        OrderEvent ev = store.get(orderId);
        if (ev == null) return false;
        ev.setStatus(status);
        ev.setUpdatedAt(Instant.now());
        return true;
    }
}
