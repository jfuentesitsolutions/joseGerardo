package com.jfuentes.josegerardo;

import java.util.ArrayList;

public final class singleton {

    private String producto="";
    private String existencia="";
    private String categoria="";
    private String marca="";
    private String estante="";
    private String ip="";
    private String id_producto="";
    private String idcategoria="";
    private String id_marca="";
    private String id_estante="";
    private ArrayList<presentacion> dato;
    private String Usuario="";
    private String[][] data;
    Boolean actualiza=false;

    public String getIdcategoria() {
        return idcategoria;
    }
    public void setIdcategoria(String idcategoria) {
        this.idcategoria = idcategoria;
    }
    public String getId_marca() {
        return id_marca;
    }
    public void setId_marca(String id_marca) {
        this.id_marca = id_marca;
    }
    public String getId_estante() {
        return id_estante;
    }
    public void setId_estante(String id_estante) {
        this.id_estante = id_estante;
    }
    public String[][] getData() {
        return data;
    }
    public void setData(String[][] data) {
        this.data = data;
    }
    public String getProducto() {
        return producto;
    }
    public void setProducto(String producto) {
        this.producto = producto;
    }
    public String getExistencia() {
        return existencia;
    }
    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }
    public ArrayList<presentacion> getDato() {
        return dato;
    }
    public void setDato(ArrayList<presentacion> dato) {
        this.dato = dato;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getPuerto() {
        return puerto;
    }
    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }
    private String puerto="";
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getEstante() {
        return estante;
    }
    public void setEstante(String estante) {
        this.estante = estante;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    private String codigo="";
    private String id="";
    public void setUsuario(String u){
        this.Usuario=u;
    }
    public String getUsuario(){
        return Usuario;
    }
    public String getId_producto() {
        return id_producto;
    }
    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }
    public Boolean getActualiza() {
        return actualiza;
    }
    public void setActualiza(Boolean actualiza) {
        this.actualiza = actualiza;
    }

    private static singleton INSTANCE;

    private singleton(){};

    public synchronized static void creaInstancia(){
        if(INSTANCE==null){
            INSTANCE = new singleton();
        }
    }

    public static singleton getInstance(){
        creaInstancia();
        return INSTANCE;
    }

}

