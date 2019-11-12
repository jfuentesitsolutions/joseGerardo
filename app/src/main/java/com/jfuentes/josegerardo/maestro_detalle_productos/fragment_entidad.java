package com.jfuentes.josegerardo.maestro_detalle_productos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.clases.entidades.entity;


public class fragment_entidad extends Fragment {

    String tema;
    public fragment_entidad() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle=getArguments();
        if(bundle!=null){
            entity entidad=(entity) bundle.getSerializable("enti");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entidad, container, false);
    }




}
