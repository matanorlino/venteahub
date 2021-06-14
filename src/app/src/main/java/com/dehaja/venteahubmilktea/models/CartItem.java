package com.dehaja.venteahubmilktea.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CartItem implements Parcelable {
    private int userId;
    private int productId;
    private String productName;
    private float productPrice;
    private float sellPrice;
    private int quantity;
    private String model;
    private String instruction;

    public CartItem(int userId, int productId, String productName, float productPrice, float sellPrice, int quantity, String model, String instruction) {
        this.userId = userId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.sellPrice = sellPrice;
        this.quantity = quantity;
        this.model = model;
        this.instruction = instruction;
    }

    protected CartItem(Parcel in) {
        userId = in.readInt();
        productId = in.readInt();
        productName = in.readString();
        productPrice = in.readFloat();
        sellPrice = in.readFloat();
        quantity = in.readInt();
        model = in.readString();
        instruction = in.readString();
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

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

    public String getModel() { return model; }

    public void setModel(String model) { this.model = model; }

    public String getInstruction() { return instruction; }

    public void setInstruction(String instruction) { this.instruction = instruction; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(userId);
        parcel.writeInt(productId);
        parcel.writeString(productName);
        parcel.writeFloat(productPrice);
        parcel.writeFloat(sellPrice);
        parcel.writeInt(quantity);
        parcel.writeString(model);
        parcel.writeString(instruction);
    }
}
