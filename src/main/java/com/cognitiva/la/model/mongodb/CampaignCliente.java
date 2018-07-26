/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.model.mongodb;

import java.util.Date;

/**
 *
 * @author jgarfias
 */
public class CampaignCliente {

    private String claveCliente;
    private String noCuenta;
    private String nombreCliente;
    private String telSms;
    private boolean status = true;
    private Date fechaActividad;
    private Date fechaCreaPromesa;
    private Date fechaVencePromesa;
    private double montoPromesa;
    private Cliente cliente;
    private String codResultado;

    public CampaignCliente() {
    }

    public CampaignCliente(String claveCliente, String noCuenta, String nombreCliente, String telSms, Date fechaActividad, Date fechaCreaPromesa, Date fechaVencePromesa, double montoPromesa, Cliente cliente, String codResultado) {
        this.claveCliente = claveCliente;
        this.noCuenta = noCuenta;
        this.nombreCliente = nombreCliente;
        this.telSms = telSms;
        this.fechaActividad = fechaActividad;
        this.fechaCreaPromesa = fechaCreaPromesa;
        this.fechaVencePromesa = fechaVencePromesa;
        this.montoPromesa = montoPromesa;
        this.cliente = cliente;
        this.codResultado = codResultado;
    }

    public String getClaveCliente() {
        return claveCliente;
    }

    public void setClaveCliente(String claveCliente) {
        this.claveCliente = claveCliente;
    }

    public String getNoCuenta() {
        return noCuenta;
    }

    public void setNoCuenta(String noCuenta) {
        this.noCuenta = noCuenta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getTelSms() {
        return telSms;
    }

    public void setTelSms(String telSms) {
        this.telSms = telSms;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(Date fechaActividad) {
        this.fechaActividad = fechaActividad;
    }

    public Date getFechaCreaPromesa() {
        return fechaCreaPromesa;
    }

    public void setFechaCreaPromesa(Date fechaCreaPromesa) {
        this.fechaCreaPromesa = fechaCreaPromesa;
    }

    public Date getFechaVencePromesa() {
        return fechaVencePromesa;
    }

    public void setFechaVencePromesa(Date fechaVencePromesa) {
        this.fechaVencePromesa = fechaVencePromesa;
    }

    public double getMontoPromesa() {
        return montoPromesa;
    }

    public void setMontoPromesa(double montoPromesa) {
        this.montoPromesa = montoPromesa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getCodResultado() {
        return codResultado;
    }

    public void setCodResultado(String codResultado) {
        this.codResultado = codResultado;
    }

    @Override
    public String toString() {
        return "CampaignCliente{" + "claveCliente=" + claveCliente + ", noCuenta=" + noCuenta + ", nombreCliente=" + nombreCliente + ", telSms=" + telSms + ", status=" + status + ", fechaActividad=" + fechaActividad + ", fechaCreaPromesa=" + fechaCreaPromesa + ", fechaVencePromesa=" + fechaVencePromesa + ", montoPromesa=" + montoPromesa + ", cliente=" + cliente + ", codResultado=" + codResultado + '}';
    }

}
