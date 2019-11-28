package com.jfuentes.josegerardo.adaptadores;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jfuentes.josegerardo.R;

public class viewHolder extends RecyclerView.ViewHolder {
    public TextView tipo;
    public TextView cantidad;
    public TextView precio;

    public viewHolder(@NonNull View itemView) {
        super(itemView);

        tipo=itemView.findViewById(R.id.ele_prese);
        cantidad=itemView.findViewById(R.id.ele_canti);
        precio=itemView.findViewById(R.id.ele_precio);
    }
}
