package com.jfuentes.josegerardo.maestro_detalle_productos.adaptador_maestro;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.clases.entidades.entity;
import com.jfuentes.josegerardo.maestro_detalle_productos.contenido_elemento;
import com.jfuentes.josegerardo.maestro_detalle_productos.contenido_entidad;
import com.jfuentes.josegerardo.maestro_detalle_productos.fragment_contenido;
import com.jfuentes.josegerardo.maestro_detalle_productos.lista_entidades;

import java.util.ArrayList;

public class adapatador_entidad extends RecyclerView.Adapter<viewHolder_entidad> implements View.OnClickListener{

    private final String entidad;
    private Context contexto;
    private int img, fondo;
    private ArrayList<entity> lista;
    private final lista_entidades mParentActivity;

    public adapatador_entidad(String entidad, Context contexto,
                              ArrayList<entity> lista, lista_entidades mParentActivity,
                              int imagen, int fondo) {
        this.entidad = entidad;
        this.contexto = contexto;
        this.lista = lista;
        this.mParentActivity = mParentActivity;
        this.img=imagen;
        this.fondo=fondo;
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
        holder.imagen.setImageResource(img);
        holder.imagen.setBackgroundResource(fondo);

        holder.itemView.setTag(lista.get(position));
        holder.itemView.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        Bundle dato=new Bundle();
        dato.putSerializable("entidad", (entity)view.getTag());
        dato.putString("enti", this.entidad);
        Context c=view.getContext();
        Intent intent = new Intent(c, contenido_entidad.class);
        intent.putExtras(dato);
        c.startActivity(intent);
    }

    public ArrayList<entity> getLista() {
        return lista;
    }
}
