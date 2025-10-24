package com.example.orders.api;

import com.example.common.model.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    private String userEmail;
    private List<OrderItem> items;
    private Double total;
}