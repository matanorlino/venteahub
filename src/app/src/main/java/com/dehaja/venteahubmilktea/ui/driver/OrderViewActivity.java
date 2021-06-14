package com.dehaja.venteahubmilktea.ui.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.Order;
import com.dehaja.venteahubmilktea.models.OrderItem;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.dehaja.venteahubmilktea.util.constants.Validator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderViewActivity extends AppCompatActivity {
    List<OrderItem> orderItems;
    VenteaUser user;
    Order order;
    int order_id;
    RecyclerView recyclerView;
    TextView txtUsername;
    TextView txtGrandTotal;
    Button btnOrderViewAccept;
    OrderItemsAdapter orderItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_view);
        // get value from extra
        user = (VenteaUser) getIntent().getSerializableExtra("VenteaUser");
        order_id = getIntent().getIntExtra("order_id", 0);
        System.out.println("order_id: " + order_id);
        recyclerView = findViewById(R.id.recOrderView);
        btnOrderViewAccept = findViewById(R.id.btnOrderViewAccept);
        txtUsername = findViewById(R.id.txtOrderViewUsername);
        txtGrandTotal = findViewById(R.id.txtOrderViewGrandTotal);
        initialize();
    }

    public void initialize() {
        orderItems = new ArrayList<OrderItem>();
        btnOrderViewAccept.setVisibility(View.VISIBLE);
        if (user.getAccesslevel().equals(Properties.CUSTOMER)) {
            btnOrderViewAccept.setVisibility(View.INVISIBLE);
        }
        getOrderItems(this);
    }

    private void getOrderItems(Context context) {
        String url = "";
        if (user.getAccesslevel().equals(Properties.DRIVER)) {
            url = Properties.SERVER_URL + "api/App_Get_Order_Item.php?order_id="+order_id;
        } else {
            url = Properties.SERVER_URL + "api/App_Get_Order_Item.php?order_id=" + order_id
                    + "&state=" + Properties.DELIVERING;
        }

        RequestQueue q = Volley.newRequestQueue(context);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            if (Validator.isResponseSuccess(res.getString("response"))) {
                                JSONArray data = res.getJSONArray("data");
                                float grandTotal = 0;
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < data.length(); i++) {
                                    sb.delete(0, sb.length());
                                    JSONObject obj = data.getJSONObject(i);
                                    orderItems.add(new OrderItem(
                                            obj.getInt("user_id"),
                                            obj.getInt("order_id"),
                                            obj.getInt("product_id"),
                                            obj.getString("username"),
                                            obj.getString("contact_no"),
                                            obj.getString("date"),
                                            obj.getString("product_img"),
                                            obj.getString("product_name"),
                                            obj.getString("address"),
                                            obj.getInt("qty"),
                                            (float)obj.getDouble("sell_price"),
                                            (float)obj.getDouble("sub_total"),
                                            obj.getString("state"),
                                            obj.getString("request")
                                    ));
                                    grandTotal += (obj.getInt("qty") * (float)obj.getDouble("sell_price"));
                                    sb.append(obj.getString("username"));
                                }

                                // Set adapter to recycler view
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(layoutManager);
                                orderItemsAdapter = new OrderItemsAdapter(getApplicationContext(), orderItems);
                                recyclerView.setAdapter(orderItemsAdapter);
                                txtUsername.setText(sb.toString());
                                txtGrandTotal.setText(String.format("%s %.2f", Properties.PESO_SIGN, grandTotal));
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to load Order list", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Failed to load Order list", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Failed to load Order list", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        q.add(jsonObjRequest);
    }

    public void closeOnClick(View view) {
        this.finish();
    }

    public void acceptOnClick(View view) {
        String url = Properties.SERVER_URL + "api/App_Update_Order_Status.php";
        RequestQueue q = Volley.newRequestQueue(this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("response: " + response);
                            JSONObject res = new JSONObject(response);

                            if (Validator.isResponseSuccess(res.getString("response"))) {
                                String msg = String.format("Order status has been updated to %s", Properties.DELIVERING);
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                // move to maps
                                closeOnClick(view);
                            } else {
                                Toast.makeText(getApplicationContext(), "Error accepting order", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Error accepting order", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", String.valueOf(user.getId()));
                params.put("state", Properties.DELIVERING);
                params.put("order_id", String.valueOf(order_id));
                return params;
            }
        };
        q.add(jsonObjRequest);
    }

}