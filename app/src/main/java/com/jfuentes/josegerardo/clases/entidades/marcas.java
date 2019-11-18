package com.jfuentes.josegerardo.clases.entidades;

public class marcas implements entity {

    private String descripcion,nombre,id;

    public marcas(String id, String nombre, String descrpcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descrpcion;
    }

    public marcas() {
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
