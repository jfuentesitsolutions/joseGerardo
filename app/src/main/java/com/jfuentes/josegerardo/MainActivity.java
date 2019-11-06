package com.jfuentes.josegerardo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jfuentes.josegerardo.clases.entidades.entity;
import com.jfuentes.josegerardo.clases.entidades.presentaciones;
import com.jfuentes.josegerardo.clases.utilidades;
import com.jfuentes.josegerardo.maestro_detalle_productos.contenido_elemento;
import com.jfuentes.josegerardo.maestro_detalle_productos.lista_productos;
import com.jfuentes.josegerardo.clases.mensaje_dialogo_by_jfuentes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RequestQueue res;
    ImageButton bu, usu, estan;
    String ipe="", puertoe="";
    Intent intent;
    SharedPreferences prefe;
    ArrayList<entity> lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        conexiones_base base= new conexiones_base(getApplication());
        String[] va={"Hola","1","2","Buenas"};
        String url= null;
        try {
            url = conexiones_base.sentencia(new presentaciones(), base.getIpe(), base.getPuertoe(), utilidades.REGISTROS,va);
            Log.d("evento",url);
            lista=base.extraer(url,utilidades.PRESENTACIONES);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        setContentView(R.layout.activity_main);

        usu=findViewById(R.id.imgBuscar);
        bu=findViewById(R.id.imgBuscar2);
        estan=findViewById(R.id.imgEstantes);

        usu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento= new Intent(MainActivity.this, lista_productos.class);
                startActivity(intento);
            }
        });

        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirVentanaDialogo();
            }
        });

        estan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent= new Intent(getApplicationContext(),lista_estantes.class);
                startActivity(intent);
            }
        });

    }

    private void abrirVentanaDialogo(){

        AlertDialog.Builder mBuilder= new AlertDialog.Builder(MainActivity.this);
        View mView=getLayoutInflater().inflate(R.layout.tipo_codigo, null);

        final TextView txtB=mView.findViewById(R.id.txtCodigoManual);

        Button btnManual=mView.findViewById(R.id.btnBManual);
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

        Button btnE=mView.findViewById(R.id.btnEscanear);
        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                escanear();
            }
        });


        mBuilder.setView(mView);
        AlertDialog dialog= mBuilder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


    }

    public void escanear(){
        IntentIntegrator intent=new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);

        intent.setPrompt("Escanear el codigo de barras del productos");
        intent.setCameraId(0);
        intent.setBeepEnabled(true);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void quitarDatosPersonales(){
        prefe= getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=prefe.edit();
        editor.putString("usu", "");
        editor.putString("contra", "");
        editor.putBoolean("check", false);
        editor.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();


        if(id==R.id.btn_conectar){
            Toast.makeText(getApplicationContext(),"Esta conectado",Toast.LENGTH_LONG).show();
        }else if(id==R.id.btn_cerrar){
            System.exit(0);
        }else if(id==R.id.btnCerrarS){
            intent=new Intent(this, loggin.class);
            startActivity(intent);
            quitarDatosPersonales();
            this.finish();
        }
        return super.onOptionsItemSelected(item);
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
                    productos producto= new productos(
                            objeto.getString("producto"),
                            objeto.getString("codigo"),
                            objeto.getString("categoria"),
                            objeto.getString("marca"),
                            objeto.getString("estante"),
                            objeto.getString("id"),
                            objeto.getString("exis"),
                            objeto.getString("id_pro"),
                            objeto.getString("idcat"),
                            objeto.getString("idmar"),
                            objeto.getString("idest"));

                   Bundle bundle= new Bundle();
                   bundle.putSerializable("producto",producto);
                   bundle.putBoolean("busqueda_codigo",true);

                    Toast.makeText(getApplicationContext(),"Cargando información del producto",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, contenido_elemento.class);
                    intent.putExtras(bundle);
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

    @Override
    public void onBackPressed() {

        final mensaje_dialogo_by_jfuentes mensaje=new mensaje_dialogo_by_jfuentes(this,"Desea salir de la aplicación",
                "Si continua saldra de la aplicación");

        mensaje.getAcepta().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
                mensaje.cerrar();
            }
        });

        mensaje.getCancelar().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensaje.cerrar();
            }
        });
    }
}
