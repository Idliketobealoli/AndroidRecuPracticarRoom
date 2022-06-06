package com.example.androidrecupracticarroom.data;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Dato.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    //creamos instancia de la base de datos
    private static RoomDB db;

    //creamos el nombre de la base de datos
    private static String DATABASE_NAME = "database";

    //creamos el dao  --- Parece ser que los parentesis en el nombre del dao son necesarios.
    public abstract DatoDao mainDao();


    public synchronized static RoomDB getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }
}
