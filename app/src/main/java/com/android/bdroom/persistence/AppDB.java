package com.android.bdroom.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.android.bdroom.persistence.dao.ProductoDao;
import com.android.bdroom.persistence.entity.Producto;


@Database(entities = {Producto.class}, version = 1)
public abstract class AppDB extends RoomDatabase {

    private static AppDB instance;

    public abstract ProductoDao productoDao();

    public static synchronized AppDB getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDB.class,
                    "app_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
