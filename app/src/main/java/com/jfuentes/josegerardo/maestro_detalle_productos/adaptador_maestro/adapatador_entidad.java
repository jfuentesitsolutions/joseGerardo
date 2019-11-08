package com.jfuentes.josegerardo.maestro_detalle_productos.adaptador_maestro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.clases.entidades.entity;
import com.jfuentes.josegerardo.productos;

import java.util.ArrayList;

public class adapatador_entidad extends RecyclerView.Adapter<viewHolder_entidad> {

    private String entidad;
    private Context contexto;
    private ArrayList<entity> lista;

    public adapatador_entidad(String entidad, Context contexto, ArrayList<entity> lista) {
        this.entidad = entidad;
        this.contexto = contexto;
        this.lista = lista;
    }

    @NonNull
    @Override
    public viewHolder_entidad onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_2, parent, false);
        viewHolder_entidad vh= new viewHolder_entidad(vista);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder_entidad holder, int position) {
        holder.nombre.setText(lista.get(position).nombre());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void setFiltro(ArrayList<entity> listaa){
        lista= new ArrayList<>();
        lista.addAll(listaa);
        notifyDataSetChanged();
    }
}
