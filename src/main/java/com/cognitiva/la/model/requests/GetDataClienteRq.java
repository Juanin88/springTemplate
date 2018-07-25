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
public class GetDataClienteRq {

    private String clave;
    private String tel;
    private String cuenta;

    public GetDataClienteRq(String clave, String tel, String cuenta) {
        this.clave = clave;
        this.tel = tel;
        this.cuenta = cuenta;
    }

    public GetDataClienteRq() {
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public String toString() {
        return "GetDataClienteRq{" + "clave=" + clave + ", tel=" + tel + ", cuenta=" + cuenta + '}';
    }

}
