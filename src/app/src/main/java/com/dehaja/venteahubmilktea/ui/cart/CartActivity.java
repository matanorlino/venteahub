package com.dehaja.venteahubmilktea.ui.cart;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.CartItem;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.util.cart.CartUtil;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private ArrayList<CartItem> cartItems;
    private VenteaUser user;
    private ListView listProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Intent cartIntent = getIntent();
        user = (VenteaUser) cartIntent.getSerializableExtra("VenteaUser");

        TextView textEmptyCart = (TextView) findViewById(R.id.textEmptyCart);
        listProducts = (ListView) findViewById(R.id.listCartItems);

        loadCart();
        if (cartItems.size() > 0) {
            textEmptyCart.setVisibility(View.GONE);
            listProducts.setVisibility(View.VISIBLE);
            populateListView();
        } else {
            listProducts.setVisibility(View.GONE);
            textEmptyCart.setVisibility(View.VISIBLE);
        }
    }

    private void loadCart() {
        this.cartItems = new ArrayList<>();
        CartUtil cartUtil = CartUtil.getInstance(this);
        Cursor resultSet = cartUtil.getCart(user.getId());

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
        listProducts.setAdapter(adapter);
//        listProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent("android.intent.action.PRODUCT_VIEW");
//                intent.putExtra("product", (Product) adapterView.getItemAtPosition(i));
//                intent.putExtra("VenteaUser", user);
//                startActivity(intent);
//            }
//        });
    }

    public void closeOnClick(View view) {
        finish();
    }

    public void checkoutOnClick(View view) {
    }
}