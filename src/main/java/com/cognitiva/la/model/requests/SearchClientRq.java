/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.model.requests;

/**
 *
 * @author jgarfias
 */
public class SearchClientRq {
    private String clave;

    public SearchClientRq() {
    }

    public SearchClientRq(String clave) {
        this.clave = clave;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return "SearchClientRq{" + "clave=" + clave + '}';
    }
    
}
