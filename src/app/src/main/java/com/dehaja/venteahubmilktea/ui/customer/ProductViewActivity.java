package com.dehaja.venteahubmilktea.ui.customer;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.Product;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.util.cart.CartUtil;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.dehaja.venteahubmilktea.util.constants.Validator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class ProductViewActivity extends AppCompatActivity {
    private VenteaUser user;
    private float subtotal;
    private Button buttonMinusQty;
    private Button buttonPlusQty;
    private Button buttonAddToCart;
    private ImageView imageProductViewImage;
    private TextView textQuantity;
    private TextView textProductViewName;
    private TextView textProductViewPrice;
    private TextView textProductViewDescription;
    private RadioGroup radioProductModels;
    private Product selectedModel;
    private boolean isExistingInCart;
    private String productCode;
    private int productId;
    private ArrayList<Product> models;
    private EditText editProductInstruction;

    private int qty;
    private String instruction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        models = new ArrayList<>();
        Intent intent = getIntent();
        this.user = (VenteaUser) intent.getSerializableExtra("VenteaUser");
        this.productCode = intent.getStringExtra("product_code");
        this.productId = intent.getIntExtra("product_id", 0);
        this.editProductInstruction = findViewById(R.id.editProductInstruction);

        getProduct();
    }

    private void getProduct() {
        String url = Properties.SERVER_URL + "api/App_Products.php?";

        if (productId != 0) {
            url = url.concat("product_id=" + productId);
            Button btnRemove = (Button) findViewById(R.id.btnRemove);
            btnRemove.setVisibility(View.VISIBLE);
        } else if (productCode != null && !productCode.isEmpty()) {
            url = url.concat("product_code=" + productCode);
        }
        RequestQueue q = Volley.newRequestQueue(this);

        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            if (Validator.isResponseSuccess(res.getString("response"))) {
                                JSONArray data = res.getJSONArray("data");
                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject obj = data.getJSONObject(i);
                                    if (obj != null) {
                                        Product product = new Product();
                                        product.setProduct_id(obj.getInt("product_id"));
                                        product.setCategory_id(obj.getInt("product_category_id"));
                                        product.setMarket_price(Float.parseFloat(obj.getString("market_price")));
                                        product.setSell_price(Float.parseFloat(obj.getString("sell_price")));
                                        product.setProduct_code(obj.getString("product_code"));
                                        product.setProduct_img(obj.getString("product_img"));
                                        product.setModel(obj.getString("model"));
                                        product.setPurchase_description(obj.getString("purchase_description"));
                                        product.setProduct_description(obj.getString("product_description"));
                                        product.setStatus(obj.getString("status"));
                                        product.setProduct_name(obj.getString("product_name"));
                                        models.add(product);
                                    }
                                }
                            }
                            initializeContent();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        q.add(jsonObjRequest);
    }

    private void initializeContent() {
        this.buttonMinusQty = (Button) findViewById(R.id.buttonMinusQty);
        this.buttonPlusQty = (Button) findViewById(R.id.buttonPlusQty);
        this.buttonAddToCart = (Button) findViewById(R.id.buttonAddToCart);
        this.imageProductViewImage = (ImageView) findViewById(R.id.imageProductViewImage);
        this.textProductViewName = (TextView) findViewById(R.id.textProductViewName);
        this.textProductViewPrice = (TextView) findViewById(R.id.textProductViewPrice);
        this.textProductViewDescription = (TextView) findViewById(R.id.textProductViewDescription);
        this.textQuantity = (TextView) findViewById(R.id.textQuantity);
        this.radioProductModels = (RadioGroup) findViewById(R.id.radioProductModels);
        this.isExistingInCart = false;

        initializeRadioGroup();
        initialize();
    }

    private void initializeRadioGroup() {
        for (Product model : models) {
            RadioButton rbModel = new RadioButton(this);
            rbModel.setText(model.getModel());
            rbModel.setId(View.generateViewId());

            RadioGroup.LayoutParams radioGroupLayout = new RadioGroup.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            this.radioProductModels.addView(rbModel, radioGroupLayout);
        }
        this.radioProductModels.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    if (radioGroup.getChildAt(i).getId() == id) {
                        selectedModel = models.get(i);
                        initialize();
                        break;
                    }
                }
            }
        });
        ((RadioButton)radioProductModels.getChildAt(0)).setChecked(true);
    }

    private void initialize() {
        String imgUrl = Properties.SERVER_URL + "/assets/product_img/" + this.selectedModel.getProduct_img();
        Glide.with(this)
                .load(imgUrl)
                .placeholder(R.mipmap.banner)
                .error(R.mipmap.banner)
                .centerCrop()
                .into(this.imageProductViewImage);

        this.textProductViewName.setText(this.selectedModel.getProduct_name());
        this.textProductViewPrice.setText(Properties.PESO_SIGN.concat(String.format(Locale.US, "%.2f", this.selectedModel.getSell_price())));
        this.textProductViewDescription.setText(this.selectedModel.getProduct_description());

        CartUtil cart = CartUtil.getInstance(this);
        isExistingInCart = cart.isProductExist(user.getId(), selectedModel.getProduct_id());

        if (isExistingInCart) {
            loadCartDetails();
        } else {
            setQuantity(1);
        }
    }

    private void loadCartDetails() {
        CartUtil cart = CartUtil.getInstance(this);

        // Check if product is existing in cart
        if (isExistingInCart) {
            Cursor resultSet = cart.getProduct(user.getId(), selectedModel.getProduct_id());
            // get product qty from cart then multiply to sell price
            this.editProductInstruction.setText(instruction == null ? "" : instruction);
            getProductInfoFromCart(resultSet);
            this.subtotal = qty * selectedModel.getSell_price();
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
        this.subtotal = quantity * selectedModel.getSell_price();
        if (isExistingInCart) {
            this.buttonAddToCart.setText(Properties.UPDATE_CART.concat(
                String.format(Locale.US, "%s %.2f", Properties.PESO_SIGN, subtotal)));
        } else {
            this.buttonAddToCart.setText(Properties.ADD_TO_CART.concat(
                    String.format(Locale.US, "%s %.2f", Properties.PESO_SIGN, subtotal)));
        }
    }

    private void getProductInfoFromCart(Cursor resultSet) {
        if (resultSet.moveToFirst()) {
            do {
                qty = resultSet.getInt(2) ;//quantity
                instruction = resultSet.getString(7); // instruction
            } while (resultSet.moveToNext());
        }
    }

    public void addToCartOnClick(View view) {
        CartUtil cart = CartUtil.getInstance(this);
        try {
            int user_id = user.getId();
            int product_id = selectedModel.getProduct_id();
            int qty = Integer.parseInt(textQuantity.getText().toString());
            String product_name = selectedModel.getProduct_name();
            float product_price = selectedModel.getSell_price();
            float sell_price = selectedModel.getSell_price();
            String model = selectedModel.getModel();
            String instruction = editProductInstruction.getText().toString();
            if (cart.isProductExist(user.getId(), selectedModel.getProduct_id())) {
                // Update product
                cart.updateProduct(user_id, product_id, qty, instruction);
                Toast.makeText(this, "Successfully updated", Toast.LENGTH_LONG).show();

                // Back to cart
                if (productId != 0) {
                    Intent cartIntent = new Intent("android.intent.action.CART");
                    cartIntent.putExtra("VenteaUser", user);
                    startActivity(cartIntent);
                    setResult(1);
                    super.onStop();
                    finish();
                }
            } else {
                // Add to cart
                if (cart.addToCart(user_id, product_id, qty, product_name, product_price, sell_price, model, instruction)) {
                    Toast.makeText(this, "Successfully added to cart", Toast.LENGTH_LONG).show();
                    isExistingInCart = true;
                    setQuantity(qty);
                } else {
                    Toast.makeText(this, "Error adding to cart", Toast.LENGTH_LONG).show();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Toast.makeText(this, "Error adding to cart", Toast.LENGTH_LONG).show();
        }
    }

    public void btnRemoveOnClick(View view) {
        CartUtil cart = CartUtil.getInstance(this);
        cart.removeProduct(user.getId(), selectedModel.getProduct_id(), selectedModel.getModel());
        Toast.makeText(this, "Successfully removed item from cart", Toast.LENGTH_LONG).show();

        // Back to cart
        Intent cartIntent = new Intent("android.intent.action.CART");
        cartIntent.putExtra("VenteaUser", user);
        startActivity(cartIntent);
        setResult(1);
        super.onStop();
        finish();
    }

    public void closeOnClick(View view) {
        finish();
    }

}