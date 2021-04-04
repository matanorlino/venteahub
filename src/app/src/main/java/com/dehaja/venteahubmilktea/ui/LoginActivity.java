package com.dehaja.venteahubmilktea.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.dehaja.venteahubmilktea.util.constants.Validator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private VenteaUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        user = new VenteaUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createConfigFile();
    }

    public void registerOnClick(View view) {
        Intent registerIntent = new Intent("android.intent.action.REGISTER");
        registerIntent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        startActivity(registerIntent);
    }

    public void loginOnClick(View view) {
        String username = ((EditText)findViewById(R.id.txtUsername)).getText().toString();
        String password = ((EditText)findViewById(R.id.txtPassword)).getText().toString();

        if (Validator.isEmpty(username, password)) {
            Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_LONG).show();
        } else {
            validateLogin(username, password);
        }
    }

    private void validateLogin(String username, String password) {
        String url = Properties.SERVER_URL + "api/app_login.php";
        System.out.println("VALIDATE LOGIN: " + url);
        RequestQueue q = Volley.newRequestQueue(this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            if (Validator.isResponseSuccess(res.getString("response"))) {
                                res = res.getJSONObject("data");
                                user.setId(res.getInt("id"));
                                user.setUsername(res.getString("username"));
                                user.setPassword(res.getString("password"));
                                user.setEmail(res.getString("email"));
                                user.setContact_no(res.getString("contact_no"));
                                user.setAccesslevel(res.getString("accesslevel"));
                                validateUser(user);
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
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        q.add(jsonObjRequest);
    }

    private void validateUser(VenteaUser user) {
        if (user.getAccesslevel().equalsIgnoreCase(Properties.CUSTOMER) ||
            user.getAccesslevel().equalsIgnoreCase(Properties.DRIVER)) {
                moveToMainMenu(user);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_LONG).show();
        }
    }

    private void moveToCustomerMenu(VenteaUser user) {
        // Move to Customer Menu
        Intent mainCustomerIntent = new Intent("android.intent.action.CUSTOMER");
        mainCustomerIntent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        mainCustomerIntent.putExtra("VenteaUser", user);
        startActivity(mainCustomerIntent);
        finish();
    }

    private void moveToMainMenu(VenteaUser user) {
        Intent mainCustomerIntent = new Intent("android.intent.action.CUSTOMER");
        mainCustomerIntent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        mainCustomerIntent.putExtra("VenteaUser", user);
        startActivity(mainCustomerIntent);
        finish();
    }

    private void createConfigFile() {
        String filename = "config.properties";
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getApplicationContext().getPackageName();
        String packageName = getApplicationContext().getPackageName();
        String comment = "CONFIG DETAILS \n " +
                         " scheme -> set to either http or https. Default, http is set. \n" +
                         " host -> server url or ip address. Default, localhost. \n" +
                         " port -> api server's port. Default, null. \n" +
                         " api_server -> api's entry point. Default, commission" ;

        java.util.Properties prop = new java.util.Properties();
        prop.setProperty("scheme", "http://");
        prop.setProperty("host", "localhost");
        prop.setProperty("port", "");
        prop.setProperty("api_server", "commission");

        File file = new File(getExternalFilesDir(""), "config");
        try {
            if (!file.exists()) {
                if (file.mkdirs()) {
                    file = new File(getExternalFilesDir("config"), filename);
                    if (file.createNewFile()) {
                        FileOutputStream fos = new FileOutputStream(file,true);
                        prop.store(fos, comment);
                        fos.close();
                        setServerUrl(prop);
                    }
                }
            } else {
                file = new File(getExternalFilesDir("config"), filename);
                FileInputStream fis = new FileInputStream(file);
                prop.load(fis);
                setServerUrl(prop);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setServerUrl(java.util.Properties prop) {
        StringBuilder sb = new StringBuilder();
        sb.append(prop.getProperty("scheme"));
        sb.append(prop.getProperty("host"));
        sb.append(prop.getProperty("port") == null || prop.getProperty("port").isEmpty() ? "" : ":" + prop.getProperty("port"));
        sb.append("/" + prop.getProperty("api_server") + "/");
        Properties.SERVER_URL = sb.toString();
        System.out.println("SERVER URL: " + sb.toString());
    }
}