package com.example.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private String orderId;
    private String userEmail;
    private List<OrderItem> items;
    private Double total;
    private OrderStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
