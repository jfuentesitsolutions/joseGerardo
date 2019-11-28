package com.jfuentes.josegerardo.clases.entidades;

import android.util.Log;

public class presentaciones_productos implements entity {

    private String id, idsucursal_pro, idpre, cantida, precio, tipo, priori, estado, nombre;

    public presentaciones_productos(String id, String idsucursal_pro) {
        this.id = id;
        this.idsucursal_pro = idsucursal_pro;
    }

    public String getId() {
        return id;
    }

    public String getIdsucursal_pro() {
        return idsucursal_pro;
    }

    public void setIdpre(String idpre) {
        this.idpre = idpre;
    }

    public void setCantida(String cantida) {
        this.cantida = cantida;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPriori(String priori) {
        this.priori = priori;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String nombre() {
        return this.nombre;
    }

    @Override
    public String descripcion() {
        return this.cantida;
    }

    @Override
    public String id() {
        return this.precio;
    }

    @Override
    public String estado() {
        return null;
    }

    @Override
    public int compareTo(entity o) {
        return 0;
    }

    public void mostrardatos(){
        Log.d("pruebas", idpre+" "+cantida+" "+precio+" "+tipo+" "+priori+" "+estado+" "+nombre);
    }

    public String getCantida() {
        return cantida;
    }

    public String getPrecio() {
        return precio;
    }

    public String getIdpre() {
        return idpre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getPriori() {
        return priori;
    }

    public String getEstado() {
        return estado;
    }

    public String getNombre() {
        return nombre;
    }
}
