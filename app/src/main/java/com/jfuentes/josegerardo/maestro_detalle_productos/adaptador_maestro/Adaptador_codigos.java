package com.jfuentes.josegerardo.maestro_detalle_productos.adaptador_maestro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.clases.entidades.codigos;
import com.jfuentes.josegerardo.presentacion;

import java.util.ArrayList;

public class Adaptador_codigos extends BaseAdapter {

    private static LayoutInflater inflater=null;
    Context contexto;
    ArrayList<codigos> lista;

    public ArrayList<codigos> getLista() {
        return lista;
    }

    public Adaptador_codigos(Context contexto, ArrayList<codigos> datos) {
        this.contexto = contexto;
        this.lista=datos;
        inflater=(LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int i) {
        return lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        codigos item=(codigos) getItem(i);
        view=LayoutInflater.from(contexto).inflate(R.layout.elemento4, null);

        TextView codigo=view.findViewById(R.id.txtCodig);
        TextView estado=view.findViewById(R.id.txtEst);

        codigo.setText(item.getCodigo());
        estado.setText(item.getEstado());

        return view;
    }
}
