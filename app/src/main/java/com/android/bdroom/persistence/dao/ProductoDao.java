package com.android.bdroom.persistence.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.android.bdroom.persistence.entity.Producto;

import java.util.List;


@Dao
public interface ProductoDao {

    @Insert
    void insert(Producto producto);

    @Update
    void update(Producto producto);

    @Delete
    void delete(Producto producto);

    @Query("SELECT * FROM producto ORDER BY id DESC")
    LiveData<List<Producto>> getAll();

}
