package com.jfuentes.josegerardo.maestro_detalle_productos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.jfuentes.josegerardo.Adaptador;
import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.clases.entidad;
import com.jfuentes.josegerardo.clases.entidades.presentaciones_productos;
import com.jfuentes.josegerardo.conexiones_base;
import com.jfuentes.josegerardo.presentacion;
import com.jfuentes.josegerardo.productos;
import com.jfuentes.josegerardo.volleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class nueva_presentacion extends AppCompatActivity {

    ListView lista;
    ArrayList<presentacion> lista_p= new ArrayList<presentacion>();
    RequestQueue res;
    String ipe, puertoe;
    Bundle bundle=null;
    productos pro=null;
    ArrayList<entidad> lista_prese;
    conexiones_base cone;
    Adaptador adap;
    FloatingActionButton agrega;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_presentacion);

        agrega=findViewById(R.id.btnAgregaProducto);
        bundle = getIntent().getExtras();

        if(bundle!=null){
            pro=(productos)bundle.getSerializable("producto");
            setTitle(pro.getNombre());
        }

        lista=findViewById(R.id.lista_pre);
        accediendoDatos();
        String url="http://"+ipe+":"+puertoe+"/servidor/presentaciones.php?pro="+pro.getId();
        conexion(url);

        cone=new conexiones_base(this);
        lista_prese=cone.llenarSpinerPre();
        lista_prese.add(new entidad("Elija una presentación","0",""));

        agrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogoAgregar().show();
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Adaptador ada= (Adaptador) parent.getAdapter();
                presentacion pre=(presentacion)ada.getItem(position);
                dialogoAgregarC(pre).show();
            }
        });

    }

    public void conexion(String URL) {
        res = volleySingleton.getInstance(this).getmRequesQueve();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject objeto=null;
                for (int i=0;i<response.length();i++){
                    try {
                        objeto= response.getJSONObject(i);

                        lista_p.add(new presentacion(objeto.getString("idpre"),
                                objeto.getString("cantidad"),
                                objeto.getString("precio"),
                                objeto.getString("tipo"),
                                objeto.getString("presentacion"),
                                objeto.getString("estado"),
                                objeto.getString("idpresentacion"),
                                objeto.getString("cantidad_unidades"),
                                objeto.getString("prioridad")));

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "No existen presentaciones", Toast.LENGTH_SHORT).show();
                    }
                }
                adap=new Adaptador(getApplicationContext(), lista_p);
                lista.setAdapter(adap);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "No hay presentaciones", Toast.LENGTH_LONG).show();
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


    private AlertDialog dialogoAgregarC(final presentacion pres){
        Log.d("prese_del_pro", pres.getIdpre());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.dialogo_presentacion_producto, null);
        builder.setView(v);
        final AlertDialog dialog=builder.create();
        Button btn=v.findViewById(R.id.btnDcan);
        Button btn1=v.findViewById(R.id.btnDagre);
        btn1.setText("Modificar");
        TextView titulo=v.findViewById(R.id.lblTitulo);

        final TextInputEditText txtCantida=v.findViewById(R.id.txtCantidaI);
        txtCantida.setText(pres.getCant_unida());
        final TextInputLayout layoCan=v.findViewById(R.id.layouCantiInterna);
        final TextInputEditText txtPrecio=v.findViewById(R.id.txtPrecio);
        txtPrecio.setText(pres.getPrecio());
        final TextInputLayout layouPre=v.findViewById(R.id.layouPrecio);

        final Spinner listaS=v.findViewById(R.id.lista_prese);
        final RadioGroup grR=v.findViewById(R.id.grpBoton);

        final Switch priori=v.findViewById(R.id.swPriori);
        final Switch estado=v.findViewById(R.id.swEstado);

        final RadioButton radio=v.findViewById(R.id.rdDetalle);
        final RadioButton radio1=v.findViewById(R.id.rdMayoreo);

        if(pres.getTipo().equals("Detalle") || pres.getTipo().equals("2")){
            radio.setChecked(true);
        }else{
            radio1.setChecked(true);
        }

        if(pres.getPriori().equals("1")){
            priori.setChecked(true);
        }

        if(pres.getEstado().equals("Activo") || pres.getEstado().equals("1")){
            estado.setChecked(true);
        }

        titulo.setText(this.pro.getNombre());

        ArrayAdapter<entidad> adapter4=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lista_prese);
        listaS.setAdapter(adapter4);
        listaS.setSelection(colocarDato(Integer.parseInt(pres.getIdpresentacion()), lista_prese));

        btn1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                presentaciones_productos pp= new presentaciones_productos(pres.getIdpre(),pro.getId());
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

                    conexiones_base cone=new conexiones_base(getApplicationContext());
                    Map<String, String> valores=new HashMap<>();
                    valores.put("entidad", "productos");
                    valores.put("opcion", "5");
                    valores.put("id",pres.getIdpre());
                    valores.put("idsuc_pro", pp.getIdsucursal_pro());
                    valores.put("idpre", pp.getIdpre());
                    valores.put("cant_uni", pp.getCantida());
                    valores.put("precio", pp.getPrecio());
                    valores.put("tipo", pp.getTipo());
                    valores.put("priori", pp.getPriori());
                    valores.put("estado_pre", pp.getEstado());

                    cone.insertar(valores);
                    pres.setPresentacion(pre.toString());
                    pres.setIdpresentacion(pre.getValor());
                    pres.setCant_unida(pp.getCantida());
                    pres.setCantidad((pp.getCantida()));
                    pres.setPrecio(pp.getPrecio());
                    if(pp.getTipo().equals("1")){
                        pres.setTipo("Mayoreo");
                    }else{
                        pres.setTipo("Detalle");
                    }

                    pres.setPriori(pp.getPriori());
                    pres.setEstado(pp.getEstado());
                    adap.notifyDataSetChanged();

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

        titulo.setText(this.pro.getNombre());

        ArrayAdapter<entidad> adapter4=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lista_prese);
        listaS.setAdapter(adapter4);

        btn1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                presentacion pp= new presentacion();
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
                    pp.setIdpresentacion(pre.getValor());
                    pp.setPresentacion(pre.toString());
                    pp.setCant_unida(txtCantida.getText().toString());
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

                    conexiones_base cone=new conexiones_base(getApplicationContext());
                    Map<String, String> valores=new HashMap<>();
                    valores.put("entidad", "productos");
                    valores.put("opcion", "1");
                    valores.put("idsuc_pro", pro.getId());
                    valores.put("idpre", pp.getIdpresentacion());
                    valores.put("cant_uni", pp.getCant_unida());
                    valores.put("precio", pp.getPrecio());
                    valores.put("tipo", pp.getTipo());
                    valores.put("priori", pp.getPriori());
                    valores.put("estado_pre", pp.getEstado());

                    cone.insertar(valores);
                    lista_p.add(pp);
                    adap.notifyDataSetChanged();


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
}
