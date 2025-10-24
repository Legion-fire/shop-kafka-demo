package com.example.orders.kafka;

import com.example.common.model.OrderEvent;
import com.example.common.model.OrderStatus;
import com.example.common.Topics;
import com.example.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrdersKafkaListener {

    private final OrderService orderService;

    @KafkaListener(
            topics = Topics.PAYED_ORDERS,
            groupId = "orders-service",
            concurrency = "${app.kafka.concurrency:3}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onPayed(ConsumerRecord<String, OrderEvent> record) {
        OrderEvent event = record.value();
        log.info("Received PAYED for order {} from partition {}", event.getOrderId(), record.partition());
        event.setStatus(OrderStatus.PAYED);
        orderService.updateFromEvent(event);
    }

    @KafkaListener(
            topics = Topics.SENT_ORDERS,
            groupId = "orders-service",
            concurrency = "${app.kafka.concurrency:3}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onSent(ConsumerRecord<String, OrderEvent> record) {
        OrderEvent event = record.value();
        log.info("Received SENT for order {} from partition {}", event.getOrderId(), record.partition());
        event.setStatus(OrderStatus.SHIPPED);
        orderService.updateFromEvent(event);
    }
}
