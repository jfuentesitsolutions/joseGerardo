package com.jfuentes.josegerardo;

import java.io.Serializable;

public class productos implements Serializable {
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setEstante(String estante) {
        this.estante = estante;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setExistencias(String existencias) {
        this.existencias = existencias;
    }

    public void setIdproducto(String idproducto) {
        this.idproducto = idproducto;
    }

    public void setIdmarca(String idmarca) {
        this.idmarca = idmarca;
    }

    public void setIdcateg(String idcateg) {
        this.idcateg = idcateg;
    }

    public void setIdestante(String idestante) {
        this.idestante = idestante;
    }

    public productos(String nombre,
                     String codigo,
                     String categoria,
                     String marca,
                     String estante,
                     String id,
                     String existencias,
                     String idproducto,
                     String idmarca,
                     String idcateg,
                     String idestante) {
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
