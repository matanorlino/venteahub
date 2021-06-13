package com.dehaja.venteahubmilktea.util.constants;

public class Properties {
    public static String TITLE = "Ventea Hub Milktea";
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
    // Other driver tries to update status
    public static final String CANNOT_UPDATE_BY_DRIVER = "Cannot update delivery status of other personnel";

    /*
     * Product View
     */
    public static final String ADD_TO_CART = "Add to Cart - ";
    public static final String UPDATE_CART = "Update Cart - ";
    public static final String PESO_SIGN = "\u20B1 ";
    public static final String FROM_PRODUCT_LIST = "product_list";
    public static final String FROM_CART = "cart";
    public static final String X = "x";

    /*
     * Map Key
     */
    public static String MAP_API_KEY = "AIzaSyDNYt-tL60_OdNJwDPTl_7KSM-Kmyxslos";

    public void setServerUrl(String url) {
        Properties.SERVER_URL = url;
    }

}
