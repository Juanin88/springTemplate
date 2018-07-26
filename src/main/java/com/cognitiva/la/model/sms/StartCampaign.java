/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.model.sms;

/**
 *
 * @author jgarfias
 */
public class StartCampaign {

    private String clave;
    private String tel;
    private String noCuenta;
    private String fechaLimitePagoCampaign;
    private String claveCliente;
    private String idCampaignsInitialMessages;

    public StartCampaign(String clave, String tel, String noCuenta, String fechaLimitePagoCampaign, String claveCliente, String idCampaignsInitialMessages)  {
        this.clave = clave;
        this.tel = tel;
        this.noCuenta = noCuenta;
        this.fechaLimitePagoCampaign = fechaLimitePagoCampaign;
        this.claveCliente = claveCliente;
        this.idCampaignsInitialMessages = idCampaignsInitialMessages;
    }

    public StartCampaign() {
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

    public String getNoCuenta() {
        return noCuenta;
    }

    public void setNoCuenta(String noCuenta) {
        this.noCuenta = noCuenta;
    }

    public String getFechaLimitePagoCampaign() {
        return fechaLimitePagoCampaign;
    }

    public void setFechaLimitePagoCampaign(String fechaLimitePagoCampaign) {
        this.fechaLimitePagoCampaign = fechaLimitePagoCampaign;
    }

    public String getClaveCliente() {
        return claveCliente;
    }

    public void setClaveCliente(String claveCliente) {
        this.claveCliente = claveCliente;
    }

    public String getIdCampaignsInitialMessages() {
        return idCampaignsInitialMessages;
    }

    public void setIdCampaignsInitialMessages(String idCampaignsInitialMessages) {
        this.idCampaignsInitialMessages = idCampaignsInitialMessages;
    }

    @Override
    public String toString() {
        return "StartCampaign{" + "clave=" + clave + ", tel=" + tel + ", noCuenta=" + noCuenta + ", fechaLimitePagoCampaign=" + fechaLimitePagoCampaign + ", claveCliente=" + claveCliente + ", idCampaignsInitialMessages=" + idCampaignsInitialMessages + '}';
    }
    
}
