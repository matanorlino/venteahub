package com.dehaja.venteahubmilktea.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.dehaja.venteahubmilktea.util.constants.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.sapereaude.maskedEditText.MaskedEditText;

public class RegisterActivity extends AppCompatActivity {
    private final String CHANNEL_NAME = "VenteaHub";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createNotificationChannel(CHANNEL_NAME);
    }

    public void submitOnClick(View view) {
        System.out.println("view" + view.toString());
        // Activity Fields
        String txtEmail = ((EditText)findViewById(R.id.txtUsername)).getText().toString();
        String txtUsername = ((EditText)findViewById(R.id.txtUsername)).getText().toString();
        String txtPassword = ((EditText)findViewById(R.id.txtPassword)).getText().toString();
        String txtContactNo = ((MaskedEditText)findViewById(R.id.txtContactNo)).getText().toString();
        String swAccessLvl = ((Switch)findViewById(R.id.swAccsLvl)).isChecked()? Properties.DRIVER : Properties.CUSTOMER;
        System.out.println("Validator isEmpty: " + Validator.isEmpty(txtEmail, txtUsername, txtPassword, txtContactNo, swAccessLvl));
        if (Validator.isEmpty(txtEmail, txtUsername, txtPassword, txtContactNo, swAccessLvl)) {
            Toast.makeText(getApplicationContext(), "All fields are required.", Toast.LENGTH_LONG).show();
        } else {
            validateRegister(txtEmail, txtUsername, txtPassword, txtContactNo, swAccessLvl);
        }
    }

    private void showNotification(String channel_name, String title, String message) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(RegisterActivity.this, channel_name);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(android.R.drawable.star_on);
        builder.setAutoCancel(true);
        NotificationManagerCompat manager = NotificationManagerCompat.from(RegisterActivity.this);
        manager.notify(1, builder.build());
    }

    private void createNotificationChannel(String channel_id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channel_id, CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    private void validateRegister(String email, String username, String password, String contact_no, String accesslevel) {
        String url = Properties.SERVER_URL + "api/App_Register.php";
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
                                String msg = String.format("%s has succesfully created!", username);
                                showNotification(CHANNEL_NAME, "Ventea Hub Miltea", msg);
                                moveToLogin();
                            } else {
                                Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_LONG).show();
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
                params.put("email", email);
                params.put("username", username);
                params.put("password", password);
                params.put("contact_no", contact_no);
                params.put("accesslevel", accesslevel);
                return params;
            }
        };
        q.add(jsonObjRequest);
    }

    private void moveToLogin() {
        // Intent declaration
        Intent submitIntent = new Intent("android.intent.action.LOGIN");
        submitIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(submitIntent);
    }


}