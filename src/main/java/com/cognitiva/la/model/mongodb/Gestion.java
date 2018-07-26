/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.model.mongodb;

import java.util.List;

/**
 *
 * @author jgarfias2
 */
public class Gestion {

    private String id;
    private String claveGestion;
    private String nombre;
    private String descripcion;
    private String tipo;
    private List<GestionCodigoResultado> gestionCodigoResultados;

    public Gestion() {
    }

    public Gestion(String id, String claveGestion, String nombre, String descripcion, String tipo, List<GestionCodigoResultado> gestionCodigoResultados) {
        this.id = id;
        this.claveGestion = claveGestion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.gestionCodigoResultados = gestionCodigoResultados;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClaveGestion() {
        return claveGestion;
    }

    public void setClaveGestion(String claveGestion) {
        this.claveGestion = claveGestion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<GestionCodigoResultado> getGestionCodigoResultados() {
        return gestionCodigoResultados;
    }

    public void setGestionCodigoResultados(List<GestionCodigoResultado> gestionCodigoResultados) {
        this.gestionCodigoResultados = gestionCodigoResultados;
    }

    @Override
    public String toString() {
        return "Gestion{" + "id=" + id + ", claveGestion=" + claveGestion + ", nombre=" + nombre + ", descripcion=" + descripcion + ", tipo=" + tipo + ", gestionCodigoResultados=" + gestionCodigoResultados + '}';
    }

}
