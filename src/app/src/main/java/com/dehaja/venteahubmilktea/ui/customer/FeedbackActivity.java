package com.dehaja.venteahubmilktea.ui.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.ui.driver.OrderAdapter;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.dehaja.venteahubmilktea.util.constants.Validator;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {
    private VenteaUser user;
    private String order_id;
    private EditText txtFeedback;
    private Button btnFBSubmit;

    private boolean hasFeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        user = (VenteaUser) getIntent().getSerializableExtra("VenteaUser");
        order_id = getIntent().getStringExtra("order_id");
        hasFeedback = false;

        txtFeedback = (EditText) findViewById(R.id.txtFeedback);
        btnFBSubmit = (Button) findViewById(R.id.btnFBSubmit);
        btnFBSubmit.setEnabled(true);
        btnFBSubmit.setOnClickListener(v -> submitFeedBack(v));
        getFeedBack();
    }

    public void getFeedBack() {
        String url = Properties.SERVER_URL + "api/App_Get_Order_Feedback.php?order_id=" + order_id;
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
                                    txtFeedback.setText(obj.getString("feedback_desc"));
                                    hasFeedback = true;
                                    btnFBSubmit.setEnabled(false);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed to load Feedback", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Failed to load Feedback", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Failed to load Feedback", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        q.add(jsonObjRequest);
    }

    public void submitFeedBack(View view){
        Toast.makeText(getApplicationContext(), order_id, Toast.LENGTH_LONG).show();
        String url = Properties.SERVER_URL + "api/App_Insert_Feedback.php";
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
                                Toast.makeText(getApplicationContext(), "Feedback sent!", Toast.LENGTH_LONG).show();
                                getFeedBack();
                            } else {
                                Toast.makeText(getApplicationContext(), "Failed sending feedback.", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Failed sending feedback.", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Failed sending feedback.", Toast.LENGTH_LONG).show();
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
                params.put("order_id", order_id.trim());
                params.put("feedback", txtFeedback.getText().toString());
                return params;
            }
        };
        q.add(jsonObjRequest);
    }

}