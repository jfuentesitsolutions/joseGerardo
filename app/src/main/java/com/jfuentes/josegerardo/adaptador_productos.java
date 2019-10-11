package com.jfuentes.josegerardo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class adaptador_productos extends RecyclerView.Adapter<adaptador_productos.MyViewHolder> {

    ArrayList<productos> listaP;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nom, cod,estante;
        ImageButton btnDetalles;
        Context contexto;
        singleton sesion=singleton.getInstance();
        String nombre, codigo, estan, cate, marca, exis, id, idpro, idcate, idmarc, idesta;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contexto=itemView.getContext();
            nom=(TextView)itemView.findViewById(R.id.txtNombrePro);
            cod=(TextView)itemView.findViewById(R.id.txtCodigoo);
            estante=(TextView)itemView.findViewById(R.id.txtEstantee);
            btnDetalles=(ImageButton) itemView.findViewById(R.id.btnVerDetalles);

        }

        public void asigandoDato(String da, String co, String es, String c, String m, String e ,String i, String id_p, String idcat, String idmar, String ides){
            nom.setText(da);
            cod.setText(co);
            estante.setText(es);
            nombre=da;
            codigo=co;
            estan=es;
            cate=c;
            marca=m;
            id=i;
            exis=e;
            idpro=id_p;
            idcate=idcat;
            idmarc=idmar;
            idesta=ides;
        }

        void setOnClickListeners(){
            btnDetalles.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent= new Intent(contexto,info_producto.class);
            sesion.setProducto(nombre);
            sesion.setCodigo(codigo);
            sesion.setEstante(estan);
            sesion.setCategoria(cate);
            sesion.setExistencia(exis);
            sesion.setMarca(marca);
            sesion.setId(id);
            sesion.setId_producto(idpro);
            sesion.setIdcategoria(idcate);
            sesion.setId_marca(idmarc);
            sesion.setId_estante(idesta);
            contexto.startActivity(intent);
        }


    }


    public adaptador_productos(ArrayList<productos> listaP) {
        this.listaP = listaP;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_producto, parent, false);
        MyViewHolder vh= new MyViewHolder(vista);
        return  vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.asigandoDato(listaP.get(position).getNombre(),
                listaP.get(position).getCodigo(),
                listaP.get(position).getEstante(),
                listaP.get(position).getCategoria(),
                listaP.get(position).getMarca(),
                listaP.get(position).getExistencias(),
                listaP.get(position).getId(),
                listaP.get(position).getIdproducto(),
                listaP.get(position).getIdcateg(),
                listaP.get(position).getIdmarca(),
                listaP.get(position).getIdestante());

        holder.setOnClickListeners();

    }

    @Override
    public int getItemCount() {
        return listaP.size();
    }

    public void setFiltro(ArrayList<productos> lista){
        this.listaP=new ArrayList<>();
        this.listaP.addAll(lista);
        notifyDataSetChanged();
    }
}
