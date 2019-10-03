package com.jfuentes.josegerardo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Adaptador_matriz extends BaseAdapter {

    String datos[][];
    Context contexto;
    private static LayoutInflater inflater=null;


    public Adaptador_matriz(String[][] datos, Context contexto) {
        this.datos = datos;
        this.contexto = contexto;
        inflater=(LayoutInflater)contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datos[0].length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final View vista = inflater.inflate(R.layout.elemento, null);

        TextView nombre=(TextView)vista.findViewById(R.id.txtNombrePre);
        TextView canti=(TextView)vista.findViewById(R.id.txtCantidad);
        TextView tpre=(TextView)vista.findViewById(R.id.txtTipoP);
        TextView pre=(TextView)vista.findViewById(R.id.txtPrecio);

        nombre.setText(datos[i][0]);
        canti.setText(datos[i][1]);
        tpre.setText(datos[i][2]);
        pre.setText(datos[i][3]);

        return vista;
    }
}
