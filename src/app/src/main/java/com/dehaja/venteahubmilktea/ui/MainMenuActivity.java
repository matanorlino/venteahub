package com.dehaja.venteahubmilktea.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.dehaja.venteahubmilktea.R;
import com.dehaja.venteahubmilktea.models.VenteaUser;
import com.dehaja.venteahubmilktea.util.constants.CustomerProperties;
import com.dehaja.venteahubmilktea.util.constants.Properties;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.constraintlayout.widget.Group;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;

public class MainMenuActivity extends AppCompatActivity {
    private VenteaUser user;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_customer);
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
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Intent intent = getIntent();
        user =  (VenteaUser) intent.getSerializableExtra("VenteaUser");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        // Inflate the menu; this adds items to the action bar if it is present.
        if (user.getAccesslevel().equalsIgnoreCase(Properties.CUSTOMER)) {
            for (Integer key : CustomerProperties.getCustomerMenu().keySet()) {
                MenuItem item = menu.add(0, key, 0, CustomerProperties.getCustomerMenu().get(key));
                item.setIcon(R.drawable.ic_menu_send);
                item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            }
        }
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