package com.dehaja.venteahubmilktea.ui.cart;


import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.CartItem;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.util.cart.CartUtil;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.dehaja.venteahubmilktea.util.constants.Validator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private TextView textGCashNumber;
    private TextView textGCashName;
    private LinearLayout linearGCashInfo;
    private TextView textOrderNumber;
    private RadioGroup rgPaymentMethod;
    private RadioButton rbCOD;
    private TextView textTotal;
    private TextView txtAddress;

    private VenteaUser user;
    private float total;
    private ArrayList<CartItem> cartItems;
    private double selectedLat;
    private double selectedLng;

    private String orderNumber;
    private final int LAUNCH_SELECT_ADDRESS_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Intent checkoutIntent = getIntent();
        user = (VenteaUser) checkoutIntent.getSerializableExtra("VenteaUser");
        cartItems = checkoutIntent.getParcelableArrayListExtra("cartItems");
        total = checkoutIntent.getFloatExtra("total", 0);

        textGCashName = findViewById(R.id.textGCashName);
        textGCashNumber = findViewById(R.id.textGCashNumber);
        linearGCashInfo = findViewById(R.id.linearGCashInfo);
        textOrderNumber = findViewById(R.id.textOrderNumber);
        txtAddress = findViewById(R.id.txtAddress);
        rgPaymentMethod = findViewById(R.id.rgPaymentMethod);
        rbCOD = findViewById(R.id.rbCOD);
        textTotal = findViewById(R.id.textTotal);
        textTotal.setText(String.format(Locale.US, "%s %.2f", Properties.PESO_SIGN, total));

        setOrderNumber();
        initializeRadioGroup();


    }

    private void setOrderNumber() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        orderNumber = String.format("%d%s",user.getId(),sdf.format(new Date()));
        textOrderNumber.setText(orderNumber);
    }

    private void initializeRadioGroup() {
        rbCOD.setChecked(true);
        displayGCashInfo(false);
        rgPaymentMethod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.rbGCash) {
                    getGCashInfo();
                    displayGCashInfo(true);
                } else {
                    displayGCashInfo(false);
                }
            }
        });
    }

    private void displayGCashInfo(boolean display) {
        if (display) {
            linearGCashInfo.setVisibility(View.VISIBLE);
        } else {
            linearGCashInfo.setVisibility(View.GONE);
        }
    }

    private void placeOrder() {
        // Close this and cart
        CartUtil cart = CartUtil.getInstance(this);
        cart.clearCart(user.getId());
        setResult(1);
        super.onStop();
        finish();
    }

    public void selectAddress(View view) {
        Intent intent = new Intent(this, SelectAddressActivity.class);
        startActivityForResult(intent, LAUNCH_SELECT_ADDRESS_ACTIVITY);
    }

    private void getGCashInfo() {
        String url = Properties.SERVER_URL + "api/App_GCash_Info.php";
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
                                    textGCashName.setText(obj.getString("name"));
                                    textGCashNumber.setText(obj.getString("number"));
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
        }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        q.add(jsonObjRequest);
    }

    public void closeOnClick(View view) {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SELECT_ADDRESS_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                String addressName=data.getStringExtra("addressName");
                selectedLat = data.getDoubleExtra("lat", 14.28575403726168);
                selectedLng = data.getDoubleExtra("lng", 121.1055055117034);

                txtAddress.setText(addressName);
            }
        }
    }
}