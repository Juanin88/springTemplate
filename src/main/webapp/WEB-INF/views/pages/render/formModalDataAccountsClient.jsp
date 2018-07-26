<%-- 
    Document   : formDataAccountsClient
    Created on : 04-jun-2018, 16:58:47
    Author     : emurillo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="modal fade" id="dataAccounts" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" name="dataAccountsModal"><i class="fa fa-credit-card"></i> Administrar cuentas</h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <div class="form-container">
                        <div class="card mb-3">
                            <div class="card-header">
                                <i class="fa fa-address-card"></i>
                                Datos de la Cuenta
                            </div>
                            <div class="card-body">
                                <form:form method="POST" id="edicionCuentaForm">
                                    <div class="row">
                                        <div class="col-md-4 form-group">
                                            <label for="noCuenta_EA" >No. de cuenta *</label>
                                            <input id="noCuenta_EA" name="noCuenta_EA"  class="form-control" onclick="setWhiteBg(this)"/>
                                        </div>
                                        <div class="col-md-4 form-group">
                                            <label for="descProducto_EA" >Descripción del producto *</label>
                                            <input id="descProducto_EA" name="descProducto_EA" class="form-control" onclick="setWhiteBg(this)"/>
                                        </div>
                                        <div class="col-md-4 form-group">
                                            <label for="fechaAperturaCuenta_EA" >Fecha de apertura cuenta *</label>
                                            <input id="fechaAperturaCuenta_EA" name="fechaAperturaCuenta_EA"  class="form-control" autocomplete="off" onclick="setWhiteBg(this)"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4 form-group">
                                            <label for="fechaLimitePago_EA" >Fecha límite de pago *</label>
                                            <input id="fechaLimitePago_EA" name="fechaLimitePago_EA" class="form-control" autocomplete="off" onclick="setWhiteBg(this)"/>
                                        </div>
                                        <div class="col-md-4 form-group">
                                            <label for="diaCorte_EA" >Día de corte *</label>
                                            <input id="diaCorte_EA" maxlength="2" name="diaCorte_EA" class="form-control" autocomplete="off" onclick="setWhiteBg(this)"/>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4 form-group">
                                            <label for="totalDeudor_EA">Total deudor *</label>
                                            <input id="totalDeudor_EA" name="totalDeudor_EA" class="form-control" onclick="setWhiteBg(this)"/>
                                        </div>
                                        <div class="col-md-4 form-group">
                                            <label for="pagoMinimo_EA" >Pago minimo *</label>
                                            <input id="pagoMinimo_EA" name="pagoMinimo_EA" class="form-control" onclick="setWhiteBg(this)"/>
                                        </div>   
                                        <div class="col-md-4 form-group">
                                            <label for="montoMoroso_EA" >Monto moroso *</label>
                                            <input id="montoMoroso_EA" name="montoMoroso_EA" class="form-control" onclick="setWhiteBg(this)"/>
                                        </div>
                                    </div>
                                </form:form>
                            </div>
                            <div class="card-footer align-buttons-card-r">
                                <button class="btn btn-secondary" onclick="cleanDataFormAccount()"><i class="fa fa-ban"></i> Cancelar</button>
                                <button class="btn btn-primary" onclick="validarClienteForm()"><i class="fa fa-save"></i> Guardar</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="container-fluid">
                    <div class="card mb-3">
                        <div class="card-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered dataTable" id="dataTableAccounts" cellspacing="0">
                                    <thead>
                                        <tr role="row">
                                            <th>Cuenta</th>
                                            <th>Producto </th>
                                            <th>Fecha de apertura</th>
                                            <th>Fecha límite</th>
                                            <th>Día de corte</th>
                                            <th>Total deudor</th>
                                            <th>Pago mínimo</th>
                                            <th>Monto moroso</th>
                                            <th>Acciones</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-secondary" data-dismiss="modal"><i class="fa fa-ban"></i> Cancelar</button>
            </div>
        </div>
    </div>
</div>
