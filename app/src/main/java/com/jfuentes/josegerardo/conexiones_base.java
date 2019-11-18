package com.jfuentes.josegerardo;

import androidx.appcompat.app.AlertDialog;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.jfuentes.josegerardo.clases.entidad;
import com.jfuentes.josegerardo.clases.entidades.categorias;
import com.jfuentes.josegerardo.clases.entidades.entity;
import com.jfuentes.josegerardo.clases.entidades.estantes;
import com.jfuentes.josegerardo.clases.entidades.marcas;
import com.jfuentes.josegerardo.clases.entidades.presentaciones;
import com.jfuentes.josegerardo.maestro_detalle_productos.lista_productos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class conexiones_base {
    public static final String TAG="EVENTO";
    String URL="";
    RequestQueue res;
    StringRequest respuesta;
    Context contexto;

    public String getIpe() {
        return ipe;
    }

    public String getPuertoe() {
        return puertoe;
    }

    String ipe, puertoe;
    ArrayList<productos> lista_P= new ArrayList<productos>();
    ArrayList<entidad> lista_C= new ArrayList<entidad>();
    ArrayList<entidad> lista_M= new ArrayList<entidad>();
    ArrayList<entidad> lista_E= new ArrayList<entidad>();
    ArrayList<entity> lis_E=new ArrayList<>();

    public String[][] getDatos() {
        return datos;
    }

    String[][] datos;
    singleton sesio=singleton.getInstance();


    public conexiones_base(String URL, Context c) {
        this.URL=URL;
        this.contexto=c;
        accediendoDatos();
        res=volleySingleton.getInstance(contexto).getmRequesQueve();
    }

    public conexiones_base() {
        accediendoDatos();
    }

    public conexiones_base(Context contexto) {
        this.contexto=contexto;
        accediendoDatos();
        res=volleySingleton.getInstance(contexto).getmRequesQueve();
    }

    public void conexion(){
        res=volleySingleton.getInstance(contexto).getmRequesQueve();

        JsonArrayRequest jsonArrayRequest= new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String variable="";
                try {
                    JSONObject objeto = response.getJSONObject(0);
                    variable=objeto.getString("usuario");

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(contexto.getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contexto.getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        res.add(jsonArrayRequest);

    }

    private boolean accediendoDatos(){
        SharedPreferences prefe= contexto.getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=prefe.edit();
        if(prefe.getString("ip","")==null || prefe.getString("puerto","")==null){
            Toast.makeText(contexto,"No se encuentran datos de conexion", Toast.LENGTH_LONG).show();
            return false;
        }else{
            ipe=prefe.getString("ip","");
            puertoe=prefe.getString("puerto","");
            return true;
        }
    }

    public ArrayList<productos> todosLosProductos(String bus){

        String url1="http://"+ipe+":"+puertoe+"/servidor/productosXnombre.php?nom="+bus;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url1, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject objeto=null;

                for (int i=0;i<response.length();i++){
                    try {
                        objeto= response.getJSONObject(i);

                        lista_P.add(new productos(objeto.getString("producto"),
                                objeto.getString("codigo"),
                                objeto.getString("categoria"),
                                objeto.getString("marca"),
                                objeto.getString("estante"),
                                objeto.getString("id"),
                                objeto.getString("exis"),
                                objeto.getString("id_pro"),
                                objeto.getString("idmar"),
                                objeto.getString("idcat"),
                                objeto.getString("idest")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(contexto, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contexto, "No existe ese producto", Toast.LENGTH_LONG).show();
            }
        });

        res.add(jsonArrayRequest);
        return this.lista_P;
    }

    public ArrayList<entidad> llenarSpinerCat(){

        String url1="http://"+ipe+":"+puertoe+"/servidor/retornando_tablas.php?tabla="+1;
        Log.d(TAG,url1);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url1, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject objeto=null;

                for (int i=0;i<response.length();i++){
                    try {
                        objeto= response.getJSONObject(i);

                                lista_C.add(new entidad(objeto.getString("nombre_categoria"),
                                        objeto.getString("idcategoria"),
                                        objeto.getString("descripcion"),
                                        R.drawable.list_32));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(contexto, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                Log.d("evento","peueba"+String.valueOf(response.length()));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contexto, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        res.add(jsonArrayRequest);
        Log.d("evento","pppp;"+String.valueOf(lista_C.size()));

        return lista_C;
    }

    public ArrayList<entidad> llenarSpinerMar(){

        String url1="http://"+ipe+":"+puertoe+"/servidor/retornando_tablas.php?tabla="+2;

        Log.d(TAG,url1);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url1, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject objeto=null;

                for (int i=0;i<response.length();i++){
                    try {
                        objeto= response.getJSONObject(i);


                        lista_M.add(new entidad(objeto.getString("nombre"),
                                objeto.getString("idmarca"),
                                objeto.getString("descripcion"),
                                R.drawable.buffer_32));



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(contexto, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contexto, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        res.add(jsonArrayRequest);

        Log.d(TAG,"2");
        return lista_M;
    }

    public ArrayList<entidad> llenarSpinerEst(){

        String url1="http://"+ipe+":"+puertoe+"/servidor/retornando_tablas.php?tabla="+3;

        Log.d(TAG,url1);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url1, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject objeto=null;

                for (int i=0;i<response.length();i++){
                    try {
                        objeto= response.getJSONObject(i);


                        lista_E.add(new entidad(objeto.getString("nombre"),
                                objeto.getString("idestante"),
                                objeto.getString("descripcion"),
                                R.drawable.warehouse_32));



                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(contexto, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contexto, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        res.add(jsonArrayRequest);
        Log.d(TAG,"3");
        return lista_E;
    }

    public void actualizandoDatos(final String opcion, final String ele, final String id, final TextView tex, final String cate, final AlertDialog aler, final boolean tablet, final Activity acti, final ProgressBar pro){
        accediendoDatos();
        String URL="http://"+ipe+":"+puertoe+"/servidor/actualizando_datos.php";

        Log.d(TAG,opcion+" "+ele+" "+id);

        respuesta= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(contexto, response, Toast.LENGTH_LONG).show();
                Log.d(TAG, response);
                tex.setText(cate);
                sesio.setActualiza(true);
                aler.cancel();
                cargandoDatosLista(tablet, acti, pro);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contexto,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("opcion",opcion);
                parametros.put("ele",ele);
                parametros.put("id", id);
                return parametros;
            }
        };
        res.add(respuesta);
    }

    private void cargandoDatosLista(boolean tablet, Activity activity, final ProgressBar progreso){
        if(tablet) {
            final lista_productos actyList = (lista_productos) activity;

            actyList.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    actyList.cargandoProductos();

                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    progreso.setVisibility(View.GONE);
                    actyList.cargandoAdaptador();

                }
            });
        }
    }

    public void actualizandoDatos(final String opcion, final String ele, final String id){
        accediendoDatos();
        String URL="http://"+ipe+":"+puertoe+"/servidor/actualizando_datos.php";

        Log.d(TAG,opcion+" "+ele+" "+id);

        respuesta= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(contexto, response, Toast.LENGTH_LONG).show();
                Log.d(TAG, response);
                sesio.setActualiza(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contexto,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams(){
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("opcion",opcion);
                parametros.put("ele",ele);
                parametros.put("id", id);
                return parametros;
            }
        };
        res.add(respuesta);
    }

    public void insertar(final Map<String, String> para){

        String url="http://"+ipe+":"+puertoe+"/servidor/tablas/entidades.php";
        respuesta= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(contexto, response, Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contexto,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams(){
                return para;
            }
        };
        res.add(respuesta);
    }

    public ArrayList<entity> extraer(String url, final String entidad){
        Log.d("evento", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject objeto=null;

                for (int i=0;i<response.length();i++){
                    try {
                        objeto= response.getJSONObject(i);

                        switch (entidad){
                            case "estantes":{
                                lis_E.add(new estantes(objeto.getString("idestante"),
                                        objeto.getString("nombre"),
                                        objeto.getString("descripcion")));
                                break;
                            }

                            case "marcas":{
                                lis_E.add(new marcas(objeto.getString("idmarca"),
                                        objeto.getString("nombre"),
                                        objeto.getString("descripcion")));
                                break;
                            }

                            case "categorias":{
                                lis_E.add(new categorias(objeto.getString("descripcion"),
                                        objeto.getString("nombre_categoria"),
                                        objeto.getString("idcategoria")));
                                break;
                            }

                            case "presentaciones":{
                                lis_E.add(new presentaciones(objeto.getString("idpresentacion"),
                                        objeto.getString("nombre_presentacion"),
                                        objeto.getString("descripcion"),
                                        objeto.getString("estado")));
                                break;
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(contexto, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                Log.d("evento","Prueba"+String.valueOf(response.length()));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(contexto, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        res.add(jsonArrayRequest);
        return lis_E;
    }

    public static String sentencia(Object entidad, String ip, String puer, String opcion, String[] valores) throws ClassNotFoundException {
        StringBuilder sentencia= new StringBuilder();
        Class<?> clase=Class.forName(entidad.getClass().getName());
        Field[] campos=clase.getDeclaredFields();

        sentencia.append("http://");
        sentencia.append(ip+":"+puer);
        sentencia.append("/servidor/tablas/entidades.php?entidad=");
        sentencia.append(entidad.getClass().getSimpleName());
        sentencia.append("&opcion=");
        sentencia.append(opcion);

        for (int i=0; i<campos.length; i++){
            sentencia.append("&"+campos[i].getName()+"="+valores[i]);
        }

        return sentencia.toString();
    }

}
