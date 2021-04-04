package com.dehaja.venteahubmilktea.models;

public class CartItem {
    private int userId;
    private int productId;
    private String productName;
    private float productPrice;
    private float sellPrice;
    private int quantity;

    public CartItem(int userId, int productId, String productName, float productPrice, float sellPrice, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getSellPrice() { return sellPrice; }

    public void setSellPrice(float sellPrice) { this.sellPrice = sellPrice; }
}
