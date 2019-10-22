package com.jfuentes.josegerardo.maestro_detalle_productos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.productos;
import com.jfuentes.josegerardo.singleton;

public class contenido_elemento extends AppCompatActivity {

    Bundle bundle=null;
    Boolean contenido=false;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Intent intent=new Intent(this, lista_productos.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contenido_elemento);

        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab =  findViewById(R.id.btnActualizar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            bundle = getIntent().getExtras();
            Bundle arguments= new Bundle();
            productos pro=null;

            if(bundle!=null){
                pro=(productos)bundle.getSerializable("producto");
                contenido=bundle.getBoolean("busqueda_codigo");
                arguments.putSerializable("pro", pro);
            }

            fragment_contenido fragment = new fragment_contenido();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(!contenido){
            Intent intent=new Intent(this, lista_productos.class);
            startActivity(intent);
            finish();
        }

    }
}
