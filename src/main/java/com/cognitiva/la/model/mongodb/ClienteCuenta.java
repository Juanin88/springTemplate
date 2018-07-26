/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cognitiva.la.model.mongodb;

import com.owlike.genson.annotation.JsonDateFormat;
import java.util.Date;

/**
 *
 * @author jgarfias
 */
public class ClienteCuenta {

    private String agenciaActual = "";
    private String agenciaPrevia = "";
    private String codigoBloqueo = "";
    private String consecutivo = "";
    private String cuentaCheques = "";
    private String descProducto = "";
    private Integer diaCorte = 0;
    private Integer diasAgencia = 0;
    private Integer diasFaltantes = 0;
    private Date fecCodigoBloqueo;
    private Date fecRefAgencia;
    private Date fecRefAgenciaPrevia;
    private Date fecUltimaCompra;
    private Date fecUltimaDisposicion;
    private Date fechaAperturaCuenta;
    private Date fechaCambioSegmento;
    private Date fechaLimitePago;
    private Double limiteCredito = 0.0;
    private Double montoAsignadoAgencia = 0.0;
    private Double montoAsignadoAgenciaPrev = 0.0;
    private Double montoMoroso = 0.0;
    private Double montoUltimaCompra = 0.0;
    private Double montoUltimaDisposicion = 0.0;
    private String noCuenta = "";
    private Double pagoMinimo = 0.0;
    private Integer pagosVencidos;
    private Double saldoVencido120Dias = 0.0;
    private Double saldoVencido30Dias = 0.0;
    private Double saldoVencido60Dias = 0.0;
    private Double saldoVencido90Dias = 0.0;
    private String segmentoActual = "";
    private String segmentoPosterior = "";
    private String stateca = "";
    private Double totalDeudor = 0.0;
    
    private Date fechaUltimoPago;
    private Double montoUltimoPago = 0.0;

    public ClienteCuenta() {
    }

    public String getAgenciaActual() {
        return agenciaActual;
    }

    public void setAgenciaActual(String agenciaActual) {
        this.agenciaActual = agenciaActual;
    }

    public String getAgenciaPrevia() {
        return agenciaPrevia;
    }

    public void setAgenciaPrevia(String agenciaPrevia) {
        this.agenciaPrevia = agenciaPrevia;
    }

    public String getCodigoBloqueo() {
        return codigoBloqueo;
    }

    public void setCodigoBloqueo(String codigoBloqueo) {
        this.codigoBloqueo = codigoBloqueo;
    }

