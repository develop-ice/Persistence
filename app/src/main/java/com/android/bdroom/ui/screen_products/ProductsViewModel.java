package com.android.bdroom.ui.screen_products;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.android.bdroom.persistence.entity.Producto;
import com.android.bdroom.repository.ProductoRepository;

import java.util.List;

public class ProductsViewModel extends AndroidViewModel {
    private ProductoRepository productoRepository;
    private LiveData<List<Producto>> productos;

    public ProductsViewModel(@NonNull Application application) {
        super(application);
        productoRepository = new ProductoRepository(application);
        productos = productoRepository.getAll();
    }

    public void insertProducto(Producto producto) {
        productoRepository.insert(producto);
    }

    public void updateProducto(Producto producto) {
        productoRepository.update(producto);
    }

    public void deleteProducto(Producto producto) {
        productoRepository.delete(producto);
    }

    public LiveData<List<Producto>> getAllProductos() {
        return productos;
    }
}
