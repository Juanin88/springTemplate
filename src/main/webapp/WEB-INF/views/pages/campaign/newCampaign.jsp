<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<div class="card mb-3">
    <div class="card-header">
        <i class="fa fa-table"></i> Campañas</div>
    <div class="card-body">
        <div class="container">
            <div class="form-container">
                <form:form method="POST" modelAttribute="fileBucket" enctype="multipart/form-data" class="form-horizontal">
                    <div class="row">
                        <div class="form-group col-md-12">
                            <div style="display: none">
                                <div class="form-row">
                                    <div class="col-md-4">
                                        <form:label path="clave">Clave</form:label>
                                        <form:input path="clave" class="form-control" readonly="true"/>      
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="form-row">
                                    <div class="col-md-3">
                                        <form:label path="nombre">Nombre de la Campaña*</form:label>
                                        <form:input path="nombre" class="form-control" required="required"/>      
                                    </div>
                                    <div class="col-md-3">
                                        <form:label path="id">Seleccione el Mensaje Inicial</form:label>
                                        <form:select path="id" class="form-control" autocomplete="off" required="required">
                                            <form:options items="${listMesajes}" />
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="form-row">
                                    <div class="col-md-3">
                                        <form:label path="producto">Producto*</form:label>
                                        <form:select path="producto" class="form-control" required="required">
                                            <form:options items="${productoTipo}" />
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="form-row">
                                    <div class="col-md-6">
                                        <form:label path="descripcion">Descripcion</form:label>
                                        <form:textarea path="descripcion" class="form-control"/>      
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="form-row">
                                    <div class="col-md-6">
                                        <label for="file">Cargar Archivo tipo CSV (Opcional)</label>
                                        <form:input type="file" path="file" id="file" class="form-control input-sm"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <input type="submit" value="Upload" class="btn btn-primary btn-sm">
                </form:form>
            </div>
        </div>
    </div>
    <div class="card-footer small text-muted"></div>
</div>

<script src="<c:url value='/public/js/campaign/index.js'/>"></script>
