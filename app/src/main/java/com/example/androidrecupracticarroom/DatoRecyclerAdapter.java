package com.example.androidrecupracticarroom;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.androidrecupracticarroom.data.Dato;
import com.example.androidrecupracticarroom.data.RoomDB;

import java.util.List;

public class DatoRecyclerAdapter extends RecyclerView.Adapter<DatoRecyclerAdapter.ViewHolder> {
    List<Dato> datos;
    private Activity context;

    RoomDB database;


    public DatoRecyclerAdapter(List<Dato> datos, Activity context) {
        this.datos = datos;
        this.context = context;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DatoRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dato_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DatoRecyclerAdapter.ViewHolder holder, int position) {
        Dato dato = datos.get(position);

        database = RoomDB.getInstance(context);

        holder.tv.setText(dato.getText());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dato d = datos.get(holder.getAdapterPosition());
                int id = d.getId();
                String text = d.getText();

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.update_dialog);

                //estas tres lineas son opcionales
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);


                dialog.show();

                EditText etDialog = dialog.findViewById(R.id.et_dialog);
                Button bUpdate = dialog.findViewById(R.id.b_dialog);

                etDialog.setText(text);

                bUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        String updatedText = etDialog.getText().toString().trim();

                        database.mainDao().update(id, updatedText);

                        datos.clear();
                        datos.addAll(database.mainDao().findAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.ivBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dato d = datos.get(holder.getAdapterPosition());

                database.mainDao().delete(d);

                datos.clear();
                datos.addAll(database.mainDao().findAll());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView ivEdit, ivBorrar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.tv_item);
            ivEdit = itemView.findViewById(R.id.iv_edit);
            ivBorrar = itemView.findViewById(R.id.iv_delete);
        }
    }
}
