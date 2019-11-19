package com.jfuentes.josegerardo.clases.entidades;

public class categorias implements entity {

    private String descripcion, nombre, id;

    public categorias(String descripcion, String nombre, String id) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.id = id;
    }

    public categorias() {
    }

    public String toString(){
        return this.nombre;
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

    @Override
    public int compareTo(entity o) {
        return nombre.compareTo(o.nombre());
    }
}
