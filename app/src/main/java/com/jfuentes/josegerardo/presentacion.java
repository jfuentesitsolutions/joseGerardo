package com.jfuentes.josegerardo;

import android.os.Parcel;
import android.os.Parcelable;

public class presentacion implements Parcelable {

    private String idpre;
    private String cantidad;
    private String precio;
    private String tipo;
    private String presentacion;
    private String estado, idpresentacion, cant_unida, priori;

    public void setIdpre(String idpre) {
        this.idpre = idpre;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setIdpresentacion(String idpresentacion) {
        this.idpresentacion = idpresentacion;
    }

    public void setCant_unida(String cant_unida) {
        this.cant_unida = cant_unida;
    }

    public void setPriori(String priori) {
        this.priori = priori;
    }

    public int getImagen() {
        return imagen;
    }

    private int imagen;

    public String getEstado() {
        return estado;
    }

    public String getIdpresentacion() {
        return idpresentacion;
    }

    public String getCant_unida() {
        return cant_unida;
    }

    public String getPriori() {
        return priori;
    }

    public presentacion() {
    }

    public presentacion(String idpre, String cantidad, String precio, String tipo, String presentacion) {
        this.idpre = idpre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.tipo = tipo;
        this.presentacion = presentacion;
    }

    public presentacion(String idpre, String cantidad, String precio, String tipo, String presentacion, String estado, String idpresentacion, String cant_unida, String priori) {
        this.idpre = idpre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.tipo = tipo;
        this.presentacion = presentacion;
        this.estado = estado;
        this.idpresentacion = idpresentacion;
        this.cant_unida = cant_unida;
        this.priori = priori;
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
