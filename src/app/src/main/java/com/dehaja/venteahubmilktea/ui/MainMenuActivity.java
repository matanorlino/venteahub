package com.dehaja.venteahubmilktea.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainMenuActivity extends AppCompatActivity {
    private VenteaUser user;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Kebab menu
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        Intent intent = getIntent();
        this.user =  (VenteaUser) intent.getSerializableExtra("VenteaUser");

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

}