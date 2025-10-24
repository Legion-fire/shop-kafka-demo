package com.example.common;

public final class Topics {
    private Topics() {}

    public static final String NEW_ORDERS = "new_orders";
    public static final String PAYED_ORDERS = "payed_orders";
    public static final String SENT_ORDERS = "sent_orders";

    public static String dlt(String topic) {
        return topic + ".DLT";
    }
}
