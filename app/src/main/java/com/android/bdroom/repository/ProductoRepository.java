package com.android.bdroom.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.android.bdroom.persistence.AppDB;
import com.android.bdroom.persistence.dao.ProductoDao;
import com.android.bdroom.persistence.entity.Producto;

import java.util.List;

public class ProductoRepository {
    private ProductoDao productoDao;
    private LiveData<List<Producto>> allProductos;

    public ProductoRepository(Application application) {
        AppDB appDB = AppDB.getInstance(application);
        productoDao = appDB.productoDao();
        allProductos = productoDao.getAll();
    }

    public void insert(Producto producto) {
        new insertProductoAsyncTask(productoDao).execute(producto);
    }

    public void update(Producto producto) {
        new updateProductoAsyncTask(productoDao).execute(producto);
    }

    public void delete(Producto producto) {
        new deleteProductoAsyncTask(productoDao).execute(producto);
    }

    public LiveData<List<Producto>> getAll() {
        return allProductos;
    }

    private static class insertProductoAsyncTask extends AsyncTask<Producto, Void, Void> {
        private ProductoDao productoDao;

        insertProductoAsyncTask(ProductoDao productoDao) {
            this.productoDao = productoDao;
        }

        @Override
        protected Void doInBackground(Producto... producto) {
            productoDao.insert(producto[0]);
            return null;
        }
    }

    private static class updateProductoAsyncTask extends AsyncTask<Producto, Void, Void> {
        private ProductoDao productoDao;

        updateProductoAsyncTask(ProductoDao productoDao) {
            this.productoDao = productoDao;
        }

        @Override
        protected Void doInBackground(Producto... producto) {
            productoDao.update(producto[0]);
            return null;
        }
    }

    private static class deleteProductoAsyncTask extends AsyncTask<Producto, Void, Void> {
        private ProductoDao productoDao;

        deleteProductoAsyncTask(ProductoDao productoDao) {
            this.productoDao = productoDao;
        }

        @Override
        protected Void doInBackground(Producto... producto) {
            productoDao.delete(producto[0]);
            return null;
        }
    }

}

