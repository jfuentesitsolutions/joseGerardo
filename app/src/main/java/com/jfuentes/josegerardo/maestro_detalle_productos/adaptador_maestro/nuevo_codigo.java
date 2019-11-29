package com.jfuentes.josegerardo.maestro_detalle_productos.adaptador_maestro;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jfuentes.josegerardo.Adaptador;
import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.clases.entidad;
import com.jfuentes.josegerardo.clases.entidades.codigos;
import com.jfuentes.josegerardo.clases.utilidades;
import com.jfuentes.josegerardo.conexiones_base;
import com.jfuentes.josegerardo.presentacion;
import com.jfuentes.josegerardo.productos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class nuevo_codigo extends AppCompatActivity {

    ArrayList<codigos> lista_C;
    ListView lista;
    productos pro;
    EditText txtCodigo;
    Adaptador_codigos ada;
    FloatingActionButton agrega;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_codigo);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            pro=(productos)bundle.getSerializable("producto");
        }

        agrega=findViewById(R.id.btnAgregaProducto);
        lista=findViewById(R.id.lista_codi);
        setTitle(pro.getNombre());
        conexiones_base cone = new conexiones_base(this);
        lista_C=cone.llenarCodigos(pro.getIdproducto(),lista);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ada=(Adaptador_codigos)parent.getAdapter();
                codigos codi=(codigos)ada.getItem(position);
                dialogoAgregarC(codi).show();
            }
        });

        agrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogoAgregar().show();
            }
        });

    }


    private AlertDialog dialogoAgregar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialogo_codigos, null);
        builder.setView(v);
        final AlertDialog dialog=builder.create();
        Button btn=v.findViewById(R.id.btnDcan);
        Button btn1=v.findViewById(R.id.btnDagre);
        TextView titulo=v.findViewById(R.id.lblTitulo);
        titulo.setText(pro.getNombre());
        txtCodigo=v.findViewById(R.id.txtCodigo_np);
        final Switch esta=v.findViewById(R.id.estadoo);
        final TextInputLayout layou_co=v.findViewById(R.id.layou_codigo);

        ImageView barra=v.findViewById(R.id.codigo_scaner);

        barra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escanear();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexiones_base cone=new conexiones_base(getApplicationContext());

                if(txtCodigo.getText().toString().isEmpty()){
                    layou_co.setError("Tienes que ingresar un codigo");
                }else{
                    String estad;
                    Map<String,String> valores=new HashMap<>();
                    valores.put("entidad", "codigos");
                    valores.put("opcion", utilidades.NUEVO_REGISTRO);
                    valores.put("codi", txtCodigo.getText().toString());
                    valores.put("idpro", pro.getIdproducto());
                    if(esta.isChecked()){
                        valores.put("estado", "1");
                        estad="Activo";
                    }else{
                        valores.put("estado", "2");
                        estad="Desactivo";
                    }
                    ada=(Adaptador_codigos) lista.getAdapter();
                    cone.insertar3(valores, ada, new codigos("", "", txtCodigo.getText().toString(),estad));
                    dialog.dismiss();
                }
            }
        });

        return dialog;
    }

    private AlertDialog dialogoAgregarC(final codigos codi){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialogo_codigos, null);
        builder.setView(v);
        final AlertDialog dialog=builder.create();
        Button btn=v.findViewById(R.id.btnDcan);
        Button btn1=v.findViewById(R.id.btnDagre);
        btn1.setText("Modificar");
        TextView titulo=v.findViewById(R.id.lblTitulo);
        txtCodigo=v.findViewById(R.id.txtCodigo_np);
        txtCodigo.setText(codi.getCodigo());
        txtCodigo.setEnabled(false);
        final Switch estado=v.findViewById(R.id.estadoo);
        final TextInputLayout layou_co=v.findViewById(R.id.layou_codigo);

        if(codi.getEstado().equals("Activo") || codi.getEstado().equals("1")){
            estado.setChecked(true);
        }

        titulo.setText(pro.getNombre());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtCodigo.getText().toString().isEmpty()){
                    layou_co.setError("No puedes dejar el codigo vacio");
                }else{
                    conexiones_base cone=new conexiones_base(getApplicationContext());
                    Map<String, String> valores=new HashMap<>();
                    valores.put("entidad", "codigos");
                    valores.put("opcion", utilidades.ACTUALIZAR_REGISTRO);
                    valores.put("id_pro_pre", codi.getIdco());
                    valores.put("codi", txtCodigo.getText().toString());
                    if(estado.isChecked()){
                        valores.put("estado", "1");
                        codi.setEstado("Activo");
                    }else{
                        valores.put("estado", "2");
                        codi.setEstado("Desactivo");
                    }
                    cone.insertar(valores);
                    codi.setCodigo(txtCodigo.getText().toString());
                    ada.notifyDataSetChanged();
                    dialog.dismiss();
                }
            }
        });


        return dialog;
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
}
