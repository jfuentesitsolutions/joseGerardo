package com.jfuentes.josegerardo;

public class productos {
    private String nombre, codigo, categoria, marca, estante, id, existencias, idproducto, idmarca, idcateg, idestante;

    public String getIdmarca() {
        return idmarca;
    }

    public String getIdcateg() {
        return idcateg;
    }

    public String getIdestante() {
        return idestante;
    }

    public String getIdproducto() {
        return idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getExistencias() {
        return existencias;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getMarca() {
        return marca;
    }

    public String getEstante() {
        return estante;
    }

    public String getId() {
        return id;
    }

    public productos(String nombre, String codigo, String categoria, String marca, String estante, String id, String existencias, String idproducto, String idmarca, String idcateg, String idestante) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.categoria = categoria;
        this.marca = marca;
        this.estante = estante;
        this.id = id;
        this.existencias = existencias;
        this.idproducto = idproducto;
        this.idmarca = idmarca;
        this.idcateg = idcateg;
        this.idestante = idestante;
    }
}
