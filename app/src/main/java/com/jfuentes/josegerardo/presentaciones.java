package com.jfuentes.josegerardo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class presentaciones extends AppCompatActivity {

    singleton sesion= singleton.getInstance();
    ListView l;
    Adaptador adapta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentaciones);


        l=(ListView)findViewById(R.id.lista);

            adapta= new Adaptador(this,sesion.getDato());
            l.setAdapter(adapta);




        /*if(sesion.getDato()!=null){

            sesion.setDato(null);
        }else {
            Toast.makeText(getApplicationContext(),"variable vacia",Toast.LENGTH_LONG).show();
        }*/


    }

    private ArrayList<presentacion> getPresentaciones(){
        ArrayList<presentacion> lista = new ArrayList<>();
        lista.add(new presentacion("12","250","1.50", "Detalle", "Unidad"));

        return lista;
    }


}
