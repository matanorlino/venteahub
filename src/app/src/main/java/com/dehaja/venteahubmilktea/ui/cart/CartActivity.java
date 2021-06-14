package com.dehaja.venteahubmilktea.ui.cart;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
    private float total = 0f;
    private Button buttonAddToCart;
    TextView textEmptyCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Intent cartIntent = getIntent();
        user = (VenteaUser) cartIntent.getSerializableExtra("VenteaUser");

        buttonAddToCart = findViewById(R.id.buttonAddToCart);

        textEmptyCart = (TextView) findViewById(R.id.textEmptyCart);
        listProducts = (ListView) findViewById(R.id.listCartItems);

        loadCart();
    }

    private void loadCart() {
        this.cartItems = new ArrayList<>();
        CartUtil cartUtil = CartUtil.getInstance(this);
        Cursor resultSet = cartUtil.getCart(user.getId());

        if (resultSet.moveToFirst()) {
            do {
                CartItem item = new CartItem(0,0,"",0,0,0,"","");
                item.setUserId(resultSet.getInt(resultSet.getColumnIndex("user_id")));
                item.setProductId(resultSet.getInt(resultSet.getColumnIndex("product_id")));
                item.setProductName(resultSet.getString(resultSet.getColumnIndex("product_name")));
                item.setProductPrice(resultSet.getFloat(resultSet.getColumnIndex("product_price")));
                item.setSellPrice(resultSet.getFloat(resultSet.getColumnIndex("sell_price")));
                item.setQuantity(resultSet.getInt(resultSet.getColumnIndex("quantity")));
                item.setModel(resultSet.getString(resultSet.getColumnIndex("model")));
                item.setInstruction(resultSet.getString(resultSet.getColumnIndex("instruction")));
                cartItems.add(item);
            } while (resultSet.moveToNext());
        }

        if (cartItems.size() < 1) {
            buttonAddToCart.setEnabled(false);
        }

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
        for(CartItem item : cartItems) {
            total += (item.getQuantity() * item.getSellPrice());
        }
        txtTotal.setText(String.format(Locale.US, "%s %.2f", Properties.PESO_SIGN, total));
    }

    public void closeOnClick(View view) {
        finish();
    }

    public void checkoutOnClick(View view) {
        Intent checkoutIntent = new Intent("android.intent.action.CHECKOUT_VIEW");
        checkoutIntent.putExtra("VenteaUser", user);
        checkoutIntent.putExtra("total", total);
        checkoutIntent.putExtra("cartItems", cartItems);
        startActivityForResult(checkoutIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == 1) {
            loadCart();
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}