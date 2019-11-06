package com.jfuentes.josegerardo.maestro_detalle_productos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jfuentes.josegerardo.Adaptador;
import com.jfuentes.josegerardo.MainActivity;
import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.info_producto;
import com.jfuentes.josegerardo.presentacion;
import com.jfuentes.josegerardo.productos;
import com.jfuentes.josegerardo.singleton;
import com.jfuentes.josegerardo.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class contenido_elemento extends AppCompatActivity {

    Bundle bundle=null;
    Boolean contenido=false;
    ArrayList<presentacion> lista= new ArrayList<presentacion>();
    RequestQueue res;
    String ipe, puertoe;
    singleton sesion=singleton.getInstance();

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if(!contenido){
                navigateUpTo(new Intent(this, lista_productos.class));
            }else{
                navigateUpTo(new Intent(this, MainActivity.class));
            }
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
                abrirVentanaDialogo();
            }
        });



        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
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

            accediendoDatos();
            String url="http://"+ipe+":"+puertoe+"/servidor/presentaciones.php?pro="+pro.getId();
            conexion(url);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(!contenido){
            if(sesion.getActualiza()){
                navigateUpTo(new Intent(this, lista_productos.class));
                sesion.setActualiza(false);
            }
        }
    }

    public void conexion(String URL) {
        res = volleySingleton.getInstance(this).getmRequesQueve();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject objeto=null;
                String [][] datos=new String[response.length()][5];
                for (int i=0;i<response.length();i++){
                    try {
                        objeto= response.getJSONObject(i);

                        lista.add(new presentacion(objeto.getString("idpre"), objeto.getString("cantidad"),
                                objeto.getString("precio"), objeto.getString("tipo"), objeto.getString("presentacion")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "No existen presentaciones", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No hay presentaciones", Toast.LENGTH_LONG).show();
            }
        });
        res.add(jsonArrayRequest);
    }

    private void abrirVentanaDialogo(){

        AlertDialog.Builder mBuilder= new AlertDialog.Builder(contenido_elemento.this);
        View mView=getLayoutInflater().inflate(R.layout.dialogo_presentaciones, null);

        ListView listaa=mView.findViewById(R.id.lista_productos) ;
        Adaptador adap=new Adaptador(this.getApplicationContext(), lista);
        listaa.setAdapter(adap);

        mBuilder.setView(mView);
        AlertDialog dialog= mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private boolean accediendoDatos(){
        SharedPreferences prefe= getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=prefe.edit();
        if(prefe.getString("ip","")==null || prefe.getString("puerto","")==null){
            Toast.makeText(getApplicationContext(),"No se encuentran datos de conexion", Toast.LENGTH_LONG).show();
            return false;
        }else{
            ipe=prefe.getString("ip","");
            puertoe=prefe.getString("puerto","");
            return true;
        }
    }
}
