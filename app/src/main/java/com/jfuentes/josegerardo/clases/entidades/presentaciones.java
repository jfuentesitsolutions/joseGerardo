package com.jfuentes.josegerardo.clases.entidades;

public class presentaciones implements entity {

    private String id, nombre, descripcion, estado;

    public presentaciones(String id, String nombre, String descripcion, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public presentaciones() {
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
        return estado;
    }

    @Override
    public int compareTo(entity o) {
        return nombre.compareTo(o.nombre());
    }
}
