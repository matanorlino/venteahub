package com.dehaja.venteahubmilktea.ui.cart;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.CartItem;
import com.dehaja.venteahubmilktea.models.Product;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.ui.customer.ProductViewAdapter;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private SQLiteDatabase database;
    private ArrayList<CartItem> cartItems;
    private VenteaUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Intent cartIntent = getIntent();
        user = (VenteaUser) cartIntent.getSerializableExtra("VenteaUser");

        loadCart();
        populateListView();
    }

    private void loadCart() {
        this.cartItems = new ArrayList<>();

        Cursor resultSet = database.rawQuery("SELECT * FROM Cart WHERE user_id = ?",
                new String[] {String.valueOf(user.getId())});

        if (resultSet.moveToFirst()) {
            do {
                cartItems.add(new CartItem(
                        resultSet.getInt(0), //user_id
                        resultSet.getInt(1), //product_id
                        resultSet.getInt(2)  //quantity
                ));
            } while (resultSet.moveToNext());
        }
    }

    private void populateListView() {
        CartAdapter adapter = new CartAdapter(this, 0, cartItems);
        ListView listProducts = (ListView) view.findViewById(R.id.listProducts);
        listProducts.setAdapter(adapter);
        listProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent("android.intent.action.PRODUCT_VIEW");
                intent.putExtra("product", (Product) adapterView.getItemAtPosition(i));
                intent.putExtra("VenteaUser", user);
                startActivity(intent);
            }
        });
    }

    public void closeOnClick(View view) {
    }

    public void checkoutOnClick(View view) {
    }
}