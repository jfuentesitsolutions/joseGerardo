package com.jfuentes.josegerardo.maestro_detalle_productos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.adaptador_productos;
import com.jfuentes.josegerardo.conexiones_base;
import com.jfuentes.josegerardo.maestro_detalle_productos.adaptador_maestro.adaptador;
import com.jfuentes.josegerardo.productos;
import com.jfuentes.josegerardo.singleton;

import java.util.ArrayList;

public class lista_productos extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private boolean panelDos;
    public ArrayList<productos> lista_P= new ArrayList<productos>();
    public RecyclerView lista;
    conexiones_base conec;
    public adaptador ada;
    ProgressBar progreso;
    tarea hilo;
    singleton sesion=singleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        progreso= findViewById(R.id.progre);


        FloatingActionButton fab = findViewById(R.id.btnAgregaProducto);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Snackbar.make(view, String.valueOf(ada.getItemCount()), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (findViewById(R.id.contenedor_elemento) != null) {
            panelDos = true;
        }

        lista=findViewById(R.id.lista_ele);
        cargandoProductos();
        new tarea().execute(2);



    }

    public void cargandoProductos(){
        lista_P.clear();
        conec=new conexiones_base("",this.getApplicationContext());
        lista_P=conec.todosLosProductos("");

        Log.d("EVEN", String.valueOf(lista_P.size()));
    }

    public void cargandoAdaptador(){
        ada=new adaptador(lista_productos.this, lista_P, panelDos);
        ada.notifyDataSetChanged();
        lista.setAdapter(ada);
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
                ((adaptador) ada).setFiltro(lista_P);
                return true;
            }
        });

        return true;
    }

    private ArrayList<productos> filtro(ArrayList<productos> pro, String texto){
        ArrayList<productos>Lsta= new ArrayList<>();
        try {
            texto=texto.toLowerCase();
            for (productos proo: pro){
                String nombre=proo.getNombre().toLowerCase();
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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        try {
            ArrayList<productos>lista=filtro(lista_P, s);
            ((adaptador) ada).setFiltro(lista);

        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("Evento","Evento");
        return true;
    }

    class tarea extends AsyncTask<Integer, Integer, ArrayList<productos>>{

        @Override
        protected ArrayList<productos> doInBackground(Integer... integers) {
                lista_P.clear();
                conec=new conexiones_base("",lista_productos.this);
                lista_P=conec.todosLosProductos("");

                try {
                    Thread.sleep(3000);
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
        protected void onPostExecute(ArrayList<productos> productos) {
            super.onPostExecute(productos);

            progreso.setVisibility(View.GONE);
            ada=new adaptador(lista_productos.this, productos, panelDos);
            lista.setAdapter(ada);


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
