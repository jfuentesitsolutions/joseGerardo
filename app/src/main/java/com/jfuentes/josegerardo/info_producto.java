package com.jfuentes.josegerardo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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
import com.jfuentes.josegerardo.clases.entidad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class info_producto extends AppCompatActivity {

    public static final String TAG="EVENTO";
    public static final int CANTIDADES=23;
    public static final int PRODUCTO=1;
    public static final int CATEGORIA=2;
    public static final int MARCAS=3;
    public static final int ESTANTES=4;

    singleton sesion=singleton.getInstance();
    EditText pro, codi, cate, marca, esta, exis, dia_nombre, categoria;
    TextView diag_nombre, diag_cate, diag_marca, diag_estante, canti;
    String id, idpro;
    String ipe, puertoe;
    RequestQueue res;
    Button presenta;
    ArrayList<presentacion> lista= new ArrayList<presentacion>();
    ArrayList<entidad> lista_categoria= new ArrayList<entidad>();
    ArrayList<entidad> lista_mar= new ArrayList<entidad>();
    ArrayList<entidad> lista_est= new ArrayList<entidad>();
    Spinner categ, mar, estan;
    conexiones_base conex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_info_producto);


        pro=findViewById(R.id.txtnomProducto);
        codi=findViewById(R.id.txtCodigo);
        cate=findViewById(R.id.txtCategoria);
        marca=findViewById(R.id.txtMarca);
        esta=findViewById(R.id.txtEstante);
        exis=findViewById(R.id.txtExisten);
        categoria=(EditText)findViewById(R.id.txtCategoria);
        canti=(TextView)findViewById(R.id.txtInfor_existe);


        diag_nombre=(TextView)findViewById(R.id.txtDiag_nom_pro);
        diag_cate=(TextView)findViewById(R.id.txtInfo_categoria);
        diag_marca=(TextView)findViewById(R.id.txtInfo_marca);
        diag_estante=(TextView)findViewById(R.id.txtInfo_Estante);


        conex=new conexiones_base("", getApplicationContext());
        lista_est=conex.llenarSpinerEst();
        lista_categoria=conex.llenarSpinerCat();
        lista_mar=conex.llenarSpinerMar();


        pro.setText(sesion.getProducto());
        codi.setText(sesion.getCodigo());
        cate.setText(sesion.getCategoria());
        marca.setText(sesion.getMarca());
        esta.setText(sesion.getEstante());
        exis.setText(sesion.getExistencia());
        id=sesion.getId();
        idpro=sesion.getId_producto();



        accediendoDatos();
        String url="http://"+ipe+":"+puertoe+"/servidor/presentaciones.php?pro="+id;
        conexion(url);


        presenta=findViewById(R.id.btnVerPres);
        presenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirVentanaDialogo();
            }
        });

        diag_nombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventanasDialogos(PRODUCTO,R.layout.dialogo_cambio_nombre);
            }
        });
        diag_cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventanasDialogos(CATEGORIA,R.layout.dialogo_cambio_categoria);
            }
        });

        diag_marca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventanasDialogos(MARCAS, R.layout.dialogo_cambio_marca);
            }
        });

        diag_estante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventanasDialogos(ESTANTES, R.layout.dialogo_cambio_estante);
            }
        });

        canti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento=new Intent(getApplicationContext(),agregar_cantidades.class);
                intento.putParcelableArrayListExtra("lista",lista);
                intento.putExtra("exis", exis.getText().toString());
                startActivityForResult(intento, CANTIDADES);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG,Integer.toString(requestCode));
        if(requestCode==CANTIDADES){
            if(resultCode==RESULT_OK){
                exis.setText(data.getStringExtra("res"));
                conex.actualizandoDatos("4",data.getStringExtra("res"), id);
            }
        }
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

        AlertDialog.Builder mBuilder= new AlertDialog.Builder(info_producto.this);
        View mView=getLayoutInflater().inflate(R.layout.dialogo_presentaciones, null);

        ListView listaa=(ListView)mView.findViewById(R.id.lista_productos) ;
        Adaptador adap=new Adaptador(this.getApplicationContext(), lista);
        listaa.setAdapter(adap);

        mBuilder.setView(mView);
        AlertDialog dialog= mBuilder.create();
        dialog.show();
    }

    private void ventanasDialogos(int tv, int layout){

        final AlertDialog.Builder dialogo= new AlertDialog.Builder(info_producto.this);
        View vista=getLayoutInflater().inflate(layout, null);
        dialogo.setView(vista);
        final AlertDialog dia=dialogo.create();

        switch (tv){
            case PRODUCTO :{

                dia_nombre=(EditText)vista.findViewById(R.id.txtDia_Nombre_producto);
                dia_nombre.setText(pro.getText().toString());


                Button btnIngre=(Button)vista.findViewById(R.id.dia_btnCambiar);
                btnIngre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String URL="http://"+ipe+":"+puertoe+"/servidor/actualizando_nombre.php";
                        if(dia_nombre.getText().toString().isEmpty()){
                            Toast.makeText(getApplication(),"No puedesde dejar en blanco este campo",Toast.LENGTH_LONG).show();
                        }else{
                            ejecuntandoServicio(URL, dia_nombre.getText().toString(), dia);
                        }

                    }
                });


                dia.show();
                break;
            }

            case CATEGORIA :{

                categ=(Spinner)vista.findViewById(R.id.spinerCate);
                Button btnIngreCate=vista.findViewById(R.id.dia_btnCambiarCat);
                ArrayAdapter<entidad> adaptador=new ArrayAdapter<entidad>(this, R.layout.support_simple_spinner_dropdown_item,
                        lista_categoria);

                categ.setAdapter(adaptador);
                categ.setSelection(colocarDato(Integer.parseInt(sesion.getIdcategoria()), lista_categoria));

                btnIngreCate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        entidad en=(entidad) categ.getSelectedItem();
                        conex.actualizandoDatos("1", en.getValor(), idpro, cate, en.toString(), dia);
                    }
                });

                //monitoreando
                Log.d(TAG,sesion.getIdcategoria());
                dia.show();
                break;
            }

            case MARCAS :{
                mar=(Spinner)vista.findViewById(R.id.spinerMarca);
                Button btnIngremar=vista.findViewById(R.id.btn_diag_marca);
                ArrayAdapter<entidad> adaptador= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, lista_mar);
                mar.setAdapter(adaptador);
                mar.setSelection(colocarDato(Integer.parseInt(sesion.getId_marca()), lista_mar));

                btnIngremar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        entidad en=(entidad) mar.getSelectedItem();
                        conex.actualizandoDatos("2", en.getValor(), idpro, marca, en.toString(), dia);
                    }
                });

                //monitoreando
                Log.d(TAG,sesion.getId_marca());
                dia.show();
                break;
            }

            case ESTANTES :{

                estan=(Spinner)vista.findViewById(R.id.spinerEstante);
                Button btnIngrees=vista.findViewById(R.id.btn_diag_esta);
                ArrayAdapter<entidad> adaptador= new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, lista_est);
                estan.setAdapter(adaptador);
                estan.setSelection(colocarDato(Integer.parseInt(sesion.getId_estante()), lista_est));

                btnIngrees.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        entidad en=(entidad) estan.getSelectedItem();
                        conex.actualizandoDatos("3", en.getValor(), idpro, esta, en.toString(), dia);
                    }
                });

                //monitoreando
                Log.d(TAG,sesion.getId_estante());
                dia.show();
                break;
            }
        }

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

    private void ejecuntandoServicio(String URL, final String nom, final AlertDialog di){
        res=volleySingleton.getInstance(this).getmRequesQueve();

        StringRequest respuesta=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "El campo se actualizo con exíto", Toast.LENGTH_LONG).show();
                pro.setText(dia_nombre.getText().toString());
                sesion.setActualiza(true);
                di.cancel();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros= new HashMap<String, String>();
                parametros.put("nombre",nom);
                parametros.put("id", idpro);
                return parametros;
            }
        };

        res.add(respuesta);
    }
}
