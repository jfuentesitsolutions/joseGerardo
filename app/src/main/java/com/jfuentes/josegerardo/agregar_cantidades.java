package com.jfuentes.josegerardo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class agregar_cantidades extends AppCompatActivity {

    ArrayList<presentacion> lista_p;
    public final static String TAG="evento";
    Spinner lista_pre;
    Button ajus, suma;
    EditText ca;
    presentacion pres;
    int exitencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_cantidades);

        ca=(EditText)findViewById(R.id.txtCantida);
        ajus=(Button)findViewById(R.id.btn_canti_ajustar);
        suma=(Button)findViewById(R.id.btn_canti_sumar);

        lista_p=getIntent().getParcelableArrayListExtra("lista");
        lista_pre=(Spinner)findViewById(R.id.spiner_lista_pre);

        exitencias=Integer.parseInt(getIntent().getStringExtra("exis"));
        if(lista_p!=null){
            ArrayAdapter<presentacion> adapatador=new ArrayAdapter<presentacion>(this, R.layout.support_simple_spinner_dropdown_item, lista_p);
            lista_pre.setAdapter(adapatador);
        }



        ajus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pres=(presentacion)lista_pre.getSelectedItem();
                if(!ca.getText().toString().isEmpty()){
                    respuesta("Ajustar", ca.getText().toString(),pres.getPresentacion(), true);
                }else{
                    Toast.makeText(getApplicationContext(),"La cantidad no puede queda a cero",Toast.LENGTH_LONG).show();
                }

            }
        });

        suma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pres=(presentacion)lista_pre.getSelectedItem();
                if(!ca.getText().toString().isEmpty()){
                    respuesta("Sumar", ca.getText().toString(),pres.getPresentacion(), false);
                }else{
                    Toast.makeText(getApplicationContext(),"La cantidad no puede queda a cero",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void respuesta(String tipo, String canti, String prese, final boolean tipoO){

        AlertDialog.Builder mensaje=new AlertDialog.Builder(this);
        mensaje.setMessage("Deseas "+tipo+" "+canti+" "+prese+" de este producto");
        mensaje.setTitle("Mensaje de confirmación");
        mensaje.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(tipoO){
                    ajustandoCantidad(Integer.parseInt(pres.getCantidad()));
                }else {
                    sumandoCantidad(Integer.parseInt(pres.getCantidad()));
                }

            }
        });

        mensaje.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"Operacion cancelada", Toast.LENGTH_LONG).show();
                dialogInterface.cancel();
            }
        });

        AlertDialog dialog=mensaje.create();
        dialog.show();
    }

    private void ajustandoCantidad(int inte){
        float in=Float.parseFloat(ca.getText().toString());
        Integer caI=inte;
        float Total=in*caI;
        int canv=(int)Total;

        Intent intento= new Intent();
        intento.putExtra("res",Integer.toString(canv));

        setResult(RESULT_OK, intento);
        finish();
    }

    private void sumandoCantidad(int inte){
        float in=Float.parseFloat(ca.getText().toString());
        Integer caI=inte;
        float Total=in*caI;
        int canv=(int)Total+exitencias;

        Intent intento= new Intent();
        intento.putExtra("res",Integer.toString(canv));

        setResult(RESULT_OK, intento);
        finish();
    }
}
