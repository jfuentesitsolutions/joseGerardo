package com.jfuentes.josegerardo.maestro_detalle_productos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.jfuentes.josegerardo.clases.dialogos;
import com.jfuentes.josegerardo.clases.entidades.categorias;
import com.jfuentes.josegerardo.clases.entidades.entity;
import com.jfuentes.josegerardo.clases.entidades.estantes;
import com.jfuentes.josegerardo.clases.entidades.marcas;
import com.jfuentes.josegerardo.clases.entidades.presentaciones;
import com.jfuentes.josegerardo.clases.mensaje_dialogo_by_jfuentes;
import com.jfuentes.josegerardo.clases.utilidades;
import com.jfuentes.josegerardo.conexiones_base;
import com.jfuentes.josegerardo.maestro_detalle_productos.adaptador_maestro.adapatador_entidad;
import com.jfuentes.josegerardo.productos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class lista_entidades extends AppCompatActivity implements SearchView.OnQueryTextListener {

    FloatingActionButton fab;
    public ArrayList<entity> lista_P= new ArrayList<entity>();
    RecyclerView lista_entidad;
    conexiones_base conec;
    private adapatador_entidad ada;
    ProgressBar progreso;
    String tipo_entidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        colocarEstilo(getIntent().getStringExtra("tema"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_entidades);

        progreso= findViewById(R.id.progre_2);
        fab=findViewById(R.id.btnAgregaEntidad);
        tipo_entidad=getIntent().getStringExtra("tema");

        this.setTitle(getIntent().getStringExtra("titulo"));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regitrosNuevos();
            }
        });

        lista_entidad=findViewById(R.id.lista_entidades);
        new tarea().execute(2);

        SwipeableRecyclerViewTouchListener deslizar=new SwipeableRecyclerViewTouchListener(lista_entidad, new SwipeableRecyclerViewTouchListener.SwipeListener() {
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
                adapatador_entidad ada=(adapatador_entidad) recyclerView.getAdapter();
                entity p=ada.getLista().get(ints[0]);
                ada.getLista().remove(p);
                lista_P.remove(p);
                ada.notifyDataSetChanged();
                elimandoEntidad(p);
            }
        });
        lista_entidad.addOnItemTouchListener(deslizar);
    }

    private void regitrosNuevos(){
        final dialogos di=new dialogos(lista_entidades.this);
        switch (tipo_entidad){
            case utilidades.ESTANTES:{
                di.colocarTitulo("Nuevo estante");
                di.deshabilitarEstado();
                di.getBtnModi().setText("Guardar");
                di.mostrar();

                di.getBtnModi().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        guardarRegistro(di);
                    }
                });
                break;
            }
            case utilidades.MARCAS:{
                di.colocarTitulo("Nueva marca");
                di.deshabilitarEstado();
                di.getBtnModi().setText("Guardar");
                di.mostrar();

                di.getBtnModi().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        guardarRegistro(di);
                    }
                });
                break;
            }
            case utilidades.CATEGORIAS:{
                di.colocarTitulo("Nueva categoria");
                di.deshabilitarEstado();
                di.getBtnModi().setText("Guardar");
                di.mostrar();

                di.getBtnModi().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        guardarRegistro(di);
                    }
                });
                break;
            }
            case utilidades.PRESENTACIONES:{
                di.colocarTitulo("Nueva presentación");
                di.getBtnModi().setText("Guardar");
                di.mostrar();

                di.getBtnModi().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        guardarRegistro(di);
                    }
                });
                break;
            }
        }
    }

    private void guardarRegistro(dialogos di){

        if(!di.validar()){
            conexiones_base cone=new conexiones_base(this);
            Map<String, String> map=new HashMap<>();
            switch (tipo_entidad){
                case utilidades.PRESENTACIONES:{
                    map.put("entidad",tipo_entidad);
                    map.put("opcion", utilidades.NUEVO_REGISTRO);
                    map.put("id", "0");
                    map.put("nombre", di.getNombre().getText().toString());
                    map.put("descripcion", di.getDescrp().getText().toString());
                    if(di.getEst().isChecked()){
                        map.put("estado","1");
                    }else{
                        map.put("estado","2");
                    }

                    cone.insertar(map);
                    recreate();
                    break;
                }
                default:{
                    map.put("entidad",tipo_entidad);
                    map.put("opcion", utilidades.NUEVO_REGISTRO);
                    map.put("id", "0");
                    map.put("nombre", di.getNombre().getText().toString());
                    map.put("descripcion", di.getDescrp().getText().toString());

                    cone.insertar(map);
                    recreate();
                    break;
                }
            }
        }

    }

    private void colocarEstilo(String tema){
       switch (tema){
           case utilidades.ESTANTES:{
               setTheme(R.style.color_mango);
               break;
           }
           case utilidades.MARCAS:{
               setTheme(R.style.color_manzana);
               break;
           }
           case utilidades.CATEGORIAS:{
               setTheme(R.style.color_remolacha);
               break;
           }
           case utilidades.PRESENTACIONES:{
               setTheme(R.style.color_uva);
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

        private void cargandoDatos(){
            lista_P.clear();
            switch (tipo_entidad){
                case utilidades.ESTANTES:{
                    conec=new conexiones_base("",getApplicationContext());
                    String[] va={"Hola","1","2","Buenas"};
                    String url= null;
                    try {
                        url = conexiones_base.sentencia(new estantes(), conec.getIpe(), conec.getPuertoe(), utilidades.REGISTROS,va);
                        lista_P=conec.extraer(url, utilidades.ESTANTES);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case utilidades.MARCAS:{
                    conec=new conexiones_base("",getApplicationContext());
                    String[] va={"Hola","1","2","Buenas"};
                    String url= null;
                    try {
                        url = conexiones_base.sentencia(new marcas(), conec.getIpe(), conec.getPuertoe(), utilidades.REGISTROS,va);
                        lista_P=conec.extraer(url, utilidades.MARCAS);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case utilidades.CATEGORIAS:{
                    conec=new conexiones_base("",getApplicationContext());
                    String[] va={"Hola","1","2","Buenas"};
                    String url= null;
                    try {
                        url = conexiones_base.sentencia(new categorias(), conec.getIpe(), conec.getPuertoe(), utilidades.REGISTROS,va);
                        lista_P=conec.extraer(url, utilidades.CATEGORIAS);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case utilidades.PRESENTACIONES:{
                    conec=new conexiones_base("",getApplicationContext());
                    String[] va={"Hola","1","2","Buenas"};
                    String url= null;
                    try {
                        url = conexiones_base.sentencia(new presentaciones(), conec.getIpe(), conec.getPuertoe(), utilidades.REGISTROS,va);
                        lista_P=conec.extraer(url, utilidades.PRESENTACIONES);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

        }

        private void cargandoAdaptador(){
            Collections.sort(lista_P);
            switch (tipo_entidad){
                case utilidades.ESTANTES:{
                    ada=new adapatador_entidad(utilidades.ESTANTES, getApplicationContext(), lista_P, lista_entidades.this,
                            R.drawable.stock, R.drawable.degradados_3);
                    ada.notifyDataSetChanged();
                    lista_entidad.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                    lista_entidad.setAdapter(ada);
                    progreso.setVisibility(View.GONE);
                    break;
                }
                case utilidades.MARCAS:{
                    ada=new adapatador_entidad(utilidades.MARCAS, getApplicationContext(), lista_P, lista_entidades.this,
                            R.drawable.brand, R.drawable.degradados_4);
                    ada.notifyDataSetChanged();
                    lista_entidad.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                    lista_entidad.setAdapter(ada);
                    progreso.setVisibility(View.GONE);
                    break;
                }
                case utilidades.CATEGORIAS:{
                    ada=new adapatador_entidad(utilidades.CATEGORIAS, getApplicationContext(), lista_P, lista_entidades.this,
                            R.drawable.cate, R.drawable.degradados_5);
                    ada.notifyDataSetChanged();
                    lista_entidad.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                    lista_entidad.setAdapter(ada);
                    progreso.setVisibility(View.GONE);
                    break;
                }
                case utilidades.PRESENTACIONES:{
                    ada=new adapatador_entidad(utilidades.PRESENTACIONES, getApplicationContext(), lista_P, lista_entidades.this,
                            R.drawable.design, R.drawable.degradados_6);
                    ada.notifyDataSetChanged();
                    lista_entidad.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                    lista_entidad.setAdapter(ada);
                    progreso.setVisibility(View.GONE);
                    break;
                }

            }
        }

        @Override
        protected ArrayList<entity> doInBackground(Integer... integers) {

            cargandoDatos();
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
            cargandoAdaptador();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    private void elimandoEntidad(final entity entidad){
        mensaje_dialogo_by_jfuentes mensaje=null;

        switch (tipo_entidad){
            case utilidades.ESTANTES:{
                mensaje= new mensaje_dialogo_by_jfuentes(this, "Eliminar estante",
                        "¿Desea eliminar el estante: "+entidad.nombre()+"?", R.drawable.degradados_3);
                break;
            }
            case utilidades.MARCAS:{
                mensaje= new mensaje_dialogo_by_jfuentes(this, "Eliminar marca",
                        "¿Desea eliminar la marca: "+entidad.nombre()+"?", R.drawable.degradados_4);
                break;
            }
            case utilidades.CATEGORIAS:{
                mensaje= new mensaje_dialogo_by_jfuentes(this, "Eliminar categoria",
                        "¿Desea eliminar la categoria: "+entidad.nombre()+"?", R.drawable.degradados_5);
                break;
            }
            case utilidades.PRESENTACIONES:{
                mensaje= new mensaje_dialogo_by_jfuentes(this, "Eliminar presentación",
                        "¿Desea eliminar la presentación: "+entidad.nombre()+"?", R.drawable.degradados_6);
                break;
            }
        }

        final mensaje_dialogo_by_jfuentes finalMensaje = mensaje;
        mensaje.getAcepta().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar(entidad);
                finalMensaje.cerrar();
            }
        });

        mensaje.getCancelar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lista_P.add(entidad);
                ada.getLista().add(entidad);
                Collections.sort(lista_P);
                ada.notifyDataSetChanged();
                finalMensaje.cerrar();
            }
        });
    }

    private void eliminar(entity entidad){
        conexiones_base conec =new conexiones_base(this);
        Map<String, String> map=new HashMap<>();

        switch (tipo_entidad){
            case utilidades.ESTANTES:{
                map.put("entidad", utilidades.ESTANTES);
                map.put("opcion", utilidades.BORRAR_REGISTRO);
                map.put("id", entidad.id());
                map.put("nombre", entidad.nombre());
                map.put("descripcion", entidad.descripcion());
                conec.insertar(map);
                recreate();

                break;
            }
            case utilidades.MARCAS:{
                map.put("entidad", utilidades.MARCAS);
                map.put("opcion", utilidades.BORRAR_REGISTRO);
                map.put("id", entidad.id());
                map.put("nombre", entidad.nombre());
                map.put("descripcion", entidad.descripcion());
                conec.insertar(map);
                recreate();

                break;
            }
            case utilidades.CATEGORIAS:{
                map.put("entidad", utilidades.CATEGORIAS);
                map.put("opcion", utilidades.BORRAR_REGISTRO);
                map.put("id", entidad.id());
                map.put("nombre", entidad.nombre());
                map.put("descripcion", entidad.descripcion());
                conec.insertar(map);
                recreate();

                break;
            }
            case utilidades.PRESENTACIONES:{
                map.put("entidad", utilidades.PRESENTACIONES);
                map.put("opcion", utilidades.BORRAR_REGISTRO);
                map.put("id", entidad.id());
                map.put("nombre", entidad.nombre());
                map.put("descripcion", entidad.descripcion());
                map.put("estado", entidad.estado());
                conec.insertar(map);
                recreate();

                break;
            }
        }
    }


}
