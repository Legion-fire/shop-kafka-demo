package com.example.orders.api;

import com.example.common.model.OrderEvent;
import com.example.common.model.OrderStatus;
import com.example.orders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Map<String, String>> create(@RequestBody CreateOrderRequest request) {
        OrderEvent event = OrderEvent.builder()
                .userEmail(request.getUserEmail())
                .items(request.getItems())
                .total(request.getTotal())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .status(OrderStatus.NEW)
                .build();

        String id = orderService.create(event);
        return ResponseEntity.ok(Map.of("orderId", id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id) {
        return orderService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestParam OrderStatus status) {
        boolean ok = orderService.manualUpdate(id, status);
        return ok ? ResponseEntity.ok(Map.of("orderId", id, "status", status.name()))
                : ResponseEntity.notFound().build();
    }
}

