package com.jfuentes.josegerardo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.inmite.android.lib.validations.form.FormValidator;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;

public class loggin extends AppCompatActivity implements View.OnClickListener {
    conexiones_base cone;
    Button btnIngresa;

    EditText usuario, contra;
    singleton sesio=singleton.getInstance();
    RequestQueue res;
    ImageView configurar;
    String ipe="", puertoe="";

    TextInputLayout usu,co;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);


        usuario=findViewById(R.id.txtUsuario);
        contra=findViewById(R.id.txtContra);
        btnIngresa=findViewById(R.id.btnIngresar);
        configurar=findViewById(R.id.imgConf);

        usu=findViewById(R.id.layouU);
        co=findViewById(R.id.layouC);

        //cone=new conexiones_base("http://192.168.1.13/servidor/prueba.php", this);

        btnIngresa.setOnClickListener(this);

        configurar.setOnClickListener(this);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnIngresar:{
                if(accediendoDatos()){
                    if(!validar()) {
                        String url = "http://" + ipe + ":" + puertoe + "/servidor/validacion_usuarios.php?usu=" + usuario.getText() + "&con=" + contra.getText();
                        conexion(url);
                    }
                }
                break;
            }
            case R.id.imgConf:{
                abrirVentanaDialogo();
                break;
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

    private void guardarDatos(String ip, String puer){
        SharedPreferences prefe= getSharedPreferences("config", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=prefe.edit();
        editor.putString("ip", ip);
        editor.putString("puerto", puer);
        editor.commit();
        Toast.makeText(loggin.this, "Datos almacenados",Toast.LENGTH_LONG).show();
    }

    private void abrirVentanaDialogo(){
        accediendoDatos();

        final AlertDialog.Builder mBuilder= new AlertDialog.Builder(loggin.this);
        View mView=getLayoutInflater().inflate(R.layout.dialogo_configuracion, null);
        final EditText ip=(EditText)mView.findViewById(R.id.txtIp);
        final EditText puer=(EditText)mView.findViewById(R.id.txtPuer);
        Button gra=(Button)mView.findViewById(R.id.btnGuardar);

        mBuilder.setView(mView);
        final AlertDialog dialog= mBuilder.create();

        ip.setText(ipe);
        puer.setText(puertoe);

        gra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ip.getText().toString().isEmpty() && !puer.getText().toString().isEmpty()){
                    guardarDatos(ip.getText().toString(), puer.getText().toString());

                    ip.setText(ipe);
                    puer.setText(puertoe);

                    dialog.dismiss();

                }else{
                    Toast.makeText(loggin.this, "Uno de los campos esta vacio", Toast.LENGTH_LONG).show();
                }
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    public void conexion(String URL) {


            res = volleySingleton.getInstance(this).getmRequesQueve();

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    String variable = "";
                    try {
                        JSONObject objeto = response.getJSONObject(0);
                        variable = objeto.getString("usuario");

                        Intent intent = new Intent(loggin.this, MainActivity.class);
                        startActivity(intent);
                        sesio.setUsuario(variable);
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "El usuario o contraseña no existe", Toast.LENGTH_LONG).show();
                }
            });

            res.add(jsonArrayRequest);


    }

    private boolean validar(){
        String u=usuario.getText().toString();
        String c=contra.getText().toString();

        usu.setErrorEnabled(false);
        co.setErrorEnabled(false);

        if(u.isEmpty()){
            usu.setError("Coloca el usuario");
            return true;
        }

        if(c.isEmpty()){
            co.setError("Coloca la contraseña");
            return true;
        }

        return false;
    }


}
