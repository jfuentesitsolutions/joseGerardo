package com.jfuentes.josegerardo.maestro_detalle_productos.adaptador_maestro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.adaptadores.adaptador_presentaciones;
import com.jfuentes.josegerardo.clases.entidad;
import com.jfuentes.josegerardo.clases.entidades.categorias;
import com.jfuentes.josegerardo.clases.entidades.entity;
import com.jfuentes.josegerardo.clases.entidades.presentaciones_productos;
import com.jfuentes.josegerardo.clases.entidades.sucursales;
import com.jfuentes.josegerardo.clases.utilidades;
import com.jfuentes.josegerardo.conexiones_base;
import com.jfuentes.josegerardo.maestro_detalle_productos.lista_productos;
import com.jfuentes.josegerardo.productos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class nuevo_producto extends AppCompatActivity {

    Spinner lista_sucursales;
    Spinner lista_categorias, lista_marcas, lista_estantes;
    conexiones_base cone;
    ArrayList<entidad> lista_cate, lista_mar, lista_esta, lista_sucu, lista_prese;
    ArrayList<presentaciones_productos> lista_pro_pre=new ArrayList<>();
    RecyclerView lista_pre;
    EditText txtNomPro, txtCodigo, txtExis;
    TextInputLayout layouNom, layouCodi, layouExis;
    adaptador_presentaciones adapta;
    ImageView codigo;
    Switch estado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.color_mora);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_producto);

        setTitle("Nuevo producto");

        lista_sucursales=findViewById(R.id.lista_sucu);
        lista_categorias=findViewById(R.id.lista_cate);
        lista_marcas=findViewById(R.id.lista_marca);
        lista_estantes=findViewById(R.id.lista_esta);
        lista_pre=findViewById(R.id.lst_pre);
        txtNomPro=findViewById(R.id.txtNombre_np);
        layouNom=findViewById(R.id.layou_nombre);
        txtCodigo=findViewById(R.id.txtCodigo_np);
        layouCodi=findViewById(R.id.layou_codigo);
        txtExis=findViewById(R.id.txtExis_np);
        layouExis=findViewById(R.id.layou_exis);
        codigo=findViewById(R.id.codigo_scaner);
        estado=findViewById(R.id.swES);

        cone=new conexiones_base(this);

        lista_cate=cone.llenarSpinerCat();
        lista_cate.add(new entidad("Elija una categoria","0",""));

        lista_mar=cone.llenarSpinerMar();
        lista_mar.add(new entidad("Elija una marca","0",""));

        lista_esta=cone.llenarSpinerEst();
        lista_esta.add(new entidad("Elija un estante","0",""));

        lista_sucu=cone.llenarSpinerSuc();
        lista_sucu.add(new entidad("Elija una sucursal","0",""));

        lista_prese=cone.llenarSpinerPre();
        lista_prese.add(new entidad("Elija una presentación","0",""));


        final ArrayAdapter<entidad> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lista_cate);
        lista_categorias.setAdapter(adapter);

        ArrayAdapter<entidad> adapter1=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lista_mar);
        lista_marcas.setAdapter(adapter1);

        ArrayAdapter<entidad> adapter2=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lista_esta);
        lista_estantes.setAdapter(adapter2);

        ArrayAdapter<entidad> adapter3=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lista_sucu);
        lista_sucursales.setAdapter(adapter3);

        adapta = new adaptador_presentaciones(lista_pro_pre);
        lista_pre.setLayoutManager(new LinearLayoutManager(this));
        lista_pre.setAdapter(adapta);

        SwipeableRecyclerViewTouchListener desliza=new SwipeableRecyclerViewTouchListener(lista_pre, new SwipeableRecyclerViewTouchListener.SwipeListener() {
            @Override
            public boolean canSwipeLeft(int position) {
                return true;
            }

            @Override
            public boolean canSwipeRight(int position) {
                return false;
            }

            @Override
            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] ints) {
                lista_pro_pre.remove(ints[0]);
                adapta.notifyDataSetChanged();
            }

            @Override
            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] ints) {

            }
        });

        lista_pre.addOnItemTouchListener(desliza);
    }

    public void guardando(View view){
        if(!validar()){
            Log.d("aqui","hola");
                insertandoProducto();
        }
    }

    public void agregaPresentacion(View view){
        if(!txtNomPro.getText().toString().isEmpty()){
            dialogoAgregar().show();
            layouNom.setErrorEnabled(false);
        }else{
            layouNom.setError("Colocale un nombre al producto");
        }

    }

    private AlertDialog dialogoAgregar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialogo_presentacion_producto, null);
        builder.setView(v);
        final AlertDialog dialog=builder.create();
        Button btn=v.findViewById(R.id.btnDcan);
        Button btn1=v.findViewById(R.id.btnDagre);
        TextView titulo=v.findViewById(R.id.lblTitulo);

        final TextInputEditText txtCantida=v.findViewById(R.id.txtCantidaI);
        final TextInputLayout layoCan=v.findViewById(R.id.layouCantiInterna);
        final TextInputEditText txtPrecio=v.findViewById(R.id.txtPrecio);
        final TextInputLayout layouPre=v.findViewById(R.id.layouPrecio);

        final Spinner listaS=v.findViewById(R.id.lista_prese);
        final RadioGroup grR=v.findViewById(R.id.grpBoton);

        final Switch priori=v.findViewById(R.id.swPriori);
        final Switch estado=v.findViewById(R.id.swEstado);

        final RadioButton radio=v.findViewById(R.id.rdDetalle);

        titulo.setText(this.txtNomPro.getText());

        ArrayAdapter<entidad> adapter4=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lista_prese);
        listaS.setAdapter(adapter4);

        btn1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                presentaciones_productos pp= new presentaciones_productos("","");
                entidad pre=(entidad)listaS.getSelectedItem();
                if(pre.getValor().equals("0")){
                    Toast.makeText(getApplicationContext(),"Elija una presentación", Toast.LENGTH_SHORT).show();
                }else if(txtCantida.getText().toString().isEmpty()){
                    layoCan.setError("No dejes en blanco la cantidad");
                }else if(txtPrecio.getText().toString().isEmpty()){
                    layoCan.setErrorEnabled(false);
                    layouPre.setError("No dejes en blanco el precio");
                }else if (grR.getCheckedRadioButtonId()>0){
                    layouPre.setErrorEnabled(false);
                    pp.setIdpre(pre.getValor());
                    pp.setNombre(pre.toString());
                    pp.setCantida(txtCantida.getText().toString());
                    pp.setPrecio(txtPrecio.getText().toString());
                    if(radio.isChecked()){
                        pp.setTipo("2");
                    }else{
                        pp.setTipo("1");
                    }

                    Log.d("tipo", pp.getTipo());

                    if(priori.isChecked()){
                        pp.setPriori("1");
                    }else{
                        pp.setPriori("2");
                    }

                    if(estado.isChecked()){
                        pp.setEstado("1");
                    }else{
                        pp.setEstado("2");
                    }
                    pp.mostrardatos();
                    lista_pro_pre.add(pp);
                    adapta.notifyDataSetChanged();
                    dialog.dismiss();
                }else{
                    Toast.makeText(getApplicationContext(), "Elige un tipo de venta", Toast.LENGTH_LONG).show();
                }

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        return dialog;
    }

    public void escaner(View view){
        escanear();
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
                txtCodigo.setText(result.getContents());
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private boolean validar(){
        boolean valido=false;
        layouNom.setErrorEnabled(false);
        layouCodi.setErrorEnabled(false);
        layouExis.setErrorEnabled(false);

        if(txtCodigo.getText().toString().isEmpty()){
            layouCodi.setError("Ingresa un codigo");
            valido=true;
        }else if(txtNomPro.getText().toString().isEmpty()){
            layouNom.setError("Colocale un nombre la producto");
            valido=true;
        }else if(((entidad)lista_categorias.getSelectedItem()).getValor().equals("0")){
            Toast.makeText(this, "Elije una categoria", Toast.LENGTH_SHORT).show();
            valido=true;
        }else if(((entidad)lista_marcas.getSelectedItem()).getValor().equals("0")){
            Toast.makeText(this, "Elije una marca", Toast.LENGTH_SHORT).show();
            valido=true;
        }else if(((entidad)lista_estantes.getSelectedItem()).getValor().equals("0")){
            Toast.makeText(this, "Elije un estante", Toast.LENGTH_SHORT).show();
            valido=true;
        }else if(((entidad)lista_sucursales.getSelectedItem()).getValor().equals("0")){
            Toast.makeText(this, "Elije una sucursal", Toast.LENGTH_SHORT).show();
            valido=true;
        }else if(txtExis.getText().toString().isEmpty()){
            layouExis.setError("Ingresa una cantidad");
            valido=true;
        } else if(lista_pro_pre.size()==0){
            Toast.makeText(this, "Tienes que agregar una presentación", Toast.LENGTH_SHORT).show();
            valido=true;
        }

        return valido;
    }

    private void insertandoProducto() {
        conexiones_base cone=new conexiones_base(this);
        Map<String,String> valores=new HashMap<>();
        valores.put("entidad","productos");
        valores.put("opcion", "4");
        valores.put("id","");
        valores.put("codigo", txtCodigo.getText().toString());
        valores.put("estado","1");
        valores.put("nombre_pro", txtNomPro.getText().toString());
        valores.put("id_cate", ((entidad)lista_categorias.getSelectedItem()).getValor());
        valores.put("id_marca", ((entidad)lista_marcas.getSelectedItem()).getValor());
        valores.put("id_esta", ((entidad)lista_estantes.getSelectedItem()).getValor());
        valores.put("id_suc", ((entidad)lista_sucursales.getSelectedItem()).getValor());
        valores.put("exis", txtExis.getText().toString());
        if(estado.isChecked()){
            valores.put("kardex","1");
        }else{
            valores.put("kardex","2");
        }

        cone.insertar2(valores, lista_pro_pre);
        navigateUpTo(new Intent(this, lista_productos.class));
        this.finish();

    }

}
