package com.dehaja.venteahubmilktea.ui.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.CartItem;
import com.dehaja.venteahubmilktea.models.Product;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.dehaja.venteahubmilktea.util.constants.Validator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartAdapter extends ArrayAdapter<CartItem> {

    private Button btnCartQty;
    private CartItem cartItem;
    private TextView txtCartProductName;
    private TextView txtCartPrice;
    private Product product;

    public CartAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CartItem> cartItems) {
        super(context, resource, cartItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        this.cartItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_item_cart, parent, false);
        }

        this.btnCartQty = (Button) convertView.findViewById(R.id.btnCartQty);
        this.txtCartProductName = (TextView) convertView.findViewById(R.id.txtCartProductName);
        this.txtCartPrice = (TextView) convertView.findViewById(R.id.txtCartPrice);

        getProduct(cartItem.getProductId(), new VolleyCallback() {
            @Override
            public void onSuccess(Product p) {
                product = p;
                setValues();
            }
        });

        return convertView;
    }

    private void setValues() {
        btnCartQty.setText(String.valueOf(cartItem.getQuantity()));
        txtCartProductName.setText(product.getProduct_name());
        txtCartPrice.setText(Properties.PESO_SIGN.concat(String.valueOf(cartItem.getQuantity() * product.getSell_price())));
    }

    private void getProduct(int productId, final VolleyCallback callback) {
        String url = Properties.SERVER_URL + "api/App_Products.php";
        RequestQueue q = Volley.newRequestQueue(getContext());

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
                                        System.out.println("getProducts");
                                        callback.onSuccess(new Product(
                                                obj.getInt("product_id"),
                                                obj.getInt("product_category_id"),
                                                Float.parseFloat(obj.getString("market_price")),
                                                Float.parseFloat(obj.getString("sell_price")),
                                                obj.getString("product_code"),
                                                obj.getString("product_img"),
                                                obj.getString("model"),
                                                obj.getString("purchase_description"),
                                                obj.getString("product_description"),
                                                obj.getString("status"),
                                                obj.getString("product_name")
                                        ));
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("product_id", String.valueOf(productId));
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        q.add(jsonObjRequest);
    }
}

interface VolleyCallback{
    void onSuccess(Product product);
}
