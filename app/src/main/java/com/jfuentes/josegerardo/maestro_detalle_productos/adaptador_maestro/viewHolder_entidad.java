package com.jfuentes.josegerardo.maestro_detalle_productos.adaptador_maestro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.clases.entidades.entity;
import com.jfuentes.josegerardo.maestro_detalle_productos.contenido_entidad;

public class viewHolder_entidad extends RecyclerView.ViewHolder {

    TextView nombre;
    ImageView imagen;

    public viewHolder_entidad(@NonNull View itemView) {
        super(itemView);
        nombre=itemView.findViewById(R.id.txtEstantes);
        imagen=itemView.findViewById(R.id.img_entidad);
    }


}
