package com.jfuentes.josegerardo.clases.entidades;

public class sucursales implements entity {

    private String id, nombre;

    public sucursales(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String toString(){
        return nombre;
    }

    public sucursales() {
    }

    @Override
    public String nombre() {
        return this.nombre;
    }

    @Override
    public String descripcion() {
        return null;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public String estado() {
        return null;
    }

    @Override
    public int compareTo(entity entity) {
        return 0;
    }
}
