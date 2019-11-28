package com.jfuentes.josegerardo.adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.clases.entidades.presentaciones_productos;

import java.util.ArrayList;

public class adaptador_presentaciones extends RecyclerView.Adapter<viewHolder> {

    ArrayList<presentaciones_productos> lista;

    public adaptador_presentaciones(ArrayList<presentaciones_productos> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento3, parent, false);
        viewHolder vh= new viewHolder(vista);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.tipo.setText(lista.get(position).nombre());
        holder.cantidad.setText("Cantidad: "+lista.get(position).getCantida());
        holder.precio.setText("Precio: $"+lista.get(position).getPrecio());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
