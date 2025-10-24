package com.example.notificationsservice.kafka;

import com.example.common.model.OrderEvent;
import com.example.common.Topics;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationListener {

    @KafkaListener(
            topics = Topics.SENT_ORDERS,
            groupId = "notifications-service",
            concurrency = "${app.kafka.concurrency:3}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onSent(ConsumerRecord<String, OrderEvent> record) {
        OrderEvent event = record.value();
        log.info("Notify user {}: order {} has been shipped. Partition: {}",
                event.getUserEmail(), event.getOrderId(), record.partition());
    }
}
