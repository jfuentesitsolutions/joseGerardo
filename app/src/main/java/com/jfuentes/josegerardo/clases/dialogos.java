package com.jfuentes.josegerardo.clases;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.jfuentes.josegerardo.R;

public class dialogos {

    AlertDialog.Builder ventana;
    AlertDialog dialog;
    EditText nombre;
    EditText descrp;
    TextInputLayout ln,ld;
    Switch est;
    LayoutInflater inflador;
    View vista;
    Button btnModi;

    public dialogos(ViewGroup con, int layout) {
        this.ventana = new AlertDialog.Builder(con.getContext());
        inflador=(LayoutInflater)con.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        vista=inflador.inflate(layout, null);
        btnModi=vista.findViewById(R.id.btnModifica);
    }

    public dialogos(Context con){
        this.ventana=new AlertDialog.Builder(con);
        this.inflador=(LayoutInflater)con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.vista=inflador.inflate(R.layout.dialogo_entidad, null);
        btnModi=vista.findViewById(R.id.btnModifica);
        nombre=this.vista.findViewById(R.id.txtNombre_enti);
        descrp=this.vista.findViewById(R.id.txtDescipcion_enti);
        est=this.vista.findViewById(R.id.estado);
    }

    public View getVista() {
        return vista;
    }

    public void mostrar(){
        ventana.setView(vista);
        dialog=ventana.create();
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    public void cerrar(){
        dialog.dismiss();
    }

    public void colocarTitulo(String titu){
        TextView titulo=this.vista.findViewById(R.id.lblTitulos);
        titulo.setText(titu);
    }

    public void deshabilitarEstado(){
        est=this.vista.findViewById(R.id.estado);
        est.setVisibility(View.GONE);
    }

    public void colocarDatos(String n,String d, boolean estado, boolean presenta){
        nombre=this.vista.findViewById(R.id.txtNombre_enti);
        descrp=this.vista.findViewById(R.id.txtDescipcion_enti);
        if(!presenta){
            nombre.setText(n);
            descrp.setText(d);
        }else{
            est=this.vista.findViewById(R.id.estado);
            est.setChecked(estado);
            nombre.setText(n);
            descrp.setText(d);
        }
    }

    public boolean validar(){
        ln=this.vista.findViewById(R.id.layouen);
        ld=this.vista.findViewById(R.id.layoued);
        boolean valido=false;

        if(nombre.getText().toString().isEmpty()){
            ln.setError("Coloca un nombre");
            valido= true;
        }else {
            ln.setErrorEnabled(false);
        }

        if (descrp.getText().toString().isEmpty()){
            ld.setError("Coloca una descripción");
            valido= true;
        }else{
            ld.setErrorEnabled(false);
        }

        return valido;
    }

    public EditText getNombre() {
        return nombre;
    }

    public EditText getDescrp() {
        return descrp;
    }

    public Switch getEst() {
        return est;
    }

    public AlertDialog getDialog() {
        return dialog;
    }

    public Button getBtnModi() {
        return btnModi;
    }
}
