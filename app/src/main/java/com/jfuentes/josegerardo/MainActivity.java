package com.jfuentes.josegerardo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;


public class MainActivity extends AppCompatActivity {

    singleton sesio=singleton.getInstance();
    RequestQueue res;
    TextView nombre;
    ImageView bu, usu, pro;
    String ipe="", puertoe="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usu=findViewById(R.id.imgBuscar);
        usu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento= new Intent(MainActivity.this,busquedas.class);
                startActivity(intento);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();


        if(id==R.id.btn_conectar){
            Toast.makeText(getApplicationContext(),"Esta conectado",Toast.LENGTH_LONG).show();
        }else if(id==R.id.btn_cerrar){
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

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
                    sesio.setIdcategoria(objeto.getString("idcat"));
                    sesio.setId_marca(objeto.getString("idmar"));
                    sesio.setId_estante(objeto.getString("idest"));

                    Toast.makeText(getApplicationContext(),"Cargando información del producto",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, info_producto.class);
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
}
