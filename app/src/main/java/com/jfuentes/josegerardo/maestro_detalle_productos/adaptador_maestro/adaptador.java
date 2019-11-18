package com.jfuentes.josegerardo.maestro_detalle_productos.adaptador_maestro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.maestro_detalle_productos.contenido_elemento;
import com.jfuentes.josegerardo.maestro_detalle_productos.fragment_contenido;
import com.jfuentes.josegerardo.maestro_detalle_productos.lista_productos;
import com.jfuentes.josegerardo.productos;

import java.util.ArrayList;
import java.util.List;

public class adaptador extends RecyclerView.Adapter<ViewHolder> {

    private static final String LOG_TAG ="EVE";
    private final lista_productos mParentActivity;
    private List<productos> listaP;
    private final boolean dos;

    private final View.OnClickListener evento= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            productos producto=(productos)v.getTag();
            if (dos) {
                Bundle arguments = new Bundle();
                arguments.putBoolean("tablet", true);
                arguments.putSerializable("pro", producto);
                fragment_contenido fragment = new fragment_contenido();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor_elemento, fragment)
                        .commit();

            } else {
                Context context = v.getContext();
                Bundle bundle= new Bundle();
                bundle.putBoolean("tablet",false);
                bundle.putSerializable("producto", producto);
                Intent intent = new Intent(context, contenido_elemento.class);
                intent.putExtras(bundle);

                context.startActivity(intent);
            }

        }
    };

    public List<productos> getListaP() {
        return listaP;
    }

    public adaptador(lista_productos parent, List<productos> items, boolean twoPane) {
        listaP = items;
        mParentActivity = parent;
        dos = twoPane;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_producto, parent, false);
        ViewHolder vh= new ViewHolder(vista);

        Log.d(LOG_TAG, "oncreateviewholder : " + viewType);
        return  vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nom.setText(listaP.get(position).getNombre());
        holder.cod.setText(listaP.get(position).getCodigo());
        holder.estante.setText(listaP.get(position).getEstante());

        holder.itemView.setTag(listaP.get(position));
        holder.itemView.setOnClickListener(evento);

    }

    @Override
    public int getItemCount() {
        return listaP.size();
    }

    public void setFiltro(ArrayList<productos> lista){
        listaP= new ArrayList<>();
        listaP.addAll(lista);
        notifyDataSetChanged();
    }
}
