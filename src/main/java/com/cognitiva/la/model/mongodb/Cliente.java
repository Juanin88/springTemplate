package com.cognitiva.la.model.mongodb;

import java.util.List;

public class Cliente {

    private String claveCliente;
    private String rfc;
    private String nombreCliente;
    private String correo;
    private String calleNoCliente;
    private String coloniaCliente;
    private String ciudadCliente;
    private String estado;
    private String codPostal;
    private List<ClienteCuenta> clienteCuentas;
    private String telSms;
    private String claveCampaign;

    public Cliente(String claveCliente, String rfc, String nombreCliente, String correo, String calleNoCliente, String coloniaCliente, String ciudadCliente, String estado, String codPostal, List<ClienteCuenta> clienteCuentas, String telSms, String claveCampaign) {
        this.claveCliente = claveCliente;
        this.rfc = rfc;
        this.nombreCliente = nombreCliente;
        this.correo = correo;
        this.calleNoCliente = calleNoCliente;
        this.coloniaCliente = coloniaCliente;
        this.ciudadCliente = ciudadCliente;
        this.estado = estado;
        this.codPostal = codPostal;
        this.clienteCuentas = clienteCuentas;
        this.telSms = telSms;
        this.claveCampaign = claveCampaign;
    }

    public Cliente() {
    }

    public String getClaveCliente() {
        return claveCliente;
    }

    public void setClaveCliente(String claveCliente) {
        this.claveCliente = claveCliente;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCalleNoCliente() {
        return calleNoCliente;
    }

    public void setCalleNoCliente(String calleNoCliente) {
        this.calleNoCliente = calleNoCliente;
    }

    public String getColoniaCliente() {
        return coloniaCliente;
    }

    public void setColoniaCliente(String coloniaCliente) {
        this.coloniaCliente = coloniaCliente;
    }

    public String getCiudadCliente() {
        return ciudadCliente;
    }

    public void setCiudadCliente(String ciudadCliente) {
        this.ciudadCliente = ciudadCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal;
    }

    public List<ClienteCuenta> getClienteCuentas() {
        return clienteCuentas;
    }

    public void setClienteCuentas(List<ClienteCuenta> clienteCuentas) {
        this.clienteCuentas = clienteCuentas;
    }

    public String getTelSms() {
        return telSms;
    }

    public void setTelSms(String telSms) {
        this.telSms = telSms;
    }

    public String getClaveCampaign() {
        return claveCampaign;
    }

    public void setClaveCampaign(String claveCampaign) {
        this.claveCampaign = claveCampaign;
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append("Cliente{");
        toString.append("claveCliente=");
        toString.append(claveCliente);
        toString.append(", rfc=");
        toString.append(rfc);
        toString.append(", nombreCliente=");
        toString.append(nombreCliente);
        toString.append("correo=");
        toString.append(correo);
        toString.append(", calleNoCliente=");
        toString.append(calleNoCliente);
        toString.append(", coloniaCliente=");
        toString.append(coloniaCliente);
        toString.append(", ciudadCliente=");
        toString.append(ciudadCliente);
        toString.append( ", estado=");
        toString.append(estado);
        toString.append(", codPostal=");
        toString.append(codPostal);
        toString.append(", telSms=");
        toString.append(telSms);
        toString.append(", claveCampaign=" );
        toString.append(claveCampaign);
        toString.append(", clienteCuentas=[");
        for (ClienteCuenta clienteCuenta : clienteCuentas) {
            toString.append(clienteCuenta.toString());
        }
        toString.append("]");
        toString.append("}");
        return toString.toString();
    }
    
}