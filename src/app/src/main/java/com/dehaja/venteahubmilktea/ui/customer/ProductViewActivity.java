package com.dehaja.venteahubmilktea.ui.customer;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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

        updateSubtotal(1);
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

    public void addToCartOnClick(View view) {
        SQLiteDatabase database = openOrCreateDatabase("Ventea", MODE_PRIVATE, null);
        try {
            // Check if the product is already existing in the cart.
            Cursor resultSet = database.rawQuery("SELECT * FROM Cart WHERE user_id = ? AND product_id = ?",
                    new String[] {String.valueOf(this.user.getId()), String.valueOf(this.product.getProduct_id())});
            if (resultSet.getCount() > 0) {
                // Update product qty
                database.execSQL(String.format(Locale.US, "UPDATE Cart SET quantity = %s WHERE user_id = %d AND product_id = %d",
                        this.textQuantity.getText(),
                        this.user.getUsername(),
                        this.product.getProduct_id()));
                Toast.makeText(this, "Successfully udpated item quantity", Toast.LENGTH_LONG).show();
            } else {
                // Add product to cart.
                database.execSQL(String.format(Locale.US, "INSERT INTO Cart VALUES(%d, %d, %s)",
                        this.user.getId(),
                        this.product.getProduct_id(),
                        this.textQuantity.getText()));
                Toast.makeText(this, "Successfully added to cart", Toast.LENGTH_LONG).show();
            }
        } catch (SQLException ex) {
            Toast.makeText(this, "Error adding to cart", Toast.LENGTH_LONG).show();
        }
    }

    public void closeOnClick(View view) {
        finish();
    }
}