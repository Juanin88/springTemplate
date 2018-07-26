/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.model.sms;
import org.springframework.web.multipart.MultipartFile;
/**
 *
 * @author jgarfias
 */
 
public class FileBucket {
 
    MultipartFile file;
    private String fechaLimitePagoCampaign;
    private String Clave;
    private String nombre;
    private String descripcion;
    private String producto;
    private String formAction;
    private String claveCampaign;
    private String id;

    public FileBucket() {
    }

    public FileBucket(MultipartFile file, String fechaLimitePagoCampaign, String Clave, String nombre, String descripcion, String producto, String formAction, String claveCampaign, String id) {
        this.file = file;
        this.fechaLimitePagoCampaign = fechaLimitePagoCampaign;
        this.Clave = Clave;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.producto = producto;
        this.formAction = formAction;
        this.claveCampaign = claveCampaign;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getFechaLimitePagoCampaign() {
        return fechaLimitePagoCampaign;
    }

    public void setFechaLimitePagoCampaign(String fechaLimitePagoCampaign) {
        this.fechaLimitePagoCampaign = fechaLimitePagoCampaign;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String Clave) {
        this.Clave = Clave;
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

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getFormAction() {
        return formAction;
    }

    public void setFormAction(String formAction) {
        this.formAction = formAction;
    }

    public String getClaveCampaign() {
        return claveCampaign;
    }

    public void setClaveCampaign(String claveCampaign) {
        this.claveCampaign = claveCampaign;
    }

    @Override
    public String toString() {
        return "FileBucket{" + "file=" + file + ", fechaLimitePagoCampaign=" + fechaLimitePagoCampaign + ", Clave=" + Clave + ", nombre=" + nombre + ", descripcion=" + descripcion + ", producto=" + producto + ", formAction=" + formAction + ", claveCampaign=" + claveCampaign + ", id=" + id + '}';
    }


}