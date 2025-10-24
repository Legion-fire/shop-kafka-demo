package com.example.order.api;

import com.example.common.model.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CreateOrderRequest {
    private String userEmail;
    private List<OrderItem> items;
    private BigDecimal total;
}