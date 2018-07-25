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
public class GestionPerfil {
    private String clavePerfil;
    private String claveGestion;
    private List<GestionCodigoResultado> gestionCodigoResultados;

    public GestionPerfil() {
    }

    public GestionPerfil(String clavePerfil, String claveGestion, List<GestionCodigoResultado> gestionCodigoResultados) {
        this.clavePerfil = clavePerfil;
        this.claveGestion = claveGestion;
        this.gestionCodigoResultados = gestionCodigoResultados;
    }

    public String getClavePerfil() {
        return clavePerfil;
    }

    public void setClavePerfil(String clavePerfil) {
        this.clavePerfil = clavePerfil;
    }

    public String getClaveGestion() {
        return claveGestion;
    }

    public void setClaveGestion(String claveGestion) {
        this.claveGestion = claveGestion;
    }

    public List<GestionCodigoResultado> getGestionCodigoResultados() {
        return gestionCodigoResultados;
    }

    public void setGestionCodigoResultados(List<GestionCodigoResultado> gestionCodigoResultados) {
        this.gestionCodigoResultados = gestionCodigoResultados;
    }

    @Override
    public String toString() {
        return "GestionPerfil{" + "clavePerfil=" + clavePerfil + ", claveGestion=" + claveGestion + ", gestionCodigoResultados=" + gestionCodigoResultados + '}';
    }
    
}