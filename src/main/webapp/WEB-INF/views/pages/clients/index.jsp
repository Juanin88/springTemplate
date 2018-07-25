<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="card mb-3">
    <div class="card-header">
        <i class="fa fa-users"></i> Clientes</div>
    <div class="card-body">
        <!-- Button trigger modal -->
        <!--
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#registrarCampaign">
            Registrar Campaña
        </button>
        -->

        <button class="btn btn-primary" type="submit" data-toggle="modal" data-target="#addClient">Agregar <i class="fa fa-user-plus" aria-hidden="true"></i></button>
        <button class="btn btn-primary" type="submit" data-toggle="collapse" data-target="#addClientTelCSV">Agregar Telefonos <i class="fa fa-user-plus" aria-hidden="true"></i></button>
        <button class="btn btn-primary" type="submit" data-toggle="collapse" data-target="#updateClientsCSV">Actualizar Clientes <i class="fa fa-user-plus" aria-hidden="true"></i></button>

        <hr>
        <div class="row">
            <div class="collapse col-md-6"  id="addClientTelCSV">
                <div class="form-container">
                    <form:form method="POST" modelAttribute="fileBucket" enctype="multipart/form-data" class="form-horizontal">
                        <form:input type="hidden" path="formAction" value="addClientTelCSV"/>

                        <div>
                            <div class="form-group">
                                <div class="form-row">
                                    <label for="file">Cargar Archivo de telefonos tipo CSV</label>
                                    <form:input type="file" path="file" id="file" class="form-control input-sm"/>
                                </div>
                                <br>
                                <div class="form-row">
                                    <div class="form-group col-md-2">
                                        <input type="submit" value="Upload" class="btn btn-primary btn-sm">
                                    </div>
                                    <div class="form-group col-md-2">
                                        <a href="#" data-toggle="collapse" class="btn btn-primary btn-sm" data-target="#addClientTelCSV" class="btn btn-primary btn-sm">Cerrar</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>

            <div class="collapse col-md-6"  id="updateClientsCSV">
                <div class="form-container">
                    <form:form method="POST" modelAttribute="fileBucket" enctype="multipart/form-data" class="form-horizontal">
                        <form:input type="hidden" path="formAction" value="updateClientsCSV"/>
                        <div>
                            <div class="form-group">
                                <div class="form-row">
                                    <label for="file">Cargar Archivo de actualización de clientes tipo CSV</label>
                                    <form:input type="file" path="file" id="file" class="form-control input-sm"/>
                                </div>
                                <br>
                                <div class="form-row">
                                    <div class="form-group col-md-2">
                                        <input type="submit" value="Upload" class="btn btn-primary btn-sm">
                                    </div>
                                    <div class="form-group col-md-2">
                                        <a href="#" data-toggle="collapse" class="btn btn-primary btn-sm" data-target="#updateClientsCSV" class="btn btn-primary btn-sm">Cerrar</a>
                                    </div>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-body">
                <div class="table-responsive">
                    <table class="table table-bordered dataTable" id="dataTableClients" width="100%" cellspacing="0">
                        <thead>
                            <tr>
                                <th>Clave cliente</th>
                                <th>Nombre</th>

                                <th>Tel</th>
                                <th>Cuentas</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${clientes}" var="cliente">
                                <tr>
                                    <td>
                                        ${cliente.claveCliente}
                                    </td>
                                    <td>${cliente.nombreCliente}</td>

                                    <td>${cliente.telSms}</td>
                                    <td>
                                        <c:forEach items="${cliente.clienteCuentas}" var="clienteCuenta">
                                            <span class="label label-info">[${clienteCuenta.noCuenta} | ${clienteCuenta.descProducto}]</span><br>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <button name="editClient-${cliente.claveCliente}" 
                                                class="btn btn-primary btn-sm" type="button" 
                                                data-toggle="tooltip" data-placement="top" 
                                                title="Editar cliente">
                                            <i class="fa fa-address-book"></i>
                                        </button>
                                        <button name="adminAccounts-${cliente.claveCliente}" 
                                                class="btn btn-primary btn-sm" type="button" 
                                                data-target="#dataAccounts"
                                                data-toggle="tooltip" data-placement="top" 
                                                title="Editar cuentas"> 
                                            <i class="fa fa-list-alt"></i>
                                        </button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="card-footer small text-muted"></div>
</div>

<jsp:include page="../render/formAddClient.jsp" />
<jsp:include page="../render/formModalDataAccountsClient.jsp" />
<script src="<c:url value='/public/js/clients/index.js'/>"></script>
<script src="<c:url value='/public/js/clients/validarFormClientes.js'/>"></script>