    public String getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(String consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getCuentaCheques() {
        return cuentaCheques;
    }

    public void setCuentaCheques(String cuentaCheques) {
        this.cuentaCheques = cuentaCheques;
    }

    public String getDescProducto() {
        return descProducto;
    }

    public void setDescProducto(String descProducto) {
        this.descProducto = descProducto;
    }

    public Integer getDiaCorte() {
        return diaCorte;
    }

    public void setDiaCorte(Integer diaCorte) {
        this.diaCorte = diaCorte;
    }

    public Integer getDiasAgencia() {
        return diasAgencia;
    }

    public void setDiasAgencia(Integer diasAgencia) {
        this.diasAgencia = diasAgencia;
    }

    public Integer getDiasFaltantes() {
        return diasFaltantes;
    }

    public void setDiasFaltantes(Integer diasFaltantes) {
        this.diasFaltantes = diasFaltantes;
    }

    @JsonDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    public Date getFecCodigoBloqueo() {
        return fecCodigoBloqueo;
    }

    @JsonDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    public void setFecCodigoBloqueo(Date fecCodigoBloqueo) {
        this.fecCodigoBloqueo = fecCodigoBloqueo;
    }

    @JsonDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    public Date getFecRefAgencia() {
        return fecRefAgencia;
    }

    @JsonDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    public void setFecRefAgencia(Date fecRefAgencia) {
        this.fecRefAgencia = fecRefAgencia;
    }

    @JsonDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    public Date getFecRefAgenciaPrevia() {
        return fecRefAgenciaPrevia;
    }

    public void setFecRefAgenciaPrevia(Date fecRefAgenciaPrevia) {
        this.fecRefAgenciaPrevia = fecRefAgenciaPrevia;
    }

    @JsonDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    public Date getFecUltimaCompra() {
        return fecUltimaCompra;
    }

    @JsonDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    public void setFecUltimaCompra(Date fecUltimaCompra) {
        this.fecUltimaCompra = fecUltimaCompra;
    }

    @JsonDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    public Date getFecUltimaDisposicion() {
        return fecUltimaDisposicion;
    }

    public void setFecUltimaDisposicion(Date fecUltimaDisposicion) {
        this.fecUltimaDisposicion = fecUltimaDisposicion;
    }

    @JsonDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    public Date getFechaAperturaCuenta() {
        return fechaAperturaCuenta;
    }

    @JsonDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    public void setFechaAperturaCuenta(Date fechaAperturaCuenta) {
        this.fechaAperturaCuenta = fechaAperturaCuenta;
    }

    @JsonDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    public Date getFechaCambioSegmento() {
        return fechaCambioSegmento;
    }

    @JsonDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    public void setFechaCambioSegmento(Date fechaCambioSegmento) {
        this.fechaCambioSegmento = fechaCambioSegmento;
    }

    @JsonDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    public Date getFechaLimitePago() {
        return fechaLimitePago;
    }

    @JsonDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    public void setFechaLimitePago(Date fechaLimitePago) {
        this.fechaLimitePago = fechaLimitePago;
    }

    
    public Double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(Double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public Double getMontoAsignadoAgencia() {
        return montoAsignadoAgencia;
    }

    public void setMontoAsignadoAgencia(Double montoAsignadoAgencia) {
        this.montoAsignadoAgencia = montoAsignadoAgencia;
    }

    public Double getMontoAsignadoAgenciaPrev() {
        return montoAsignadoAgenciaPrev;
    }

    public void setMontoAsignadoAgenciaPrev(Double montoAsignadoAgenciaPrev) {
        this.montoAsignadoAgenciaPrev = montoAsignadoAgenciaPrev;
    }

    public Double getMontoMoroso() {
        return montoMoroso;
    }

    public void setMontoMoroso(Double montoMoroso) {
        this.montoMoroso = montoMoroso;
    }

    public Double getMontoUltimaCompra() {
        return montoUltimaCompra;
    }

    public void setMontoUltimaCompra(Double montoUltimaCompra) {
        this.montoUltimaCompra = montoUltimaCompra;
    }

    public Double getMontoUltimaDisposicion() {
        return montoUltimaDisposicion;
    }

    public void setMontoUltimaDisposicion(Double montoUltimaDisposicion) {
        this.montoUltimaDisposicion = montoUltimaDisposicion;
    }

    public String getNoCuenta() {
        return noCuenta;
    }

    public void setNoCuenta(String noCuenta) {
        this.noCuenta = noCuenta;
    }

    public Double getPagoMinimo() {
        return pagoMinimo;
    }

    public void setPagoMinimo(Double pagoMinimo) {
        this.pagoMinimo = pagoMinimo;
    }

    public Integer getPagosVencidos() {
        return pagosVencidos;
    }

    public void setPagosVencidos(Integer pagosVencidos) {
        this.pagosVencidos = pagosVencidos;
    }

    public Double getSaldoVencido120Dias() {
        return saldoVencido120Dias;
    }

    public void setSaldoVencido120Dias(Double saldoVencido120Dias) {
        this.saldoVencido120Dias = saldoVencido120Dias;
    }

    public Double getSaldoVencido30Dias() {
        return saldoVencido30Dias;
    }

    public void setSaldoVencido30Dias(Double saldoVencido30Dias) {
        this.saldoVencido30Dias = saldoVencido30Dias;
    }

    public Double getSaldoVencido60Dias() {
        return saldoVencido60Dias;
    }

    public void setSaldoVencido60Dias(Double saldoVencido60Dias) {
        this.saldoVencido60Dias = saldoVencido60Dias;
    }

    public Double getSaldoVencido90Dias() {
        return saldoVencido90Dias;
    }

    public void setSaldoVencido90Dias(Double saldoVencido90Dias) {
        this.saldoVencido90Dias = saldoVencido90Dias;
    }

    public String getSegmentoActual() {
        return segmentoActual;
    }

    public void setSegmentoActual(String segmentoActual) {
        this.segmentoActual = segmentoActual;
    }

    public String getSegmentoPosterior() {
        return segmentoPosterior;
    }

    public void setSegmentoPosterior(String segmentoPosterior) {
        this.segmentoPosterior = segmentoPosterior;
    }

    public String getStateca() {
        return stateca;
    }

    public void setStateca(String stateca) {
        this.stateca = stateca;
    }

    public Double getTotalDeudor() {
        return totalDeudor;
    }

    public void setTotalDeudor(Double totalDeudor) {
        this.totalDeudor = totalDeudor;
    }

    @JsonDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
    public Date getFechaUltimoPago() {
        return fechaUltimoPago;
    }
    
    @JsonDateFormat("yyyy-MM-dd")
    public void setFechaUltimoPago(Date fechaUltimoPago) {
        this.fechaUltimoPago = fechaUltimoPago;
    }

    public Double getMontoUltimoPago() {
        return montoUltimoPago;
    }

    public void setMontoUltimoPago(Double montoUltimoPago) {
        this.montoUltimoPago = montoUltimoPago;
    }

    public ClienteCuenta(String agenciaActual, String agenciaPrevia, String codigoBloqueo, String consecutivo, String cuentaCheques, String descProducto, Date fecCodigoBloqueo, Date fecRefAgencia, Date fecRefAgenciaPrevia, Date fecUltimaCompra, Date fecUltimaDisposicion, Date fechaAperturaCuenta, Date fechaCambioSegmento, Date fechaLimitePago, String noCuenta, Integer pagosVencidos, String segmentoActual, String segmentoPosterior, String stateca, Date fechaUltimoPago) {
        this.agenciaActual = agenciaActual;
        this.agenciaPrevia = agenciaPrevia;
        this.codigoBloqueo = codigoBloqueo;
        this.consecutivo = consecutivo;
        this.cuentaCheques = cuentaCheques;
        this.descProducto = descProducto;
        this.fecCodigoBloqueo = fecCodigoBloqueo;
        this.fecRefAgencia = fecRefAgencia;
        this.fecRefAgenciaPrevia = fecRefAgenciaPrevia;
        this.fecUltimaCompra = fecUltimaCompra;
        this.fecUltimaDisposicion = fecUltimaDisposicion;
        this.fechaAperturaCuenta = fechaAperturaCuenta;
        this.fechaCambioSegmento = fechaCambioSegmento;
        this.fechaLimitePago = fechaLimitePago;
        this.noCuenta = noCuenta;
        this.pagosVencidos = pagosVencidos;
        this.segmentoActual = segmentoActual;
        this.segmentoPosterior = segmentoPosterior;
        this.stateca = stateca;
        this.fechaUltimoPago = fechaUltimoPago;
    }

    @Override
    public String toString() {
        return "ClienteCuenta{" + "agenciaActual=" + agenciaActual + ", agenciaPrevia=" + agenciaPrevia + ", codigoBloqueo=" + codigoBloqueo + ", consecutivo=" + consecutivo + ", cuentaCheques=" + cuentaCheques + ", descProducto=" + descProducto + ", diaCorte=" + diaCorte + ", diasAgencia=" + diasAgencia + ", diasFaltantes=" + diasFaltantes + ", fecCodigoBloqueo=" + fecCodigoBloqueo + ", fecRefAgencia=" + fecRefAgencia + ", fecRefAgenciaPrevia=" + fecRefAgenciaPrevia + ", fecUltimaCompra=" + fecUltimaCompra + ", fecUltimaDisposicion=" + fecUltimaDisposicion + ", fechaAperturaCuenta=" + fechaAperturaCuenta + ", fechaCambioSegmento=" + fechaCambioSegmento + ", fechaLimitePago=" + fechaLimitePago + ", limiteCredito=" + limiteCredito + ", montoAsignadoAgencia=" + montoAsignadoAgencia + ", montoAsignadoAgenciaPrev=" + montoAsignadoAgenciaPrev + ", montoMoroso=" + montoMoroso + ", montoUltimaCompra=" + montoUltimaCompra + ", montoUltimaDisposicion=" + montoUltimaDisposicion + ", noCuenta=" + noCuenta + ", pagoMinimo=" + pagoMinimo + ", pagosVencidos=" + pagosVencidos + ", saldoVencido120Dias=" + saldoVencido120Dias + ", saldoVencido30Dias=" + saldoVencido30Dias + ", saldoVencido60Dias=" + saldoVencido60Dias + ", saldoVencido90Dias=" + saldoVencido90Dias + ", segmentoActual=" + segmentoActual + ", segmentoPosterior=" + segmentoPosterior + ", stateca=" + stateca + ", totalDeudor=" + totalDeudor + ", fechaUltimoPago=" + fechaUltimoPago + ", montoUltimoPago=" + montoUltimoPago + '}';
    }

}
