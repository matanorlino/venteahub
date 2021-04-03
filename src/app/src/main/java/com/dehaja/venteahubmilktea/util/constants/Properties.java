package com.dehaja.venteahubmilktea.util.constants;

public class Properties {
    /*
     * Configuration
     */
    public static String SERVER_URL = "http://192.168.1.28/commission/";
    public static final String ERROR = "0";
    public static final String SUCCESS = "1";
    public static final String DUPLICATE = "2";
    /*
     * Access Level
     */
    // Administrator user
    public static final String ADMIN = "admin";
    // Customer user
    public static final String CUSTOMER = "customer";
    // Delivery personnel
    public static final String DRIVER = "driver";

    /*
     * Order State
     */
    // Admin has not yet see the order made by the customer
    public static final String UNEXAMINED = "unexamined";
    // Order was placed but cancelled by the user
    public static final String CANCELLED = "cancelled";
    // Order was placed but the driver has not yet accepted to deliver
    public static final String WAITING_DRIVER = "wait_deliver";
    // Order is being delivered by the driver
    public static final String DELIVERING = "delivering";
    // Order was delivered and paid
    public static final String RECEIVED = "received";

    public void setServerUrl(String url) {
        Properties.SERVER_URL = url;
    }

}
