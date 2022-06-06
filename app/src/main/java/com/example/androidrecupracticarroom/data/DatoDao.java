package com.example.androidrecupracticarroom.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DatoDao {
    @Insert(onConflict = REPLACE)
    void insert(Dato dato);

    @Delete
    void delete(Dato dato);

    @Delete
    void reset(List<Dato> datos);

    @Query("UPDATE table_name SET texto = :sText WHERE id = :sid")
    void update(int sid, String sText);

    @Query("SELECT * FROM table_name")
    List<Dato> findAll();
}
