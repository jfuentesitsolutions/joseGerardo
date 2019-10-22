package com.jfuentes.josegerardo.clases;

public class entidad {
    String nombre, valor, descripcion;

    public int getImagen() {
        return imagen;
    }

    int imagen;

    public entidad(String nombre, String valor, String descripcion) {
        this.nombre = nombre;
        this.valor = valor;
        this.descripcion = descripcion;
    }

    public entidad(String nombre, String valor, String descripcion, int imagen) {
        this.nombre = nombre;
        this.valor = valor;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public String toString() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
