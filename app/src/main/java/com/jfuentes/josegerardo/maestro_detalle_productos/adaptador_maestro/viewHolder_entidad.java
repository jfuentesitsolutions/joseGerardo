package com.jfuentes.josegerardo.maestro_detalle_productos.adaptador_maestro;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jfuentes.josegerardo.R;

public class viewHolder_entidad extends RecyclerView.ViewHolder {

    TextView nombre;

    public viewHolder_entidad(@NonNull View itemView) {
        super(itemView);
        nombre=itemView.findViewById(R.id.txtEstantes);
    }
}
