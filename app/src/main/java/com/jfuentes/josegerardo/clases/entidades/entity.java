package com.jfuentes.josegerardo.clases.entidades;

import java.io.Serializable;

public interface entity extends Serializable,Comparable<entity> {
    String nombre();
    String descripcion();
    String id();
    String estado();
}
