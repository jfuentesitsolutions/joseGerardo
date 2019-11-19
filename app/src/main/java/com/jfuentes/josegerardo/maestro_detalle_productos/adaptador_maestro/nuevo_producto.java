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
        lista_categorias=findViewById(R.id.lista_cate);

        conexiones_base con=new conexiones_base(this);

        String[] va={"",""};
        String[] va2={"","",""};
        String url="";
        String url2="";
        try {
            url=conexiones_base.sentencia(new sucursales(), con.getIpe(), con.getPuertoe(), utilidades.REGISTROS, va);
            lista_su=con.extraer(url, utilidades.SUCURSALES);
            lista_su.add(new sucursales("0","Elija la sucursal"));
            ArrayAdapter<entity> adapta=new ArrayAdapter<entity>(this, android.R.layout.simple_spinner_item,lista_su);
            lista_sucursales.setAdapter(adapta);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            url2=conexiones_base.sentencia(new categorias(), con.getIpe(), con.getPuertoe(), utilidades.REGISTROS, va2);
            lista_ca=con.extraer(url2, utilidades.CATEGORIAS);
            lista_ca.add(new categorias("-", "Elija la categoria","0"));
            ArrayAdapter<entity> adapta2=new ArrayAdapter<entity>(this, android.R.layout.simple_spinner_item,lista_ca);
            lista_categorias.setAdapter(adapta2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
