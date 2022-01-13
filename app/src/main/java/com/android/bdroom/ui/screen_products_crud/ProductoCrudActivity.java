package com.android.bdroom.ui.screen_products_crud;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.bdroom.R;
import com.android.bdroom.persistence.entity.Producto;


public class ProductoCrudActivity extends AppCompatActivity {

    public static final String EXTRA_ITEM = "EXTRA_ITEM";
    public static final String EXTRA_ID = "ID";

    private EditText etNombre, etCant;

    private Producto producto;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_crud);

        // Back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        init();

        if (getIntent().hasExtra(EXTRA_ID)) {
            getSupportActionBar().setTitle("Editar");

            producto = (Producto) getIntent().getExtras().getSerializable(EXTRA_ITEM);

            etNombre.setText(producto.getNombre());
            etCant.setText(producto.getCantidad() + "");

        } else {
            getSupportActionBar().setTitle("Nuevo");
        }
    }

    private void init() {
        etNombre = findViewById(R.id.et_nombre);
        etCant = findViewById(R.id.et_cant);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_crud, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ok:
                verificarDatos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void verificarDatos() {
        if (etNombre.getText().toString().trim().isEmpty() ||
                etCant.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Existen campos vacios", Toast.LENGTH_LONG).show();
            return;
        }
        guardarDatos(new Producto(
                etNombre.getText().toString(),
                Integer.parseInt(etCant.getText().toString()))
        );
    }

    private void guardarDatos(Producto prod) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_ITEM, prod);
        intent.putExtra(EXTRA_ID, prod.getId());

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            intent.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, intent);
        finish();
    }
}

