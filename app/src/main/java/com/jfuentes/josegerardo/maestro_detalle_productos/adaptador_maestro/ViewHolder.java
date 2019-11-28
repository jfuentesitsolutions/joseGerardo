package com.jfuentes.josegerardo.maestro_detalle_productos.adaptador_maestro;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.jfuentes.josegerardo.R;


public class ViewHolder extends RecyclerView.ViewHolder {
    final TextView nom;
    final TextView cod;
    final TextView estante;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        nom=itemView.findViewById(R.id.txtNombrePro);
        cod=itemView.findViewById(R.id.txtCodigoo);
        estante=itemView.findViewById(R.id.txtEstantee);
    }

}
