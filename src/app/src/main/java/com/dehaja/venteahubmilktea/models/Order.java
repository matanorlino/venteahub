package com.dehaja.venteahubmilktea.models;

import java.util.Date;

public class Order {
    private int order_id;
    private String address;
    private int user_id;
    private String username;
    private String contant_no;
    private String date;
    private float total;

    public Order(int order_id, int user_id, String address, String username, String contant_no, String date, float total) {
        this.order_id = order_id;
        this.address = address;
        this.user_id = user_id;
        this.username = username;
        this.contant_no = contant_no;
        this.date = date;
        this.total = total;
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

    public String getContant_no() {
        return contant_no;
    }

    public void setContant_no(String contant_no) {
        this.contant_no = contant_no;
    }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public float getTotal() { return total; }

    public void setTotal(float total) { this.total = total; }
}
