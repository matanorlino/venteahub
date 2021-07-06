package com.dehaja.venteahubmilktea.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.ui.driver.OrderFragment;
import com.dehaja.venteahubmilktea.util.cart.CartUtil;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.dehaja.venteahubmilktea.util.constants.Validator;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainMenuActivity extends AppCompatActivity {
    private VenteaUser user;
    private AppBarConfiguration mAppBarConfiguration;
    private SQLiteDatabase database;
    private FusedLocationProviderClient fusedLocationClient;
    private Location loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Kebab menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        Intent intent = getIntent();
        this.user = (VenteaUser) intent.getSerializableExtra("VenteaUser");

        if (!this.user.getAccesslevel().equalsIgnoreCase(Properties.CUSTOMER)) {
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setVisibility(View.GONE);
            fab.hide();
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        }

        // Set nav details
        setNavHeaderInfo(navigationView.getHeaderView(0));

        // Set menu items
        navigationView.getMenu().clear();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        if (this.user.getAccesslevel().equals(Properties.CUSTOMER)) {
            navigationView.inflateMenu(R.menu.activity_main_drawer_customer);
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_product, R.id.nav_order_history, R.id.nav_account, R.id.nav_history)
                    .setDrawerLayout(drawer)
                    .build();

            navController.setGraph(R.navigation.mobile_navigation_customer);
        } else {
            navigationView.inflateMenu(R.menu.activity_main_drawer_driver);
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_order, R.id.nav_delivery, R.id.nav_account)
                    .setDrawerLayout(drawer)
                    .build();

            navController.setGraph(R.navigation.mobile_navigation_driver);
        }

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        initializeSQLiteDB();
    }

    private void initializeSQLiteDB() {
        CartUtil cart = CartUtil.getInstance(this);
        cart.createCartTable();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_customer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setNavHeaderInfo(View nv) {
        TextView navUsername = nv.findViewById(R.id.navUsername);
        TextView navContactInfo = nv.findViewById(R.id.navContactInfo);

        navUsername.setText(user.getUsername());
        navContactInfo.setText(String.format("%s | %s", user.getContact_no(), user.getEmail()));
    }

    public void onClickLogout(MenuItem item) {
        // reset/remove value
        getIntent().removeExtra("VenteaUser");
        user = null;

        // Intent declaration
        Intent logoutIntent = new Intent("android.intent.action.LOGIN");
        logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(logoutIntent);
        finish();
    }

    public void cartOnClick(View view) {
        Intent cartIntent = new Intent("android.intent.action.CART");
        cartIntent.putExtra("VenteaUser", user);
        startActivity(cartIntent);
    }

    public void onClickViewOrder(View view) {
        Intent viewOrderIntent = new Intent("android.intent.action.ORDER_VIEW");
        viewOrderIntent.putExtra("order_id", Integer.parseInt(view.getContentDescription().toString()));
        viewOrderIntent.putExtra("VenteaUser", user);
        startActivity(viewOrderIntent);
    }

    public void acceptOnClick(View view) {
        String url = Properties.SERVER_URL + "api/App_Update_Order_Status.php";
        int order_id = Integer.parseInt(String.valueOf(view.getContentDescription()));
        RequestQueue q = Volley.newRequestQueue(getApplicationContext());
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
                                if (loc == null) {
                                    // Update driver's loc
                                    updateDriverLoc(view, user.getId(), order_id, -1, -1);
                                } else {
                                    // Update driver's loc
                                    updateDriverLoc(view, user.getId(), order_id, loc.getLatitude(), loc.getLongitude());
                                }
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

    private void updateDriverLoc(View view, int user_id, int order_id, double latitude, double longitude) {
        String url = Properties.SERVER_URL + "api/App_Update_Driver_Loc.php";
        RequestQueue q = Volley.newRequestQueue(this);
        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject res = new JSONObject(response);
                            if (Validator.isResponseSuccess(res.getString("response"))) {
                                // move to maps
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Failed updating driver location", Toast.LENGTH_LONG).show();
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
                params.put("flag", "i");
                params.put("delivered_by", String.valueOf(user_id));
                params.put("order_id", String.valueOf(order_id));
                if (latitude != -1 && longitude != -1) {
                    params.put("latitude", String.valueOf(latitude));
                    params.put("longitude", String.valueOf(longitude));
                }
                return params;
            }
        };
        q.add(jsonObjRequest);
    }

    private Location getLastKnowLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return loc;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                loc = location;
                return;
            }
        });
        return loc;
    }

    public void onClickBtnFeedback(View view) {
        Intent feedbackIntent = new Intent("android.intent.action.FEEDBACK");
        feedbackIntent.putExtra("VenteaUser", user);
        feedbackIntent.putExtra("order_id", view.getContentDescription());

        startActivity(feedbackIntent);
    }
}