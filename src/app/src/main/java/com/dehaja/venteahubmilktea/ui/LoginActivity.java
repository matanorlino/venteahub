package com.dehaja.venteahubmilktea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.util.constants.Properties;

import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void registerOnClick(View view) {
        Intent registerIntent = new Intent("android.intent.action.REGISTER");
        registerIntent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(registerIntent);
    }

    public void loginOnClick(View view) {
        // Move to Customer Menu
        Intent mainCustomerIntent = new Intent("android.intent.action.CUSTOMER");
        mainCustomerIntent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
//        startActivity(mainCustomerIntent);

        testAPI("123", "123");
        // Move to Driver Menu
    }

    public VenteaUser validateLogin(String username, String password) {
        VenteaUser loginUser = new VenteaUser();

        StringBuilder data = new StringBuilder();
        try {
            // Encode username
            data.append(URLEncoder.encode("username", "UTF-8"));
            data.append("=");
            data.append(URLEncoder.encode(username, "UTF-8"));
            data.append("&");
            // Encode password
            data.append(URLEncoder.encode("password", "UTF-8"));
            data.append("=");
            data.append(URLEncoder.encode(password, "UTF-8"));

            URL url = new URL(Properties.SERVER_URL + "api/app_login.php");
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(data.toString());
            wr.flush();

        } catch (IOException e) {
            loginUser = null;
        }

        return loginUser;
    }

    public void testAPI(String username, String password) {

        String url = Properties.SERVER_URL + "api/app_login.php";
        Toast t = Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG);
        t.show();

        JSONObject postData = new JSONObject();
        try {
            postData.put("username", username);
            postData.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
        (Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Toast t = Toast.makeText(getApplicationContext(), response.getString("response"), Toast.LENGTH_LONG);
                    t.show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Toast t = Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                t.show();
            }
        });
    }
}