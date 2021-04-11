package com.dehaja.venteahubmilktea.models;

public class OrderItem {
    private int user_id;
    private int order_id;
    private int product_id;
    private String username;
    private String contact_no;
    private String date;
    private String product_img;
    private String product_name;
    private String address;
    private int qty;
    private float sell_price;
    private float sub_total;
    private String state;
    private String request;

    public OrderItem(int user_id, int order_id, int product_id, String username, String contact_no, String date, String product_img, String product_name, String address, int qty, float sell_price, float sub_total, String state, String request) {
        this.user_id = user_id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.username = username;
        this.contact_no = contact_no;
        this.date = date;
        this.product_img = product_img;
        this.product_name = product_name;
        this.address = address;
        this.qty = qty;
        this.sell_price = sell_price;
        this.sub_total = sub_total;
        this.state = state;
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getSell_price() {
        return sell_price;
    }

    public void setSell_price(float sell_price) {
        this.sell_price = sell_price;
    }

    public float getSub_total() {
        return sub_total;
    }

    public void setSub_total(float sub_total) {
        this.sub_total = sub_total;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
