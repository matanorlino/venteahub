package com.dehaja.venteahubmilktea.util.cart;

import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.Locale;

import static android.content.Context.*;

public class CartUtil {
    public static CartUtil instance;
    private Activity activity;
    private SQLiteDatabase db;

    public static CartUtil getInstance(Activity activity) {
        if (instance == null) {
            instance = new CartUtil(activity);
        }
        return instance;
    }

    private CartUtil (Activity activity) {
        this.activity = activity;
        this.db = activity.openOrCreateDatabase("Ventea", MODE_PRIVATE, null);
    }

    public void dropCartTable() {
        db.execSQL("DROP TABLE IF EXISTS Cart");
    }

    public void createCartTable() {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                "Cart(user_id INT, product_id INT, quantity INT, product_name TEXT, product_price FLOAT, sell_price FLOAT)");
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

    public Cursor getProduct(int user_id, int product_id) {
        Cursor result = null;
        result = db.rawQuery("SELECT * FROM Cart WHERE user_id = ? AND product_id = ?",
                new String[] {String.valueOf(user_id), String.valueOf(product_id)});
        return result;
    }

    public boolean isProductExist(int user_id, int product_id) {
        boolean isExist = false;
        try {
            Cursor result = db.rawQuery("SELECT * FROM Cart WHERE user_id = ? AND product_id = ?",
                    new String[] {String.valueOf(user_id), String.valueOf(product_id)});
            isExist = result.getCount() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isExist;
    }

    public boolean addToCart(int user_id, int product_id, int qty, String product_name, float product_price, float sell_price) {
        try {
            db.execSQL(String.format(Locale.US,
                "INSERT INTO Cart VALUES(%d, %d, %s, %s, %f, %f)",
                user_id,
                product_id,
                qty,
                "\'" + product_name + "\'",
                product_price,
                sell_price));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return isProductExist(user_id, product_id);
    }

    public void updateProduct(int user_id, int product_id, int qty) {
        try {
            Cursor result = getProduct(user_id, product_id);
            if (result.moveToFirst()) {
                do {
                    db.execSQL(String.format(Locale.US, "UPDATE Cart SET quantity = %d WHERE user_id = %d AND product_id = %d",
                            qty, user_id, product_id));
                } while (result.moveToNext());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
