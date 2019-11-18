package com.jfuentes.josegerardo.fragmentos;

import android.app.Activity;
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

public class fragmento_presentacion extends Fragment {

    TextView nombre, descripcion, estado;
    entity entidad;
    ViewGroup con;
    FloatingActionButton fab;
    singleton sesion=singleton.getInstance();
    CollapsingToolbarLayout barra_colapsable;

    public fragmento_presentacion() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle=getArguments();
        if(bundle!=null){
            entidad=(entity) bundle.getSerializable("enti");
            Activity actividad=getActivity();
            fab=actividad.findViewById(R.id.btnModificar);
            barra_colapsable=actividad.findViewById(R.id.barra_colapsable_entidad);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogos di=new dialogos(con, R.layout.dialogo_entidad);
                di.colocarTitulo("Modificar presentacion");
                if(entidad.estado().equals("Activa")){
                    di.colocarDatos(nombre.getText().toString(), descripcion.getText().toString(), true, true);
                }else{
                    di.colocarDatos(nombre.getText().toString(), descripcion.getText().toString(), false, true);
                }
                modificar(di);
                di.mostrar();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmento_presentacion, container, false);

        con=container;
        nombre=rootView.findViewById(R.id.txtNombre);
        descripcion=rootView.findViewById(R.id.txtDescripcion);
        estado=rootView.findViewById(R.id.txtEstado);

        nombre.setText(entidad.nombre());
        descripcion.setText(entidad.descripcion());
        estado.setText(entidad.estado());

        return rootView;
    }

    private void modificar(final dialogos di){
        di.getBtnModi().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!di.validar()){
                    conexiones_base cone= new conexiones_base(con.getContext());
                    Map<String, String> map= new HashMap<>();
                    map.put("entidad", utilidades.PRESENTACIONES);
                    map.put("opcion", utilidades.ACTUALIZAR_REGISTRO);
                    map.put("id", entidad.id());
                    map.put("nombre", di.getNombre().getText().toString());
                    map.put("descripcion", di.getDescrp().getText().toString());
                    if(di.getEst().isChecked()){
                        map.put("estado", "1");
                        estado.setText("Activada");
                    }else{
                        map.put("estado","2");
                        estado.setText("Inactiva");
                    }

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
