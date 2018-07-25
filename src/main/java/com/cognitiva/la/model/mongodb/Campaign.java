package com.cognitiva.la.model.mongodb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;
import java.util.List;

/**
 *
 * @author jgarfias
 */
public class Campaign {
    private String clave;
    private String nombre;
    private String descripcion;
    private Date fechaCreacion; 
    private Date fechaLimitePagoCampaign;
    private String producto;
    private List<CampaignCliente> campaignClientes;
    private String tel;
    private String idCampaignsInitialMessages;
    private String claveGestion;

    public Campaign() {
    }

    public Campaign(String clave, String nombre, String descripcion, Date fechaCreacion, Date fechaLimitePagoCampaign, String producto, List<CampaignCliente> campaignClientes, String tel, String idCampaignsInitialMessages, String claveGestion) {
        this.clave = clave;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaLimitePagoCampaign = fechaLimitePagoCampaign;
        this.producto = producto;
        this.campaignClientes = campaignClientes;
        this.tel = tel;
        this.idCampaignsInitialMessages = idCampaignsInitialMessages;
        this.claveGestion = claveGestion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaLimitePagoCampaign() {
        return fechaLimitePagoCampaign;
    }

    public void setFechaLimitePagoCampaign(Date fechaLimitePagoCampaign) {
        this.fechaLimitePagoCampaign = fechaLimitePagoCampaign;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public List<CampaignCliente> getCampaignClientes() {
        return campaignClientes;
    }

    public void setCampaignClientes(List<CampaignCliente> campaignClientes) {
        this.campaignClientes = campaignClientes;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIdCampaignsInitialMessages() {
        return idCampaignsInitialMessages;
    }

    public void setIdCampaignsInitialMessages(String idCampaignsInitialMessages) {
        this.idCampaignsInitialMessages = idCampaignsInitialMessages;
    }

    public String getClaveGestion() {
        return claveGestion;
    }

    public void setClaveGestion(String claveGestion) {
        this.claveGestion = claveGestion;
    }

    @Override
    public String toString() {
        return "Campaign{" + "clave=" + clave + ", nombre=" + nombre + ", descripcion=" + descripcion + ", fechaCreacion=" + fechaCreacion + ", fechaLimitePagoCampaign=" + fechaLimitePagoCampaign + ", producto=" + producto + ", campaignClientes=" + campaignClientes + ", tel=" + tel + ", idCampaignsInitialMessages=" + idCampaignsInitialMessages + ", claveGestion=" + claveGestion + '}';
    }
    
}