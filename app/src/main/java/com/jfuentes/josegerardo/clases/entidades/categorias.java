package com.jfuentes.josegerardo.clases.entidades;

public class categorias implements entity {

    String descripcion, nombre, id;

    public categorias(String descripcion, String nombre, String id) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.id = id;
    }

    public categorias() {
    }

    @Override
    public String nombre() {
        return nombre;
    }

    @Override
    public String descripcion() {
        return descripcion;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String estado() {
        return null;
    }
}
