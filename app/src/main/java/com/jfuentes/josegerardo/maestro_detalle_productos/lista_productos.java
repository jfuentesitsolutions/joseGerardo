package com.jfuentes.josegerardo.maestro_detalle_productos;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toast;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.adaptador_productos;
import com.jfuentes.josegerardo.busquedas;
import com.jfuentes.josegerardo.clases.entidades.entity;
import com.jfuentes.josegerardo.clases.mensaje_dialogo_by_jfuentes;
import com.jfuentes.josegerardo.clases.utilidades;
import com.jfuentes.josegerardo.conexiones_base;
import com.jfuentes.josegerardo.maestro_detalle_productos.adaptador_maestro.adaptador;
import com.jfuentes.josegerardo.productos;


import java.util.ArrayList;
import java.util.Collections;

public class lista_productos extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private boolean panelDos;
    public ArrayList<productos> lista_P= new ArrayList<productos>();
    public RecyclerView lista;
    conexiones_base conec;
    public adaptador ada;
    ProgressBar progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_productos);

        progreso= findViewById(R.id.progre);
        this.setTitle("Listado de productos");

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
        new tarea().execute(2);

        SwipeableRecyclerViewTouchListener deslizar= new SwipeableRecyclerViewTouchListener(lista, new SwipeableRecyclerViewTouchListener.SwipeListener() {
            @Override
            public boolean canSwipeLeft(int position) {
                return false;
            }

            @Override
            public boolean canSwipeRight(int position) {
                return true;
            }

            @Override
            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] ints) {

            }

            @Override
            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] ints) {
                adaptador ada=(adaptador) recyclerView.getAdapter();
                productos p=ada.getListaP().get(ints[0]);
                ada.getListaP().remove(p);
                lista_P.remove(p);
                ada.notifyDataSetChanged();
                eliminado_productos(p);
            }
        });

        lista.addOnItemTouchListener(deslizar);

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
                ada.setFiltro(lista_P);
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
            Collections.sort(productos);
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

    private void eliminado_productos(final productos pro){
        final mensaje_dialogo_by_jfuentes mensaje= new mensaje_dialogo_by_jfuentes(this, "Eliminar estante",
                "¿Desea eliminar el producto: "+pro.getNombre()+"?", R.drawable.degradados_2);

        mensaje.getAcepta().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //eliminar(entidad);
                mensaje.cerrar();
            }
        });

        mensaje.getCancelar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista_P.add(pro);
                ada.getListaP().add(pro);
                Collections.sort(lista_P);
                ada.notifyDataSetChanged();
                mensaje.cerrar();
            }
        });
    }
}
