package com.jfuentes.josegerardo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class busquedas extends AppCompatActivity implements SearchView.OnQueryTextListener{


    String ipe, puertoe, producto_en_espera="";
    singleton sesio=singleton.getInstance();
    RequestQueue res;
    RecyclerView lista;
    RecyclerView.Adapter adaptador;
    RecyclerView.LayoutManager diseño;
    ArrayList<productos> lista_P= new ArrayList<productos>();
    conexiones_base conec;
    private Boolean tablet=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busquedas);

        if(findViewById(R.id.lista_productos_) !=null){
            tablet=true;
        }

        if(tablet){
            lista=findViewById(R.id.lista_productos_);
            diseño= new GridLayoutManager(this,2);
        }else{
            lista=findViewById(R.id.lista_productos);
            diseño= new LinearLayoutManager(this);
        }

        lista.setLayoutManager(diseño);

        cargandoProductos();

        SwipeableRecyclerViewTouchListener desliza = new SwipeableRecyclerViewTouchListener(lista, new SwipeableRecyclerViewTouchListener.SwipeListener() {
            @Override
            public boolean canSwipeLeft(int position) {
                return true;
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
                /*for (int position : ints) {
                    lista_P.remove(position);
                    adaptador.notifyItemRemoved(position);
                }
                adaptador.notifyDataSetChanged();*/

                adaptador_productos ada=(adaptador_productos) recyclerView.getAdapter();
                productos p=ada.listaP.get(ints[0]);
                Toast.makeText(busquedas.this, p.getNombre(), Toast.LENGTH_SHORT).show();
            }
        });

        lista.addOnItemTouchListener(desliza);

    }

    private void cargandoProductos(){
        lista_P.clear();
        conec=new conexiones_base("",this.getApplicationContext());
        lista_P=conec.todosLosProductos("");
        adaptador=new adaptador_productos(lista_P);
        lista.setAdapter(adaptador);
    }

    private void abrirVentanaDialogo(){

        AlertDialog.Builder mBuilder= new AlertDialog.Builder(busquedas.this);
        View mView=getLayoutInflater().inflate(R.layout.tipo_codigo, null);

        final TextView txtB=(TextView)mView.findViewById(R.id.txtCodigoManual);

        Button btnManual=(Button)mView.findViewById(R.id.btnBManual);
        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtB.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Tienes que ingresar un codigo", Toast.LENGTH_LONG).show();
                }else {
                    abrirActiviti(txtB.getText().toString());
                }
            }
        });

        Button btnE=(Button)mView.findViewById(R.id.btnEscanear);
        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escanear();
            }
        });


        mBuilder.setView(mView);
        AlertDialog dialog= mBuilder.create();
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

    private void abrirActiviti(String codigo){

        accediendoDatos();

        res = volleySingleton.getInstance(this).getmRequesQueve();
        String url1="http://"+ipe+":"+puertoe+"/servidor/productos.php?codi="+codigo;


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url1, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                try {
                    JSONObject objeto = response.getJSONObject(0);
                    sesio.setProducto(objeto.getString("producto"));
                    sesio.setCodigo(objeto.getString("codigo"));
                    sesio.setCategoria(objeto.getString("categoria"));
                    sesio.setMarca(objeto.getString("marca"));
                    sesio.setEstante(objeto.getString("estante"));
                    sesio.setExistencia(objeto.getString("existencias"));
                    sesio.setId(objeto.getString("id"));
                    sesio.setId_producto(objeto.getString("id_pro"));

                    Toast.makeText(getApplicationContext(),"Cargando información del producto",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(busquedas.this, info_producto.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No existe ese producto", Toast.LENGTH_LONG).show();
            }
        });


        res.add(jsonArrayRequest);

    }

    public void escanear(){
        IntentIntegrator intent=new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);

        intent.setPrompt("ESCANEAR");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }

    private void ocultarTeclado(){
        View view=this.getCurrentFocus();
        if(view != null){
            InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode, resultCode,data);

        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "Escaneo cancelado", Toast.LENGTH_SHORT).show();
            }else{
                abrirActiviti(result.getContents().toString());
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onResume() {
        if(sesio.getActualiza()){
            cargandoProductos();
            Toast.makeText(getApplicationContext(),"Actualizando", Toast.LENGTH_LONG).show();
            sesio.setActualiza(false);
        }
        super.onResume();
    }

    @Override
    protected void onStop() {

        super.onStop();
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
                ((adaptador_productos) adaptador).setFiltro(lista_P);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        try {
            ArrayList<productos>lista=filtro(lista_P, s);
            ((adaptador_productos) adaptador).setFiltro(lista);

        }catch (Exception e){
            e.printStackTrace();
        }
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
}
