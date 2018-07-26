<%-- 
    Document   : fomrDataAccountClient
    Created on : 05-jun-2018, 11:07:07
    Author     : emurillo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="container-fluid">
    <div class="form-container">
        <div class="card mb-3">
            <div class="card-header">
                <i class="fa fa-address-card"></i>
                Datos de la Cuenta
            </div>
            <div class="card-body">
                <form:form method="POST" action="/spring-mvc-xml/addEmployee" modelAttribute="clienteCuentaForm">
                    <div class="row">
                        <div class="col-md-4 form-group">
                            <form:label path="noCuenta">No. de cuenta *</form:label>
                            <form:input path="noCuenta" class="form-control" onclick="setWhiteBg(this)"/>
                        </div>
                        <div class="col-md-4 form-group">
                            <form:label path="descProducto">Descripción del producto *</form:label>
                            <form:input path="descProducto" class="form-control" onclick="setWhiteBg(this)"/>
                        </div>
                        <div class="col-md-4 form-group">
                            <form:label path="fechaAperturaCuenta">Fecha de apertura cuenta *</form:label>
                            <form:input path="fechaAperturaCuenta" class="form-control" autocomplete="off" onclick="setWhiteBg(this)"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4 form-group">
                            <form:label path="fechaLimitePago">Fecha límite de pago *</form:label>
                            <form:input path="fechaLimitePago" class="form-control" autocomplete="off" onclick="setWhiteBg(this)"/>
                        </div>
                        <div class="col-md-4 form-group">
                            <form:label path="diaCorte">Día de corte *</form:label>
                            <form:input path="diaCorte" maxlength="2" class="form-control" autocomplete="off" onclick="setWhiteBg(this)"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4 form-group">
                            <form:label path="totalDeudor">Total deudor *</form:label>
                            <fmt:setLocale value = "es_MX"/>
                            <fmt:formatNumber value = "${totalDeudor}" type = "currency"/>
                            <form:input path="totalDeudor" class="form-control" onclick="setWhiteBg(this)"/>
                        </div>
                        <div class="col-md-4 form-group">
                            <form:label path="pagoMinimo">Pago minimo *</form:label>
                            <fmt:setLocale value = "es_MX"/>
                            <fmt:formatNumber value = "${pagoMinimo}" type = "currency"/>
                            <form:input path="pagoMinimo" class="form-control" onclick="setWhiteBg(this)"/>
                        </div>   
                        <div class="col-md-4 form-group">
                            <form:label path="montoMoroso">Monto moroso *</form:label>
                            <fmt:setLocale value = "es_MX"/>
                            <fmt:formatNumber value = "${montoMoroso}" type = "currency"/>
                            <form:input path="montoMoroso" class="form-control" onclick="setWhiteBg(this)"/>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
