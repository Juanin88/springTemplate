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
public class CampaignsInitialMessages {
    
    private String id;
    private String descripcion;
    private String nombreMsg;
    private List<Mensaje> mensajes;

    public CampaignsInitialMessages() {
    }

    public CampaignsInitialMessages(String id, String descripcion, String nombreMsg, List<Mensaje> mensajes) {
        this.id = id;
        this.descripcion = descripcion;
        this.nombreMsg = nombreMsg;
        this.mensajes = mensajes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreMsg() {
        return nombreMsg;
    }

    public void setNombreMsg(String nombreMsg) {
        this.nombreMsg = nombreMsg;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    @Override
    public String toString() {
        return "CampaignsInitialMessages{" + "id=" + id + ", descripcion=" + descripcion + ", nombreMsg=" + nombreMsg + ", mensajes=" + mensajes + '}';
    }

}
