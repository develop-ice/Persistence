package com.android.bdroom.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.bdroom.R;
import com.android.bdroom.ui.screen_help.HelpFragment;
import com.android.bdroom.ui.screen_products.ProductsFragment;
import com.android.bdroom.ui.screen_shared_preferences.SharedPreferencesFragment;
import com.android.bdroom.ui.screen_to_do.ToDoFragment;

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

        changeFragment(new SharedPreferencesFragment(), getResources().getString(R.string.shared_preferences));
        navigationView.setCheckedItem(R.id.nav_shared_preferences);

        // Saludo desde las SharedPreferences
        SharedPreferences prefs =
                getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String saved_name = prefs.getString(getString(R.string.saved_name), "");
        if (!saved_name.isEmpty()) {
            Toast.makeText(this, "Hola " + saved_name, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            hideKeyboard();
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportActionBar().getTitle().equals(getResources().getString(R.string.shared_preferences))) {
                super.onBackPressed();
            } else {
                changeFragment(new SharedPreferencesFragment(), getResources().getString(R.string.shared_preferences));
                navigationView.setCheckedItem(R.id.nav_shared_preferences);
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
            case R.id.nav_shared_preferences:
                changeFragment(new SharedPreferencesFragment(), getResources().getString(R.string.shared_preferences));
                break;
            case R.id.nav_todo:
                changeFragment(new ToDoFragment(), getResources().getString(R.string.todo));
                break;
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

    private void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }

}
