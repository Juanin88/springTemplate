<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- Logout Modal-->
<div class="modal fade" id="addClient" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="dataClientModal">
                    <i class="fa fa-user"></i> 
                    <span id="title_modal">${titleModal}</span> cliente
                </h5>
                <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row"  id="dataClientToAddSave"  name="dataClientToAddSave">
                    <div class="container-fluid">
                        <div class="form-container">
                            <div class="card mb-3">
                                <div class="card-header">
                                    <i class="fa fa-address-book"></i>
                                    Datos del Cliente
                                </div>
                                <div class="card-body">
                                    <form:form method="POST" action="/spring-mvc-xml/addEmployee" modelAttribute="clienteForm">
                                        <input type="hidden" id="existeCliente" value="0" />
                                        <input type="hidden" id="actionFormCliente" value="${actionFormCliente}" />
                                        <div class="row">
                                            <div class="col-md-4 form-group">
                                                <form:label path="claveCliente">Clave Cliente*</form:label>
                                                <form:input path="claveCliente" class="form-control" 
                                                            onclick="setWhiteBg(this)"/>
                                            </div>
                                            <div class="col-md-4 form-group">
                                                <form:label path="rfc">RFC</form:label>
                                                <form:input path="rfc" class="form-control"/>
                                            </div>
                                            <div class="col-md-4 form-group">
                                                <form:label path="nombreCliente">Nombre Completo*</form:label>
                                                <form:input path="nombreCliente" class="form-control" onclick="setWhiteBg(this)"/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-4 form-group">
                                                <form:label path="calleNoCliente">Calle</form:label>
                                                <form:input path="calleNoCliente" class="form-control"/>
                                            </div>
                                            <div class="col-md-4 form-group">
                                                <form:label path="coloniaCliente">Colonia</form:label>
                                                <form:input path="coloniaCliente" class="form-control"/>
                                            </div>
                                            <div class="col-md-4 form-group">
                                                <form:label path="ciudadCliente">Ciudad</form:label>
                                                <form:input path="ciudadCliente" class="form-control"/>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-4 form-group">
                                                <form:label path="estado">Estado </form:label>
                                                <form:input path="estado" class="form-control"/>
                                            </div>
                                            <div class="col-md-4 form-group">
                                                <form:label path="codPostal">C.P.</form:label>
                                                <form:input path="codPostal" class="form-control"/>
                                            </div>
                                            <div class="col-md-4 form-group">
                                                <form:label path="telSms">Tel*</form:label>
                                                <form:input path="telSms" class="form-control" onclick="setWhiteBg(this)"/>
                                            </div>
                                        </div>
                                        <div class="row">    
                                            <div class="col-md-4 form-group">
                                                <form:label path="correo">Correo</form:label>
                                                <form:input path="correo" class="form-control"/>
                                            </div>
                                        </div>
                                    </form:form>

                                </div>
                            </div>       
                        </div>
                    </div>
                </div>
                <div class="row" id="initAccountClient" name="initAccountClient">
                    <jsp:include page="../render/fomrDataAccountClient.jsp" flush="true"/>
                </div>
            </div>
            <div class="modal-footer">
                <button class="btn btn-secondary" data-dismiss="modal"><i class="fa fa-ban"></i> Cancelar</button>
                <button class="btn btn-primary" onclick="validarClienteForm()"><i class="fa fa-save"></i> Guardar</button>
            </div>
        </div>
    </div>
</div>


