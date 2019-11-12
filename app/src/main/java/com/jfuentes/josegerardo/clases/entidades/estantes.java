package com.jfuentes.josegerardo.clases.entidades;

import java.io.Serializable;

public class estantes implements entity {
    private String id, nombre, descripcion;

    public estantes(String id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public estantes() {
    }

    @Override
    public String nombre() {
        return this.nombre;
    }

    @Override
    public String descripcion() {
        return this.descripcion;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public String estado() {
        return null;
    }
}
