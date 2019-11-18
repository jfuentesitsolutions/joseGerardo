package com.jfuentes.josegerardo.clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.jfuentes.josegerardo.R;

public class mensaje_dialogo_by_jfuentes {

    Context context;
    AlertDialog dialogo;
    View vista;
    String titulo, mensaje;
    Button acepta, cancelar;
    LayoutInflater inflater;
    LinearLayout contenedor;
    int fondo;
    public Context getContext() {
        return context;
    }

    public Button getAcepta() {
        return acepta;
    }

    public Button getCancelar() {
        return cancelar;
    }

    public mensaje_dialogo_by_jfuentes(Context context, String titulo, String mensaje) {
        this.context = context;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.inflater=(LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        creandodialogo();
    }

    public mensaje_dialogo_by_jfuentes(Context context, String titulo, String mensaje, int fondo) {
        this.context = context;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.inflater=(LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        this.fondo=fondo;
        creandodialogo();
    }

    private void creandodialogo(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        vista=inflater.inflate(R.layout.mensaje_confirmacion, null);
        acepta=vista.findViewById(R.id.btnAceptarDialogo);
        cancelar=vista.findViewById(R.id.btnCancelarDialogo);
        contenedor=vista.findViewById(R.id.contenedor_mensaje);

        contenedor.setBackgroundResource(fondo);
        TextView titul=vista.findViewById(R.id.txtTituloDialogo);
        titul.setText(titulo);
        TextView mensaj=vista.findViewById(R.id.txtContenidoDialogo);
        mensaj.setText(mensaje);

        builder.setView(vista);
        dialogo=builder.create();
        dialogo.show();
    }

    public void cerrar(){
        dialogo.dismiss();
    }

}
