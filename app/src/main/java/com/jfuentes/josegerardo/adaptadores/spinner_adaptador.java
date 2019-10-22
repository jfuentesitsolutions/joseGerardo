package com.jfuentes.josegerardo.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.clases.entidad;

import java.util.List;

public class spinner_adaptador extends ArrayAdapter {

    public spinner_adaptador(@NonNull Context context, @NonNull List<entidad> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        entidad enti=(entidad) getItem(position);
        LayoutInflater inflater=(LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null){
            convertView=inflater.inflate(R.layout.spinner_personalizado, parent, false);
        }

        TextView texto=convertView.findViewById(R.id.Item);
        ImageView imagen=convertView.findViewById(R.id.imagen);

        texto.setText(enti.toString());
        imagen.setImageResource(enti.getImagen());

        return convertView;
    }
}
