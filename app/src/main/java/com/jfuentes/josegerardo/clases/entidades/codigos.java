package com.jfuentes.josegerardo.clases.entidades;

public class codigos {

    private String id_pro_co, idco, codigo, estado, id_pro;

    public codigos() {
    }

    public codigos(String id_pro_co, String idco, String codigo, String estado) {
        this.id_pro_co = id_pro_co;
        this.idco = idco;
        this.codigo = codigo;
        this.estado = estado;
    }

    public String getId_pro_co() {
        return id_pro_co;
    }

    public void setId_pro_co(String id_pro_co) {
        this.id_pro_co = id_pro_co;
    }

    public String getIdco() {
        return idco;
    }

    public void setIdco(String idco) {
        this.idco = idco;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
