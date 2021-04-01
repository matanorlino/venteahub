package com.dehaja.venteahubmilktea.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductCategory implements Parcelable {
    private int category_id;
    private String category_name;
    private String category_note;

    private ProductCategory() {}

    public ProductCategory(int category_id, String category_name, String category_note) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.category_note = category_note;
    }

    protected ProductCategory(Parcel in) {
        category_id = in.readInt();
        category_name = in.readString();
        category_note = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(category_id);
        dest.writeString(category_name);
        dest.writeString(category_note);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductCategory> CREATOR = new Creator<ProductCategory>() {
        @Override
        public ProductCategory createFromParcel(Parcel in) {
            return new ProductCategory(in);
        }

        @Override
        public ProductCategory[] newArray(int size) {
            return new ProductCategory[size];
        }
    };

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_note() {
        return category_note;
    }

    public void setCategory_note(String category_note) {
        this.category_note = category_note;
    }

}
