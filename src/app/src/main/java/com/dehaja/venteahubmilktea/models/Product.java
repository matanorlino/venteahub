package com.dehaja.venteahubmilktea.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private int product_id;
    private int category_id;
    private float market_price;
    private float sell_price;
    private String product_code;
    private String product_img;
    private String model;
    private String purchase_description;
    private String product_description;
    private String status;
    private String product_name;

    public Product() {}

    public Product(int product_id, int category_id, float market_price, float sell_price, String product_code, String product_img, String model, String purchase_description, String product_description, String status, String product_name) {
        this.product_id = product_id;
        this.category_id = category_id;
        this.market_price = market_price;
        this.sell_price = sell_price;
        this.product_code = product_code;
        this.product_img = product_img;
        this.model = model;
        this.purchase_description = purchase_description;
        this.product_description = product_description;
        this.status = status;
        this.product_name = product_name;
    }

    protected Product(Parcel in) {
        product_id = in.readInt();
        category_id = in.readInt();
        market_price = in.readFloat();
        sell_price = in.readFloat();
        product_code = in.readString();
        product_img = in.readString();
        model = in.readString();
        purchase_description = in.readString();
        product_description = in.readString();
        status = in.readString();
        product_name = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public float getMarket_price() {
        return market_price;
    }

    public void setMarket_price(float market_price) {
        this.market_price = market_price;
    }

    public float getSell_price() {
        return sell_price;
    }

    public void setSell_price(float sell_price) {
        this.sell_price = sell_price;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getModel() { return model; }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPurchase_description() {
        return purchase_description;
    }

    public void setPurchase_description(String purchase_description) {
        this.purchase_description = purchase_description;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(product_id);
        parcel.writeInt(category_id);
        parcel.writeFloat(market_price);
        parcel.writeFloat(sell_price);
        parcel.writeString(product_code);
        parcel.writeString(product_img);
        parcel.writeString(model);
        parcel.writeString(purchase_description);
        parcel.writeString(product_description);
        parcel.writeString(status);
        parcel.writeString(product_name);
    }
}
