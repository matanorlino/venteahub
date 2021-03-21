package com.dehaja.venteahubmilktea.util.constants;

public class OrderState {
    // Admin has not yet see the order made by the customer
    public static final String UNEXAMINED = "unexamined";
    // Order was placed but cancelled by the user
    public static final String CANCELLED = "cancelled";
    // Order was placed but the driver has not yet accepted to deliver
    public static final String WAITING_DRIVER = "wait deliver";
    // Order was delivered and paid
    public static final String RECEIVED = "received";
}
