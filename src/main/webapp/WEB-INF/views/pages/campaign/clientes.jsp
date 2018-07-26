<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="card mb-3">
    <div class="card-header">
        <i class="fa fa-table"></i> Clientes - Campaña [${campaign.clave}]</div>
    <input type="hidden" id="clave" value="${campaign.clave}" />
    <div class="card-body">
        
        
        <div class="row">
            <div class="col-md-12">
                <div class="dropdown">
                    <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Acciones
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <a class="dropdown-item" href="#" data-toggle="modal" data-target="#addClient"><i class="fa fa-user-plus" aria-hidden="true"></i> Agregar Cliente</a>
                        <a class="dropdown-item" href="#" data-toggle="modal" data-target="#addClientCSV">Agregar Clientes (CSV) </a>
                        <a class="dropdown-item" href="#" data-toggle="modal" data-target="#desClientCSV"><i class="fa fa-user-times" aria-hidden="true"></i> Desasignación (CSV)</a>
                        <a class="dropdown-item" href="<c:url value='/campaign/download-layout.htm?clave=' />${campaign.clave}"><i class="fa fa-download" aria-hidden="true"></i> Descargar Layout (txt) </a>
                    </div>
                </div>
            </div>
        </div>
                    <hr>              

          

        <div class="modal fade" id="addClientCSV" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
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
                    <form:form method="POST" modelAttribute="fileBucket" enctype="multipart/form-data" class="form-horizontal">    
                        <form:input type="hidden" path="formAction" value="addClientCSV"/>
                        <div class="modal-body">
                            <div class="container">
                                <div class="form-container">
                                    <div class="row">
                                        <div class="form-group col-md-6">
                                            <div class="form-group">
                                                <div class="form-row">
                                                    <form:input path="clave" type="hidden" class="form-control" readonly="true"/>      
                                                    <label for="file">Cargar Archivo tipo CSV</label>
                                                    <form:input type="file" path="file" id="file" class="form-control input-sm"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-secondary" data-dismiss="modal"><i class="fa fa-ban"></i> Cancelar</button>
                            <input type="submit" value="Upload" class="btn btn-primary" >
                        </div>
                    </form:form>    
                </div>
            </div>
        </div>
        <div class="modal fade" id="desClientCSV" tabindex="-1" role="dialog" aria-labelledby="dessClientCsv" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">
                            <i class="fa fa-user"></i> 
                            Desasignación Cliente
                        </h5>
                        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <form:form method="POST" modelAttribute="fileBucket" enctype="multipart/form-data" class="form-horizontal">    
                        <form:input type="hidden" path="formAction" value="desClientCSV"/>
                        <form:input type="hidden" path="claveCampaign"/>

                        <div class="modal-body">
                            <div class="container">
                                <div class="form-container">
                                    <div class="row">
                                        <div class="form-group col-md-6">
                                            <div class="form-group">
                                                <div class="form-row">
                                                    <form:input path="clave" type="hidden" class="form-control" readonly="true"/>      
                                                    <label for="file">Cargar Archivo tipo CSV</label>
                                                    <form:input type="file" path="file" id="file" class="form-control input-sm"/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-secondary" data-dismiss="modal"><i class="fa fa-ban"></i> Cancelar</button>
                            <input type="submit" value="Upload" class="btn btn-primary" >
                        </div>
                    </form:form>    
                </div>
            </div>
        </div>



        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
            <thead>
                <tr>
                    <th>Clave Cliente (BUC)</th>
                    <th>No. Cuenta</th>
                    <th>TEL</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${campaign.campaignClientes}" var="cliente">
                    <tr>
                        <td>${cliente.claveCliente}</td>
                        <td>${cliente.noCuenta}</td>
                        <td>${cliente.telSms}</td>
                        <td>
                            <c:if test="${cliente.status == true}">
                                <button type="button" class="btn btn-primary" onclick="startClientCampaign('${cliente.telSms}',
                                                '<fmt:formatDate value="${campaign.fechaLimitePagoCampaign}" pattern = "dd/MM/yyyy" />',
                                                '${cliente.noCuenta}', '${campaign.clave}', '${cliente.claveCliente}', '${campaign.idCampaignsInitialMessages}'
                                                )">
                                    <i class="fa fa-envelope" aria-hidden="true"></i>Iniciar</button>
                                </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
                        
                        
          <div class="row">
              <div class="col-md-6" style="text-align: right">
                <strong>Fecha de Alta de Campaña : </strong>
            </div>
            <div class="col-md-6">
                <fmt:formatDate value="${campaign.fechaCreacion}" dateStyle = "long" />
            </div>
        </div>
        <div class="row">    
            <div class="col-md-6" style="text-align: right">
                <strong>Fecha Limite de Pago de la Campaña:</strong>
            </div>
            <div class="col-md-6">
                <fmt:formatDate value="${campaign.fechaLimitePagoCampaign}" dateStyle = "long" />
            </div>
        </div>

        <div class="row">
            <div class="col-md-6" style="text-align: right">
                <strong>Ejemplo de Texto del Mensaje [${CampaignsInitialMessages.nombreMsg}]:</strong>
            </div>
            <div class="col-md-6" >
                <p>
                    <c:forEach items="${CampaignsInitialMessages.mensajes}" var="mensajeBlock">
                        ${mensajeBlock.content}
                    </c:forEach>
                </p>
            </div>
        </div>                
                        
                        
    </div>
    <div class="card-footer small text-muted"></div>
</div>
<jsp:include page="../render/formAddClient.jsp" />
<script src="<c:url value='/public/js/campaign/index.js'/>"></script>