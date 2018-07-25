<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<div class="card mb-3">
    <div class="card-header">
        <i class="fa fa-table"></i> Campañas</div>
    <div class="card-body">
        <!-- Button trigger modal -->
        <!--
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#registrarCampaign">
            Registrar Campaña
        </button>
        -->
        <div class="table-responsive">
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th>Clave</th>
                        <th>Fecha Alta</th>
                        <th>Fecha Limite Pago</th>
                        <th>producto</th>
                        <th>Campaña</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${campaigns}" var="item">
                        <tr>
                            <td>
                                <a href="<c:url value='/campaign/clientes.htm?clave='/>${item.clave}">
                                    ${fn:substring(item.clave,25, 32)}
                                </a>
                            </td>
                            <td>
                                <fmt:formatDate value="${item.fechaCreacion}" dateStyle = "long" />
                            </td>
                            <td>
                                <fmt:formatDate value="${item.fechaLimitePagoCampaign}" dateStyle = "long"  />
                            </td>
                            <td>${item.producto}</td>
                            <td>
                                <button type="button" class="btn btn-primary" onclick="startCampaign('${item.clave}')">
                                    <i class="fa fa-envelope" aria-hidden="true"></i>
                                    Iniciar
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="card-footer small text-muted"></div>
</div>
<!-- Modal -->
<div class="modal fade" id="registrarCampaign" tabindex="-1" role="dialog" aria-labelledby="registrarCampaignLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="registrarCampaignLabel">Registrar Campaña</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form:form>
                <div class="modal-body">
                    <div class="card-body">
                        <div class="form-group">
                            <div class="form-row">
                                <div class="col-md-6">
                                    <form:label path="clave">Clave*</form:label>
                                    <form:input path="clave" class="form-control"/>                
                                </div>
                                <div class="col-md-6">
                                    <form:label path="fechaLimitePagoCampaign">Fecha Limite de Pago*</form:label>
                                    <form:input path="fechaLimitePagoCampaign" class="form-control" /> 
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="form-row">
                                <div class="col-md-12">
                                    <form:label path="producto">Producto*</form:label>
                                    <form:select path="producto" class="form-control">
                                        <form:options items="${productoTipo}" />
                                    </form:select>
                                </div>
                            </div>
                        </div> 
                        <p>* Campos obligatorios.</p>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                    <button type="button" class="btn btn-primary" id="saveCampaign">Guardar</button>
                </div>
            </form:form>
        </div>
    </div>
</div>

<script src="<c:url value='/public/js/campaign/index.js'/>"></script>
