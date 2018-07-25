/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.model.mongodb;

/**
 *
 * @author jgarfias2
 */
public class GestionCodigoResultado {
    private String codigo;
    private String descripcion;

    public GestionCodigoResultado() {
    }

    public GestionCodigoResultado(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "GestionCodigoResultado{" + "codigo=" + codigo + ", descripcion=" + descripcion + '}';
    }
    
}