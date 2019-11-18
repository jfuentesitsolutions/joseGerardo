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
import com.jfuentes.josegerardo.clases.dialogos;
import com.jfuentes.josegerardo.clases.entidades.entity;
import com.jfuentes.josegerardo.clases.utilidades;
import com.jfuentes.josegerardo.fragmentos.fragmento_categoria;
import com.jfuentes.josegerardo.fragmentos.fragmento_marcas;
import com.jfuentes.josegerardo.fragmentos.fragmento_presentacion;
import com.jfuentes.josegerardo.singleton;

public class contenido_entidad extends AppCompatActivity {

    AppBarLayout layout;
    CollapsingToolbarLayout barra_colapsable;
    String tipo_entidad;
    FloatingActionButton fab;
    singleton sesion = singleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        colocarTema(getIntent().getExtras().getString("enti"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido_entidad);

        layout=findViewById(R.id.barra_herra_entidad);
        barra_colapsable=findViewById(R.id.barra_colapsable_entidad);
        fab=findViewById(R.id.btnModificar);


        Toolbar toolbar = findViewById(R.id.barra_interna_entidad);
        setSupportActionBar(toolbar);

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

    private void colocarTema(String tema){
        switch (tema){
            case utilidades.ESTANTES:{
                setTheme(R.style.color_mango_NoActionBar);
                break;
            }
            case utilidades.MARCAS:{
                setTheme(R.style.color_manzana_NoActionBar);
                break;
            }
            case utilidades.CATEGORIAS:{
                setTheme(R.style.color_remolacha_NoActionBar);
                break;
            }
            case utilidades.PRESENTACIONES:{
                setTheme(R.style.color_uva_NoActionBar);
                break;
            }
        }
    }

    private void colocarEstilo(String tema){
        switch (tema){
            case utilidades.ESTANTES:{
                layout.setBackgroundResource(R.drawable.degradados_3);
                break;
            }
            case utilidades.MARCAS:{
                layout.setBackgroundResource(R.drawable.degradados_4);
                break;
            }
            case utilidades.CATEGORIAS:{
                layout.setBackgroundResource(R.drawable.degradados_5);
                break;
            }
            case utilidades.PRESENTACIONES:{
                layout.setBackgroundResource(R.drawable.degradados_6);
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
            case utilidades.MARCAS:{
                fragmento_marcas fragment = new fragmento_marcas();
                fragment.setArguments(args);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.detalles_entidad, fragment)
                        .commit();
                break;
            }
            case utilidades.CATEGORIAS:{
                fragmento_categoria fragment = new fragmento_categoria();
                fragment.setArguments(args);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.detalles_entidad, fragment)
                        .commit();
                break;
            }
            case utilidades.PRESENTACIONES:{
                fragmento_presentacion fragment = new fragmento_presentacion();
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
                if(sesion.getActualiza()){
                    actualizarListas();
                }else{
                    finish();
                }
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(sesion.getActualiza()){
            actualizarListas();
        }
    }

    private void actualizarListas(){
        Intent intent= new Intent(this,lista_entidades.class);
        switch (tipo_entidad){
            case utilidades.ESTANTES:{
                intent.putExtra("titulo", "Lista de estantes");
                intent.putExtra("tema",utilidades.ESTANTES);
                navigateUpTo(intent);
                sesion.setActualiza(false);
                break;
            }
            case utilidades.MARCAS:{
                intent.putExtra("titulo", "Lista de marcas");
                intent.putExtra("tema",utilidades.MARCAS);
                navigateUpTo(intent);
                sesion.setActualiza(false);
                break;
            }
            case utilidades.CATEGORIAS:{
                intent.putExtra("titulo", "Lista de categorias");
                intent.putExtra("tema",utilidades.CATEGORIAS);
                navigateUpTo(intent);
                sesion.setActualiza(false);
                break;
            }
            case utilidades.PRESENTACIONES:{
                intent.putExtra("titulo", "Lista de presentaciones");
                intent.putExtra("tema",utilidades.PRESENTACIONES);
                navigateUpTo(intent);
                sesion.setActualiza(false);
                break;
            }
        }
    }
}
