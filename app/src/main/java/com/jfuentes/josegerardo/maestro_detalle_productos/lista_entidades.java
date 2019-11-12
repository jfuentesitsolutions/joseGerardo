package com.jfuentes.josegerardo.maestro_detalle_productos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.clases.entidades.entity;
import com.jfuentes.josegerardo.clases.entidades.estantes;
import com.jfuentes.josegerardo.clases.utilidades;
import com.jfuentes.josegerardo.conexiones_base;
import com.jfuentes.josegerardo.maestro_detalle_productos.adaptador_maestro.adapatador_entidad;

import java.util.ArrayList;

public class lista_entidades extends AppCompatActivity implements SearchView.OnQueryTextListener {

    FloatingActionButton fab;
    public ArrayList<entity> lista_P= new ArrayList<entity>();
    RecyclerView lista_entidad;
    conexiones_base conec;
    private adapatador_entidad ada;
    ProgressBar progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        colocarEstilo(getIntent().getStringExtra("tema"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_entidades);

        progreso= findViewById(R.id.progre_2);
        fab=findViewById(R.id.btnAgregaEntidad);

        this.setTitle(getIntent().getStringExtra("titulo"));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hola buenas", Snackbar.LENGTH_LONG).setAction("Hola", null).show();
            }
        });

        lista_entidad=findViewById(R.id.lista_entidades);
        new tarea().execute(2);
    }

    private void colocarEstilo(String tema){
       switch (tema){
           case "estantes":{
               setTheme(R.style.color_mango);
               break;
           }
       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        getMenuInflater().inflate(R.menu.menu_busqueda, menu);
        MenuItem item= menu.findItem(R.id.buscador);
        SearchView bus=(SearchView) item.getActionView();

        bus.setMaxWidth(width);

        bus.setOnQueryTextListener(this);

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }
            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                ada.setFiltro(lista_P);
                return true;
            }
        });

        return true;
    }

    private ArrayList<entity> filtro(ArrayList<entity> enti, String texto){
        ArrayList<entity>Lsta= new ArrayList<>();
        try {
            texto=texto.toLowerCase();
            for (entity proo: enti){
                String nombre=proo.nombre().toLowerCase();
                if(nombre.contains(texto)){
                    Lsta.add(proo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return Lsta;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        try {
            ArrayList<entity>lista=filtro(lista_P, s);
            ada.setFiltro(lista);
            Log.d("Evento",String.valueOf(lista.size()));

        }catch (Exception e){
            e.printStackTrace();
        }

        return true;
    }

    class tarea extends AsyncTask<Integer, Integer, ArrayList<entity>> {

        @Override
        protected ArrayList<entity> doInBackground(Integer... integers) {
            lista_P.clear();
            conec=new conexiones_base("",getApplicationContext());
            String[] va={"Hola","1","2","Buenas"};
            String url= null;
            try {
                url = conexiones_base.sentencia(new estantes(), conec.getIpe(), conec.getPuertoe(), utilidades.REGISTROS,va);
                lista_P=conec.extraer(url, utilidades.ESTANTES);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return lista_P;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if(progreso.getVisibility()==View.GONE){
                progreso.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onPostExecute(ArrayList<entity> entidad) {
            super.onPostExecute(entidad);
            ada=new adapatador_entidad(utilidades.ESTANTES, getApplicationContext(), lista_P, lista_entidades.this,
                    R.drawable.stock, R.drawable.degradados_3);
            ada.notifyDataSetChanged();
            lista_entidad.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            lista_entidad.setAdapter(ada);
            progreso.setVisibility(View.GONE);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
