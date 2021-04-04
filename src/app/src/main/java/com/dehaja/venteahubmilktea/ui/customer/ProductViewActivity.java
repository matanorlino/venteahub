package com.dehaja.venteahubmilktea.ui.customer;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.Product;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.util.cart.CartUtil;
import com.dehaja.venteahubmilktea.util.constants.Properties;

import java.util.Locale;

public class ProductViewActivity extends AppCompatActivity {
    private VenteaUser user;
    private Product product;
    private float subtotal;
    private Button buttonMinusQty;
    private Button buttonPlusQty;
    private Button buttonAddToCart;
    private ImageView imageProductViewImage;
    private TextView textQuantity;
    private TextView textProductViewName;
    private TextView textProductViewPrice;
    private TextView textProductViewDescription;
    private String screenFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        Intent intent = getIntent();
        this.product = intent.getParcelableExtra("product");
        this.user = (VenteaUser) intent.getSerializableExtra("VenteaUser");
        this.screenFrom = intent.getStringExtra("ScreenFrom");

        this.buttonMinusQty = (Button) findViewById(R.id.buttonMinusQty);
        this.buttonPlusQty = (Button) findViewById(R.id.buttonPlusQty);
        this.buttonAddToCart = (Button) findViewById(R.id.buttonAddToCart);
        this.imageProductViewImage = (ImageView) findViewById(R.id.imageProductViewImage);
        this.textProductViewName = (TextView) findViewById(R.id.textProductViewName);
        this.textProductViewPrice = (TextView) findViewById(R.id.textProductViewPrice);
        this.textProductViewDescription = (TextView) findViewById(R.id.textProductViewDescription);
        this.textQuantity = (TextView) findViewById(R.id.textQuantity);

        initialize();
    }

    private void initialize() {
        setQuantity(1);

        String imgUrl = Properties.SERVER_URL + "/assets/product_img/" + this.product.getProduct_img();
        Glide.with(this)
                .load(imgUrl)
                .placeholder(R.mipmap.banner)
                .error(R.mipmap.banner)
                .centerCrop()
                .into(this.imageProductViewImage);

        this.textProductViewName.setText(this.product.getProduct_name());
        this.textProductViewPrice.setText(Properties.PESO_SIGN.concat(String.format(Locale.US, "%.2f", this.product.getSell_price())));
        this.textProductViewDescription.setText(this.product.getProduct_description());

        CartUtil cart = CartUtil.getInstance(this);
        if (cart.isProductExist(user.getId(), product.getProduct_id())) {
            loadCartDetails();
        } else {
            updateSubtotal(1);
        }

    }

    private void loadCartDetails() {
        CartUtil cart = CartUtil.getInstance(this);
        // Check if product is existing in cart
        if (cart.isProductExist(user.getId(), product.getProduct_id())) {
            Cursor resultSet = cart.getProduct(user.getId(), product.getProduct_id());
            // get product qty from cart then multiply to sell price
            int qty = getProductQtyFromCart(resultSet);
            this.subtotal = qty * product.getSell_price();
            this.buttonAddToCart.setText(Properties.UPDATE_CART.concat(
                    String.format(Locale.US, "%s %.2f", Properties.PESO_SIGN, subtotal)));
            this.textQuantity.setText(String.valueOf(qty));
        } else {
            this.buttonAddToCart.setText(Properties.ADD_TO_CART.concat(
                    String.format(Locale.US, "%s %.2f", Properties.PESO_SIGN, subtotal)));
        }
    }

    private void setQuantity(int quantity) {
        this.textQuantity.setText(String.valueOf(quantity));
        updateSubtotal(quantity);
    }

    public void plusOnClick(View view) {
        int quantity = Integer.parseInt(String.valueOf(this.textQuantity.getText()));
        if (quantity + 1 <= 999) {
            quantity++;
            setQuantity(quantity);
            updateQtyButtonState(quantity);
        }
    }

    public void minusOnClick(View view) {
        int quantity = Integer.parseInt(String.valueOf(this.textQuantity.getText()));
        if (quantity - 1 >= 1) {
            quantity--;
            setQuantity(quantity);
            updateQtyButtonState(quantity);
        }
    }

    private void updateQtyButtonState(int quantity) {
        if (quantity == 999) {
            // enable minus button
            this.buttonMinusQty.setEnabled(true);
            this.buttonMinusQty.setTextColor(getResources().getColor(R.color.teal_700));

            // disable plus button
            this.buttonPlusQty.setEnabled(false);
            this.buttonPlusQty.setTextColor(getResources().getColor(R.color.light_gray));
        } else if (quantity == 1) {
            // disable minus button
            this.buttonMinusQty.setEnabled(false);
            this.buttonMinusQty.setTextColor(getResources().getColor(R.color.light_gray));

            // enable plus button
            this.buttonPlusQty.setEnabled(true);
            this.buttonPlusQty.setTextColor(getResources().getColor(R.color.teal_700));
        } else if (quantity > 1 && quantity < 999) {
            // enable minus button
            this.buttonMinusQty.setEnabled(true);
            this.buttonMinusQty.setTextColor(getResources().getColor(R.color.teal_700));

            // enable plus button
            this.buttonPlusQty.setEnabled(true);
            this.buttonPlusQty.setTextColor(getResources().getColor(R.color.teal_700));
        }
    }

    private void updateSubtotal(int quantity) {
        this.subtotal = quantity * product.getSell_price();
        if (this.screenFrom.equalsIgnoreCase(Properties.FROM_CART)) {
            this.buttonAddToCart.setText(Properties.UPDATE_CART.concat(
                String.format(Locale.US, "%s %.2f", Properties.PESO_SIGN, subtotal)));
        } else {
            this.buttonAddToCart.setText(Properties.ADD_TO_CART.concat(
                    String.format(Locale.US, "%s %.2f", Properties.PESO_SIGN, subtotal)));
        }
    }

    private int getProductQtyFromCart(Cursor resultSet) {
        int qty = 0;
        if (resultSet.moveToFirst()) {
            do {
                qty = resultSet.getInt(2) ;//quantity
            } while (resultSet.moveToNext());
        }
        return qty;
    }

    public void addToCartOnClick(View view) {
        CartUtil cart = CartUtil.getInstance(this);
        try {
            int user_id = user.getId();
            int product_id = product.getProduct_id();
            int qty = Integer.parseInt(textQuantity.getText().toString());
            String product_name = product.getProduct_name();
            float product_price = product.getSell_price();
            float sell_price = product.getSell_price();
            if (cart.isProductExist(user.getId(), product.getProduct_id())) {
                // Update product qty
                cart.updateProduct(user_id, product_id, qty);
                Toast.makeText(this, "Successfully udpated item quantity", Toast.LENGTH_LONG).show();
            } else {
                // Add to cart
                if (cart.addToCart(user_id, product_id, qty, product_name, product_price, sell_price)) {
                    Toast.makeText(this, "Successfully added to cart", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Error adding to cart", Toast.LENGTH_LONG).show();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Toast.makeText(this, "Error adding to cart", Toast.LENGTH_LONG).show();
        }
    }

    public void closeOnClick(View view) {
        finish();
    }
}