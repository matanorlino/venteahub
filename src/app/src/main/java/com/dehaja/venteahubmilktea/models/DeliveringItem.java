package com.dehaja.venteahubmilktea.models;

public class DeliveringItem {

    private int delivered_by;
    private int order_id;
    private String address;
    private double latitude;
    private double longitude;
    private String state;

    public DeliveringItem () {}

    public DeliveringItem(int delivered_by, int order_id, String address, double latitude, double longitude, String state) {
        this.delivered_by = delivered_by;
        this.order_id = order_id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.state = state;
    }

    public int getDelivered_by() {
        return delivered_by;
    }

    public void setDelivered_by(int delivered_by) {
        this.delivered_by = delivered_by;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
