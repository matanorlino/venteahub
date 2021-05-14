package com.dehaja.venteahubmilktea.ui.cart;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.CartItem;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.util.cart.CartUtil;
import com.dehaja.venteahubmilktea.util.constants.Properties;

import java.util.ArrayList;
import java.util.Locale;

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
            setTotalText();
        } else {
            listProducts.setVisibility(View.GONE);
            textEmptyCart.setVisibility(View.VISIBLE);
        }
    }

    private void loadCart() {
        this.cartItems = new ArrayList<>();
        CartUtil cartUtil = CartUtil.getInstance(this);
        Cursor resultSet = cartUtil.getCart(user.getId());

        System.out.println("ResultSet: " + resultSet);
        if (resultSet.moveToFirst()) {
            do {
                cartItems.add(new CartItem(
                        resultSet.getInt(0), //user_id
                        resultSet.getInt(1), //product_id
                        resultSet.getString(3), // product_name
                        resultSet.getFloat(4), // product_price
                        resultSet.getFloat(5), // sell_price
                        resultSet.getInt(2), //quantity
                        resultSet.getString(6) //model
                ));
            } while (resultSet.moveToNext());
        }
    }

    private void populateListView() {
        CartAdapter adapter = new CartAdapter(this, 0, cartItems);
        listProducts.setAdapter(adapter);
        listProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent("android.intent.action.PRODUCT_VIEW");
                intent.putExtra("product_id", ((CartItem) adapterView.getItemAtPosition(i)).getProductId());
                intent.putExtra("VenteaUser", user);
                startActivityForResult(intent,1);
            }
        });
    }

    private void setTotalText() {
        TextView txtTotal = (TextView) findViewById(R.id.textTotal);
        float total = 0f;
        for(CartItem item : cartItems) {
            total += (item.getQuantity() * item.getSellPrice());
        }
        txtTotal.setText(String.format(Locale.US, "%s %.2f", Properties.PESO_SIGN, total));
    }

    public void closeOnClick(View view) {
        finish();
    }

    public void checkoutOnClick(View view) {
        CartUtil cart = CartUtil.getInstance(this);
        cart.clearCart(user.getId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == 1) {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}