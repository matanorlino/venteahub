package com.dehaja.venteahubmilktea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.util.constants.Properties;

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
        startActivity(mainCustomerIntent);

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

}