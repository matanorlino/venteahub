package com.dehaja.venteahubmilktea.ui.cart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.OrderItem;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.ui.driver.OrderItemsAdapter;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.dehaja.venteahubmilktea.util.constants.Validator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class DriverSearchActivity extends AppCompatActivity {

    private VenteaUser user;
    private int orderId;

    private TextView txtStatus;

    private Timer timer;
    private TimerTask timerTask;
    final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_search);

        Intent checkoutIntent = getIntent();
        user = (VenteaUser) checkoutIntent.getSerializableExtra("VenteaUser");
        orderId = checkoutIntent.getIntExtra("orderId", 0);

        txtStatus = findViewById(R.id.txtStatus);
        txtStatus.setText("Waiting for Driver...");

        waitForDriver();
    }

    private void waitForDriver() {
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 0, 1000);
        timerTask.run();
    }

    private void initializeTimerTask() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(() -> {
                    String url = Properties.SERVER_URL + "api/App_Get_Order_Status.php?order_id=" + orderId;

                    RequestQueue q = Volley.newRequestQueue(getApplicationContext());
                    StringRequest jsonObjRequest = new StringRequest(Request.Method.GET,
                            url,
                            response -> {
                                try {
                                    JSONObject res = new JSONObject(response);
                                    if (Validator.isResponseSuccess(res.getString("response"))) {
                                        JSONArray data = res.getJSONArray("data");
                                        for (int i = 0; i < data.length(); i++) {
                                            JSONObject obj = data.getJSONObject(i);

                                            if (obj.getString("state").equals(Properties.DELIVERING)) {
                                                Toast.makeText(getApplicationContext(), "Driver found! Go to Orders to track your order.", Toast.LENGTH_LONG).show();
                                                timer.cancel();
                                                timer.purge();
                                                finish();
                                            }
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }, error -> {
                                error.printStackTrace();
                            });
                    q.add(jsonObjRequest);
                });
            }
        };
    }
}