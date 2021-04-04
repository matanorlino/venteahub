package com.dehaja.venteahubmilktea.util.cart;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Locale;

import static android.content.Context.*;

public class CartUtil {
    private Activity activity;
    private SQLiteDatabase db;
    public CartUtil (Activity activity) {
        this.activity = activity;
        SQLiteDatabase db = activity.openOrCreateDatabase("Ventea", MODE_PRIVATE, null);
    }

    public Cursor getCart() {
        Cursor result = null;
        result = db.rawQuery("SELECT * FROM Cart", null);
        return result;
    }

    public Cursor getCart(int user_id) {
        Cursor result = null;
        result = db.rawQuery("SELECT * FROM Cart WHERE user_id = ?", new String[] {String.valueOf(user_id)});
        return result;
    }

    public Cursor getProductCart(int user_id, int product_id) {
        Cursor result = null;
        result = db.rawQuery("SELECT * FROM Cart WHERE user_id = ? AND product_id = ?",
                new String[] {String.valueOf(user_id), String.valueOf(product_id)});
        return result;
    }

    public boolean isProductExist(int user_id, int product_id) {
        Cursor result = db.rawQuery("SELECT * FROM Cart WHERE user_id = ? AND product_id = ?",
                new String[] {String.valueOf(user_id), String.valueOf(product_id)});

        return result.getCount() > 0;
    }

    public boolean addToCart(int user_id, int product_id, int qty) {
        db.execSQL(String.format(Locale.US, "INSERT INTO Cart VALUES(%d, %d, %s)",
                user_id,
                product_id,
                qty));

        return isProductExist(user_id, product_id);
    }

    public void updateProduct(int user_id, int product_id, int qty) {
        db.execSQL(String.format(Locale.US, "UPDATE Cart SET quantity = %s WHERE user_id = %d AND product_id = %d",
                qty, user_id, product_id));
    }

    public void clearCart(int user_id) {
        db.execSQL(String.format(Locale.US, "DELETE FROM Cart WHERE user_id = %d", user_id));
    }

    public boolean removeProduct(int user_id, int product_id) {
        db.execSQL(String.format(Locale.US, "DELETE FROM Cart WHERE user_id = %d AND product_id",
                user_id, product_id));
        return isProductExist(user_id, product_id);
    }

}
