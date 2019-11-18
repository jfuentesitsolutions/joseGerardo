package com.jfuentes.josegerardo.fragmentos;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.clases.dialogos;
import com.jfuentes.josegerardo.clases.entidades.entity;
import com.jfuentes.josegerardo.clases.utilidades;
import com.jfuentes.josegerardo.conexiones_base;
import com.jfuentes.josegerardo.singleton;

import java.util.HashMap;
import java.util.Map;


public class fragmento_marcas extends Fragment {

    TextView nombre, descripcion;
    entity entidad;
    ViewGroup con;
    FloatingActionButton fab;
    singleton sesion=singleton.getInstance();
    CollapsingToolbarLayout barra_colapsable;

    public fragmento_marcas() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle=getArguments();
        if(bundle!=null){
            entidad=(entity) bundle.getSerializable("enti");
            Activity actividad=getActivity();
            barra_colapsable=actividad.findViewById(R.id.barra_colapsable_entidad);
            fab=actividad.findViewById(R.id.btnModificar);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogos di=new dialogos(con, R.layout.dialogo_entidad);
                di.colocarTitulo("Modificar marca");
                di.deshabilitarEstado();
                di.colocarDatos(nombre.getText().toString(), descripcion.getText().toString(), false, false);
                modificar(di);
                di.mostrar();

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_fragmento_marcas, container, false);

        //Inicializando los valores.
        con=container;
        nombre=rootView.findViewById(R.id.txtNombre);
        descripcion=rootView.findViewById(R.id.txtDescripcion);

        nombre.setText(entidad.nombre());
        descripcion.setText(entidad.descripcion());

        return rootView;
    }

    private void modificar(final dialogos di){
        di.getBtnModi().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!di.validar()){
                    conexiones_base cone= new conexiones_base(con.getContext());
                    Map<String, String> map= new HashMap<>();
                    map.put("entidad", utilidades.MARCAS);
                    map.put("opcion", utilidades.ACTUALIZAR_REGISTRO);
                    map.put("id", entidad.id());
                    map.put("nombre", di.getNombre().getText().toString());
                    map.put("descripcion", di.getDescrp().getText().toString());

                    cone.insertar(map);
                    barra_colapsable.setTitle(di.getNombre().getText());
                    nombre.setText(di.getNombre().getText());
                    descripcion.setText(di.getDescrp().getText());
                    sesion.setActualiza(true);
                    di.cerrar();
                }
            }
        });
    }

}
