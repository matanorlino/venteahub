package com.dehaja.venteahubmilktea.ui;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.util.cart.CartUtil;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainMenuActivity extends AppCompatActivity {
    private VenteaUser user;
    private AppBarConfiguration mAppBarConfiguration;
    private SQLiteDatabase database;

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
        this.user =  (VenteaUser) intent.getSerializableExtra("VenteaUser");

        if (!this.user.getAccesslevel().equalsIgnoreCase(Properties.CUSTOMER)) {
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setVisibility(View.GONE);
            fab.hide();
        }

        // Set nav details
        setNavHeaderInfo(navigationView.getHeaderView(0));

        // Set menu items
        navigationView.getMenu().clear();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        if (this.user.getAccesslevel().equals(Properties.CUSTOMER)) {
            navigationView.inflateMenu(R.menu.activity_main_drawer_customer);
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_product, R.id.nav_order_history, R.id.nav_account)
                    .setDrawerLayout(drawer)
                    .build();

            navController.setGraph(R.navigation.mobile_navigation_customer);
        } else {
            navigationView.inflateMenu(R.menu.activity_main_drawer_driver);
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_order, R.id.nav_account)
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

}