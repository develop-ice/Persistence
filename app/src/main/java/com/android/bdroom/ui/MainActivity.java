package com.android.bdroom.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.bdroom.R;
import com.android.bdroom.ui.screen_help.HelpFragment;
import com.android.bdroom.ui.screen_products.ProductsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    // UI
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        changeFragment(new ProductsFragment(), getResources().getString(R.string.products));
        navigationView.setCheckedItem(R.id.nav_products);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportActionBar().getTitle().equals(getResources().getString(R.string.products))) {
                super.onBackPressed();
            } else {
                changeFragment(new ProductsFragment(), getResources().getString(R.string.products));
                navigationView.setCheckedItem(R.id.nav_products);
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment destination = null;
        String title = "";
        boolean isFragmentSelected = false;

        switch (id) {
            case R.id.nav_products:
                changeFragment(new ProductsFragment(), getResources().getString(R.string.products));
                break;
            case R.id.nav_help:
                changeFragment(new HelpFragment(), getResources().getString(R.string.help));
                break;
            case R.id.nav_share:
                Toast.makeText(getApplicationContext(), "Share...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_exit:
                finish();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        if (isFragmentSelected) changeFragment(destination, title);

        return true;
    }

    private void changeFragment(Fragment destination, String title) {
        getSupportActionBar().setTitle(title);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, destination).commit();
    }

}