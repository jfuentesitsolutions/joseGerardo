package com.jfuentes.josegerardo.maestro_detalle_productos.adaptador_maestro;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jfuentes.josegerardo.R;
import com.jfuentes.josegerardo.singleton;

public class ViewHolder extends RecyclerView.ViewHolder {
    final TextView nom;
    final TextView cod;
    final TextView estante;

    /*Context contexto;
    CardView tar;
    singleton sesion=singleton.getInstance();
    String nombre, codigo, estan, cate, marca, exis, id, idpro, idcate, idmarc, idesta;*/

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        //contexto=itemView.getContext();
        nom=itemView.findViewById(R.id.txtNombrePro);
        cod=itemView.findViewById(R.id.txtCodigoo);
        estante=itemView.findViewById(R.id.txtEstantee);
       // tar=itemView.findViewById(R.id.tarjeta);
    }

    public void asigandoDato(String da, String co, String es, String c, String m, String e ,String i, String id_p, String idcat, String idmar, String ides){
        nom.setText(da);
        cod.setText(co);
        estante.setText(es);
       /* nombre=da;
        codigo=co;
        estan=es;
        cate=c;
        marca=m;
        id=i;
        exis=e;
        idpro=id_p;
        idcate=idcat;
        idmarc=idmar;
        idesta=ides;*/
    }
}
