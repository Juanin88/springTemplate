/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.model.responses;

import com.cognitiva.la.model.mongodb.Cliente;
import com.owlike.genson.annotation.JsonDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author jgarfias
 */
public class GetDataClienteRs {
    
    private String Clave;
    
    private Date fechaCreacion;
    
    private Date fechaLimitePagoCamapign;
    
    private Cliente cliente;

    public GetDataClienteRs(String Clave, Date fechaCreacion, Date fechaLimitePagoCamapign, Cliente cliente) {
        this.Clave = Clave;
        
        this.fechaCreacion = fechaCreacion;
        this.fechaLimitePagoCamapign = fechaLimitePagoCamapign;
        this.cliente = cliente;
    }

    public GetDataClienteRs() {
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String Clave) {
        this.Clave = Clave;
    }

    @JsonDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @JsonDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    public Date getFechaLimitePagoCampaign() {
        return fechaLimitePagoCamapign;
    }

    public void setFechaLimitePagoCampaign(Date fechaLimitePagoCamapign) {
        this.fechaLimitePagoCamapign = fechaLimitePagoCamapign;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente( Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "GetDataClienteRs{" + "Clave=" + Clave + ", fechaCreacion=" + fechaCreacion + ", fechaLimitePagoCamapign=" + fechaLimitePagoCamapign + ", cliente=" + cliente + '}';
    }
    
}
