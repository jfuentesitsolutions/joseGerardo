package com.jfuentes.josegerardo.maestro_detalle_productos;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.jfuentes.josegerardo.Adaptador;
import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.adaptadores.spinner_adaptador;
import com.jfuentes.josegerardo.adaptadores.spinner_adaptador_2;
import com.jfuentes.josegerardo.agregar_cantidades;
import com.jfuentes.josegerardo.clases.entidad;
import com.jfuentes.josegerardo.clases.mensaje_dialogo_by_jfuentes;
import com.jfuentes.josegerardo.clases.utilidades;
import com.jfuentes.josegerardo.conexiones_base;
import com.jfuentes.josegerardo.maestro_detalle_productos.adaptador_maestro.adaptador;
import com.jfuentes.josegerardo.presentacion;
import com.jfuentes.josegerardo.productos;
import com.jfuentes.josegerardo.singleton;
import com.jfuentes.josegerardo.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class fragment_contenido extends Fragment implements View.OnClickListener {

    TextView nombre, coodigo, categoria, marca, estante, existencia;
    Bundle bundle=null;
    productos pro=null;
    CardView tarNombre, tarCategoria, tarMarca, tarEstante, tarExistencia, tarPresenta;
    ViewGroup con;
    ArrayList<entidad> lista_categoria= new ArrayList<entidad>();
    ArrayList<entidad> lista_marca= new ArrayList<entidad>();
    ArrayList<entidad> lista_estante= new ArrayList<entidad>();
    ArrayList<presentacion> lista= new ArrayList<presentacion>();
    ArrayList<productos> list_pro= new ArrayList<productos>();
    conexiones_base conex;
    RequestQueue res;
    EditText nombree;
    String ipe, puertoe;
    presentacion pres;
    Integer existencias;
    public static final int CANTIDADES=23;
    singleton sesion=singleton.getInstance();
    RecyclerView listaaa;
    Boolean tablet=false;
    ProgressBar progreso;
    CollapsingToolbarLayout appBarLayout;


    public fragment_contenido() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle=getArguments();
        if (bundle!=null) {
            pro=(productos) bundle.getSerializable("pro");
            Activity activity = this.getActivity();

            listaaa=activity.findViewById(R.id.lista_ele);
            appBarLayout = activity.findViewById(R.id.toolbar_layout);

            if (appBarLayout != null) {
                appBarLayout.setTitle(pro.getNombre());
            }

            if(listaaa!=null){
                tablet=true;
                progreso=activity.findViewById(R.id.progre);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment_contenido, container, false);

        con=container;
        nombre=rootView.findViewById(R.id.txtNombre);
        coodigo=rootView.findViewById(R.id.txtCodigo);
        categoria=rootView.findViewById(R.id.txtCate);
        marca=rootView.findViewById(R.id.txtMarc);
        estante=rootView.findViewById(R.id.txtEstan);
        existencia=rootView.findViewById(R.id.txtExiste);

        nombre.setText(pro.getNombre());
        coodigo.setText(pro.getCodigo());
        categoria.setText(pro.getCategoria());
        marca.setText(pro.getMarca());
        estante.setText(pro.getEstante());
        existencia.setText(pro.getExistencias());
        existencias=Integer.parseInt(pro.getExistencias());

        //Definiendo las tarjetas

        tarNombre=rootView.findViewById(R.id.tarjetaNombre);
        tarCategoria=rootView.findViewById(R.id.tarjetaCategoria);
        tarMarca=rootView.findViewById(R.id.tarjetaMarca);
        tarEstante=rootView.findViewById(R.id.tarjetaEstante);
        tarExistencia=rootView.findViewById(R.id.tarjetaExistencia);
        tarPresenta=rootView.findViewById(R.id.tarjetaPresentaciones);


        tarNombre.setOnClickListener(this);
        tarCategoria.setOnClickListener(this);
        tarMarca.setOnClickListener(this);
        tarEstante.setOnClickListener(this);
        tarExistencia.setOnClickListener(this);
        if(tablet){
            tarPresenta.setOnClickListener(this);
        }else{
            tarPresenta.setVisibility(View.GONE);
        }



        //Retornando las listas de datos

        conex=new conexiones_base("", con.getContext());
        lista_categoria=conex.llenarSpinerCat();
        lista_marca=conex.llenarSpinerMar();
        lista_estante=conex.llenarSpinerEst();
        accediendoDatos();
        String url="http://"+ipe+":"+puertoe+"/servidor/presentaciones.php?pro="+pro.getId();
        conexion(url);

        /*try{
            lista_productos lis=(lista_productos)getActivity();
            RecyclerView llista=lis.lista;
            Toast.makeText(con.getContext(),String.valueOf(llista.getAdapter().getItemCount()),Toast.LENGTH_LONG).show();

        }catch (Exception e){
        }*/

        return rootView;
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        switch (id){
            case R.id.tarjetaNombre:{
                ventanasDialogos(utilidades.DIALOGO_CAMBIO_NOMBRE, R.layout.dialogo_cambio_nombre);
                break;
            }

            case R.id.tarjetaCategoria:{
                ventanasDialogos(utilidades.DIALOGO_CAMBIO_CATEGORIA, R.layout.dialogo_cambio_categoria);
                break;
            }

            case R.id.tarjetaMarca:{
                ventanasDialogos(utilidades.DIALOGO_CAMBIO_MARCA,R.layout.dialogo_cambio_marca);
                break;
            }

            case R.id.tarjetaEstante:{
                ventanasDialogos(utilidades.DIALOGO_CAMBIO_ESTANTE, R.layout.dialogo_cambio_estante);
                break;
            }

            case R.id.tarjetaExistencia:{
                ventanasDialogos(utilidades.EXISTENCIAS, R.layout.dialogo_cambio_cantidades);
                break;
            }

            case R.id.tarjetaPresentaciones:{
                abrirVentanaDialogo();
                break;
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CANTIDADES){
            if(resultCode==RESULT_OK){
                existencia.setText(data.getStringExtra("res"));
                conex.actualizandoDatos("4",data.getStringExtra("res"), pro.getId());
            }
        }
    }

    private void ventanasDialogos(int tv, int layout){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(con.getContext());
        View vista=getLayoutInflater().inflate(layout,null);
        dialogo.setView(vista);
        final AlertDialog dialog=dialogo.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        switch (tv){
            case utilidades.DIALOGO_CAMBIO_NOMBRE:{
                nombree=vista.findViewById(R.id.txtDia_Nombre_producto);
                nombree.setText(pro.getNombre());

                Button btn=vista.findViewById(R.id.dia_btnCambiar);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String URL="http://"+ipe+":"+puertoe+"/servidor/actualizando_nombre.php";
                        if(nombree.getText().toString().isEmpty()){
                            Toast.makeText(con.getContext(),"No puedesde dejar en blanco este campo",Toast.LENGTH_LONG).show();
                        }else{
                            if(tablet){
                                progreso.setVisibility(View.VISIBLE);
                            }
                            ejecuntandoServicio(URL, nombree.getText().toString(), dialog);
                        }
                    }
                });


                dialog.show();
                break;
            }

            case utilidades.DIALOGO_CAMBIO_CATEGORIA:{
                final Spinner categ=vista.findViewById(R.id.spinerCate);
                Button btnIngreCate=vista.findViewById(R.id.dia_btnCambiarCat);
                spinner_adaptador adapta= new spinner_adaptador(con.getContext(), lista_categoria);
                categ.setAdapter(adapta);
                categ.setSelection(colocarDato(Integer.parseInt(pro.getIdcateg()), lista_categoria));

                btnIngreCate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        entidad en=(entidad) categ.getSelectedItem();
                        pro.setIdcateg(en.getValor());
                        if(tablet){
                            progreso.setVisibility(View.VISIBLE);
                            conex.actualizandoDatos("1", en.getValor(), pro.getIdproducto(), categoria, en.toString(), dialog, true, getActivity(), progreso);
                        }else{
                            conex.actualizandoDatos("1", en.getValor(), pro.getIdproducto(), categoria, en.toString(), dialog, false, null, null);
                        }


                    }
                });

                dialog.show();
                break;
            }

            case utilidades.DIALOGO_CAMBIO_MARCA:{
                final Spinner mar=vista.findViewById(R.id.spinerMarca);
                Button btnIngresa=vista.findViewById(R.id.btn_diag_marca);
                spinner_adaptador adapta=new spinner_adaptador(con.getContext(), lista_marca);
                mar.setAdapter(adapta);
                mar.setSelection(colocarDato(Integer.parseInt(pro.getIdmarca()), lista_marca));

                btnIngresa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        entidad en=(entidad) mar.getSelectedItem();
                        pro.setIdmarca(en.getValor());

                        if(tablet){
                            progreso.setVisibility(View.VISIBLE);
                            conex.actualizandoDatos("2", en.getValor(), pro.getIdproducto(), marca, en.toString(), dialog,true, getActivity(), progreso);
                        }else{
                            conex.actualizandoDatos("2", en.getValor(), pro.getIdproducto(), marca, en.toString(), dialog,false, null,null);
                        }

                    }
                });

                dialog.show();
                break;
            }

            case utilidades.DIALOGO_CAMBIO_ESTANTE:{
                final Spinner estan=vista.findViewById(R.id.spinerEstante);
                Button btnIngresa=vista.findViewById(R.id.btn_diag_esta);
                spinner_adaptador adapta= new spinner_adaptador(con.getContext(), lista_estante);
                estan.setAdapter(adapta);
                estan.setSelection(colocarDato(Integer.parseInt(pro.getIdestante()), lista_estante));

                btnIngresa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        entidad en=(entidad)estan.getSelectedItem();
                        pro.setIdestante(en.getValor());

                        if(tablet){
                            progreso.setVisibility(View.VISIBLE);
                            conex.actualizandoDatos("3", en.getValor(), pro.getIdproducto(), estante, en.toString(), dialog, true, getActivity(),progreso);
                        }else{
                            conex.actualizandoDatos("3", en.getValor(), pro.getIdproducto(), estante, en.toString(), dialog, false, null,null);
                        }

                    }
                });

                dialog.show();
                break;
            }

            case utilidades.EXISTENCIAS:{
                final Spinner prese=vista.findViewById(R.id.dial_spinner_prese);
                Button btnAjustar=vista.findViewById(R.id.btn_dia_ajustar);
                Button btnSumar=vista.findViewById(R.id.btn_dia_sumar);
                final EditText txtCantidad=vista.findViewById(R.id.dia_cantidad);

                spinner_adaptador_2 adapta= new spinner_adaptador_2(con.getContext(),lista);
                prese.setAdapter(adapta);

                btnAjustar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!mensaje(txtCantidad.getText().toString())){
                        pres = (presentacion)prese.getSelectedItem();
                            if(tablet){
                                progreso.setVisibility(View.VISIBLE);
                            }
                        respuesta("ajustar", txtCantidad.getText().toString(),pres.getPresentacion(),true, dialog);
                        }
                    }
                });

                btnSumar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!mensaje(txtCantidad.getText().toString())){
                            pres = (presentacion)prese.getSelectedItem();
                            if(tablet){
                                progreso.setVisibility(View.VISIBLE);
                            }
                        respuesta("sumar", txtCantidad.getText().toString(),pres.getPresentacion(),false, dialog);
                        }
                    }
                });

                dialog.show();
                break;
            }
        }
    }

    private boolean mensaje(String cantidad){
        if(cantidad.isEmpty()){
            Toast.makeText(con.getContext(),"La cantidad no puede quedar vacia", Toast.LENGTH_LONG).show();
            return true;
        }else{
            return false;
        }
    }

    public void conexion(String URL) {
        res = volleySingleton.getInstance(con.getContext()).getmRequesQueve();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject objeto=null;
                String [][] datos=new String[response.length()][5];
                for (int i=0;i<response.length();i++){
                    try {
                        objeto= response.getJSONObject(i);

                        lista.add(new presentacion(objeto.getString("idpre"),
                                objeto.getString("cantidad"),
                                objeto.getString("precio"),
                                objeto.getString("tipo"),
                                objeto.getString("presentacion"),
                                R.drawable.stock_32));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(con.getContext(), "No existen presentaciones", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(con.getContext(), "No hay presentaciones", Toast.LENGTH_LONG).show();
            }
        });
        res.add(jsonArrayRequest);
    }

    private int colocarDato(int opcion, ArrayList<entidad> ent){
        int index=0;
        for (entidad enti: ent ){
            int op=Integer.parseInt(enti.getValor());
            if(op==opcion){
                index=ent.indexOf(enti);
            }
        }
        return index;
    }

    private void ejecuntandoServicio(String URL, final String nom, final androidx.appcompat.app.AlertDialog di){
        res= volleySingleton.getInstance(con.getContext()).getmRequesQueve();

        StringRequest respuesta=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(con.getContext(), "El campo se actualizo con exíto", Toast.LENGTH_LONG).show();
                nombre.setText(nombree.getText().toString());
                pro.setNombre(nombree.getText().toString());
                if(appBarLayout!=null){
                    appBarLayout.setTitle(nombree.getText());
                }

                sesion.setActualiza(true);
                di.cancel();
                cargandoDatosLista();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(con.getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("nombre",nom);
                parametros.put("id", pro.getIdproducto());
                return parametros;
            }
        };

        res.add(respuesta);
    }

    private boolean accediendoDatos(){
        SharedPreferences prefe= con.getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=prefe.edit();
        if(prefe.getString("ip","")==null || prefe.getString("puerto","")==null){
            Toast.makeText(con.getContext(),"No se encuentran datos de conexion", Toast.LENGTH_LONG).show();
            return false;
        }else{
            ipe=prefe.getString("ip","");
            puertoe=prefe.getString("puerto","");
            return true;
        }
    }

    private void respuesta(String tipo, final String canti, String prese, final boolean tipoO, final AlertDialog dia){

        final mensaje_dialogo_by_jfuentes mensaje = new mensaje_dialogo_by_jfuentes(con.getContext(),
                "Mensaje de confirmación",
                "Deseas "+tipo+" "+canti+" "+prese+" de este producto");
        mensaje.getAcepta().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tipoO){
                    ajustandoCantidad(Integer.parseInt(pres.getCantidad()), canti);
                    dia.dismiss();
                    mensaje.cerrar();
                    cargandoDatosLista();
                }else {
                    sumandoCantidad(Integer.parseInt(pres.getCantidad()), canti);
                    dia.dismiss();
                    mensaje.cerrar();
                    cargandoDatosLista();
                }
            }
        });

        mensaje.getCancelar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(con.getContext(),"Operación cancelada", Toast.LENGTH_LONG).show();
                mensaje.cerrar();
            }
        });

    }

    private void ajustandoCantidad(int inte, String ca){
        float in=Float.parseFloat(ca);
        Integer caI=inte;
        float Total=in*caI;
        int canv=(int)Total;

        existencia.setText(String.valueOf(canv));
        conex.actualizandoDatos("4",existencia.getText().toString(), pro.getId());

    }

    private void sumandoCantidad(int inte, String ca){
        float in=Float.parseFloat(ca);
        Integer caI=inte;
        float Total=in*caI;
        int canv=(int)Total+existencias;

        existencia.setText(String.valueOf(canv));
        conex.actualizandoDatos("4",existencia.getText().toString(), pro.getId());
    }

    private void cargandoDatosLista(){
        if(tablet) {
            final lista_productos actyList = (lista_productos) getActivity();

            actyList.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    actyList.cargandoProductos();

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    progreso.setVisibility(View.GONE);
                    actyList.cargandoAdaptador();

                }
            });
        }
    }

    private void abrirVentanaDialogo(){

        AlertDialog.Builder mBuilder= new AlertDialog.Builder(con.getContext());
        View mView=getLayoutInflater().inflate(R.layout.dialogo_presentaciones, null);

        ListView listaa=mView.findViewById(R.id.lista_productos) ;
        Adaptador adap=new Adaptador(con.getContext(), lista);
        listaa.setAdapter(adap);

        mBuilder.setView(mView);
        AlertDialog dialog= mBuilder.create();
        dialog.show();
    }

}
