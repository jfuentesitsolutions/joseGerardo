package com.jfuentes.josegerardo;

import android.os.Parcel;
import android.os.Parcelable;

public class presentacion implements Parcelable {

    private String idpre;
    private String cantidad;
    private String precio;
    private String tipo;
    private String presentacion;

    public int getImagen() {
        return imagen;
    }

    private int imagen;

    public presentacion(String idpre, String cantidad, String precio, String tipo, String presentacion) {
        this.idpre = idpre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.tipo = tipo;
        this.presentacion = presentacion;
    }

    public presentacion(String idpre, String cantidad, String precio, String tipo, String presentacion, int img) {
        this.idpre = idpre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.tipo = tipo;
        this.presentacion = presentacion;
        this.imagen=img;
    }

    public String getIdpre() {
        return idpre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public String getTipo() {
        return tipo;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public String toString(){
        return presentacion;
    }


    protected presentacion(Parcel in) {
        idpre = in.readString();
        cantidad = in.readString();
        precio = in.readString();
        tipo = in.readString();
        presentacion = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idpre);
        dest.writeString(cantidad);
        dest.writeString(precio);
        dest.writeString(tipo);
        dest.writeString(presentacion);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<presentacion> CREATOR = new Parcelable.Creator<presentacion>() {
        @Override
        public presentacion createFromParcel(Parcel in) {
            return new presentacion(in);
        }

        @Override
        public presentacion[] newArray(int size) {
            return new presentacion[size];
        }
    };
}
