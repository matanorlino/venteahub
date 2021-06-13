package com.dehaja.venteahubmilktea.models;

import java.io.Serializable;

public class Order implements Serializable {
    private int order_id;
    private String address;
    private int user_id;
    private String username;
    private String contact_no;
    private String date;
    private float total;
    private int delivered_by;

    public Order(int order_id, int user_id, String address, String username, String contact_no, String date, float total) {
        this.order_id = order_id;
        this.address = address;
        this.user_id = user_id;
        this.username = username;
        this.contact_no = contact_no;
        this.date = date;
        this.total = total;
    }

    public Order(int order_id, int user_id, String address, String username, String contact_no, String date, float total, int delivered_by) {
        this.order_id = order_id;
        this.address = address;
        this.user_id = user_id;
        this.username = username;
        this.contact_no = contact_no;
        this.date = date;
        this.total = total;
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

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public float getTotal() { return total; }

    public void setTotal(float total) { this.total = total; }

    public int getDelivered_by() { return this.delivered_by; }

    public void setDelivered_by(int delivered_by) { this.delivered_by = delivered_by; }
}
