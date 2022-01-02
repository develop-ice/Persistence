package com.android.bdroom.ui.screen_products;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.android.bdroom.R;
import com.android.bdroom.persistence.entity.Producto;
import com.android.bdroom.ui.screen_products_crud.ProductoCrudActivity;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.android.bdroom.ui.screen_products_crud.ProductoCrudActivity.EXTRA_ID;
import static com.android.bdroom.ui.screen_products_crud.ProductoCrudActivity.EXTRA_ITEM;

public class ProductsFragment extends Fragment {

    public static final int ADD_REQUEST = 1;
    public static final int UPDATE_REQUEST = 2;
    private ProductsViewModel viewModel;
    private ProductoAdapter adapter;

    private View rootView;

    public ProductsFragment() {
        // Required empty public constructor
    }

    @NonNull
    public static ProductsFragment newInstance() {
        return new ProductsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_products, container, false);
        init();
        search();
        return rootView;
    }

    /**
     * INIT
     */
    private void init() {
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new ProductoAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = ViewModelProviders.of(getActivity()).get(ProductsViewModel.class);

        // SHOW
        viewModel.getAllProductos().observe(this, new Observer<List<Producto>>() {
            @Override
            public void onChanged(@Nullable List<Producto> productos) {
                adapter.setProductos(productos);
            }
        });

        // ADD
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProductoCrudActivity.class);
                startActivityForResult(intent, ADD_REQUEST);
            }
        });

        // DELETE
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.deleteProducto(adapter.getProductoAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Eliminado", Toast.LENGTH_SHORT).show();
            }

        }).attachToRecyclerView(recyclerView);

        // EDIT
        adapter.setOnItemClickListener(new ProductoAdapter.OnItemClicklistener() {
            @Override
            public void OnItemClick(Producto producto) {
                Intent intent = new Intent(getContext(), ProductoCrudActivity.class);
                intent.putExtra(EXTRA_ITEM, producto);
                intent.putExtra(EXTRA_ID, producto.getId());
                startActivityForResult(intent, UPDATE_REQUEST);
            }
        });
    }

    private void search() {
        EditText etBuscar = rootView.findViewById(R.id.et_buscar);
        etBuscar.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(final String s) {
        viewModel.getAllProductos().observe(this, new Observer<List<Producto>>() {
            @Override
            public void onChanged(@Nullable List<Producto> productos) {
                ArrayList<Producto> filteredList = new ArrayList<>();
                for (Producto producto : productos) {
                    if (String.valueOf(producto.getNombre()).contains(s)) {
                        filteredList.add(producto);
                    }
                }
                adapter.filterList(filteredList);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == ADD_REQUEST && resultCode == RESULT_OK) {
            Producto producto = (Producto) data.getExtras().getSerializable(EXTRA_ITEM);

            viewModel.insertProducto(producto);

            Toast.makeText(getContext(), "Guardado correctamente", Toast.LENGTH_SHORT).show();

        } else if (requestCode == UPDATE_REQUEST && resultCode == RESULT_OK) {
            Producto producto = (Producto) data.getExtras().getSerializable(EXTRA_ITEM);

            int id = data.getIntExtra(EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(getContext(), "No pudo editarse", Toast.LENGTH_SHORT).show();
                return;
            }

            assert producto != null;
            producto.setId(id);
            viewModel.updateProducto(producto);
            Toast.makeText(getContext(), "Actualizado correctamente", Toast.LENGTH_SHORT).show();

        } else {
            // Cancelado
            return;
        }
    }

}
