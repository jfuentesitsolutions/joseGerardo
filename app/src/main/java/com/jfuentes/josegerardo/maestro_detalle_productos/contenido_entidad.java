package com.jfuentes.josegerardo.maestro_detalle_productos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.clases.entidades.entity;
import com.jfuentes.josegerardo.clases.utilidades;

public class contenido_entidad extends AppCompatActivity {

    AppBarLayout layout;
    CollapsingToolbarLayout barra_colapsable;
    String tipo_entidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.color_mango_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido_entidad);

        layout=findViewById(R.id.barra_herra_entidad);


        barra_colapsable=findViewById(R.id.barra_colapsable_entidad);

        Toolbar toolbar = findViewById(R.id.barra_interna_entidad);
        setSupportActionBar(toolbar);

        FloatingActionButton fab =  findViewById(R.id.btnModificar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if(savedInstanceState == null){
            Bundle arguments= new Bundle();
            Bundle bundle= getIntent().getExtras();
            entity entidad=(entity)bundle.getSerializable("entidad");
            tipo_entidad=bundle.getString("enti");
            colocarEstilo(tipo_entidad);
            arguments.putSerializable("enti", entidad);
            barra_colapsable.setTitle(entidad.nombre());
            abrirFragmento(arguments);
        }
    }

    private void colocarEstilo(String tema){
        switch (tema){
            case utilidades.ESTANTES:{
                layout.setBackgroundResource(R.drawable.degradados_3);
                break;
            }
        }
    }

    private void abrirFragmento(Bundle args){
        switch (tipo_entidad){
            case utilidades.ESTANTES:{
                fragment_entidad fragment = new fragment_entidad();
                fragment.setArguments(args);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.detalles_entidad, fragment)
                        .commit();
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
