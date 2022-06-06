package com.example.androidrecupracticarroom;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidrecupracticarroom.data.Dato;
import com.example.androidrecupracticarroom.data.RoomDB;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText et;
    Button bAdd, bReset;
    RecyclerView recycler;
    List<Dato> datos;
    LinearLayoutManager manager;
    RoomDB db;
    DatoRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.et_main);
        bAdd = findViewById(R.id.b_add_main);
        bReset = findViewById(R.id.b_reset_main);
        recycler = findViewById(R.id.rv_main);

        db = RoomDB.getInstance(this);
        datos = db.mainDao().findAll();

        manager = new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);

        adapter = new DatoRecyclerAdapter(datos, this);
        recycler.setAdapter(adapter);

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = et.getText().toString().trim();
                if (!text.equals("")) {
                    Dato dato = new Dato();
                    dato.setText(text);

                    db.mainDao().insert(dato);

                    et.setText("");
                    datos.clear();
                    datos.addAll(db.mainDao().findAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        bReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.mainDao().reset(datos);
                datos.clear();
                datos.addAll(db.mainDao().findAll());
                adapter.notifyDataSetChanged();
            }
        });
    }
}