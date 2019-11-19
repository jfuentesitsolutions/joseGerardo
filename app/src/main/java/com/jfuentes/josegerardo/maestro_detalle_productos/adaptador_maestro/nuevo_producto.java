package com.jfuentes.josegerardo.maestro_detalle_productos.adaptador_maestro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.clases.entidades.categorias;
import com.jfuentes.josegerardo.clases.entidades.entity;
import com.jfuentes.josegerardo.clases.entidades.sucursales;
import com.jfuentes.josegerardo.clases.utilidades;
import com.jfuentes.josegerardo.conexiones_base;

import java.util.ArrayList;

public class nuevo_producto extends AppCompatActivity {

    Spinner lista_sucursales;
    Spinner lista_categorias;
    ArrayList<entity> lista_su=new ArrayList<>();
    ArrayList<entity> lista_ca=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.color_mora);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);

        setTitle("Nuevo producto");

        lista_sucursales=findViewById(R.id.lista_sucu);
        lista_categorias=findViewById(R.id.lista_cate       );

    }

}
