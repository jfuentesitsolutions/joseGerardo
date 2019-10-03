package com.jfuentes.josegerardo;

import android.content.Context;
import android.content.Entity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    private static LayoutInflater inflater=null;
    Context contexto;
    ArrayList<presentacion> lista;


    public Adaptador(Context contexto, ArrayList<presentacion> datos) {
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
        presentacion item=(presentacion) getItem(i);
        view=LayoutInflater.from(contexto).inflate(R.layout.elemento, null);

        TextView nombre=(TextView)view.findViewById(R.id.txtNombrePre);
        TextView canti=(TextView)view.findViewById(R.id.txtCantidad);
        TextView tpre=(TextView)view.findViewById(R.id.txtTipoP);
        TextView pre=(TextView)view.findViewById(R.id.txtPrecio);

        nombre.setText(item.getPresentacion());
        canti.setText(item.getCantidad());
        tpre.setText(item.getTipo());
        pre.setText(item.getPrecio());
        return view;
    }
}
