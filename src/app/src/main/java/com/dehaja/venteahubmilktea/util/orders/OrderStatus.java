package com.dehaja.venteahubmilktea.util.orders;

public class OrderStatus {

    private static OrderStatus instance;
    private String orderStatus;

    private OrderStatus() {}

    public static OrderStatus getInstance() {
        if (instance == null) {
            instance = new OrderStatus();
        }
        return instance;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String status) {
        this.orderStatus = status;
    }
}