<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


<div class="card mb-3">
    <div class="card-header">
        Mensaje Inicial
    </div>
    <div class="card-body">
        <div id="accordion">
            <div class="card">
                <div class="card-header" id="headingOne">
                    <h5 class="mb-0">
                        <button class="btn btn-link" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                            Agregar
                        </button>
                    </h5>
                </div>
                <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">
                    <div class="card-body">

                        <div class="row">
                            <div class="col-lg-4">

                                <div class="form-group">
                                    <label for="exampleInputEmail1">Nombre del Mensaje*</label>
                                    <input type="text" class="form-control" id="nombreMsg" placeholder="Nombre del Mensaje">
                                </div>
                            </div>
                            <div class="col-lg-4">

                                <div class="form-group">
                                    <label for="exampleInputEmail1">Descripción</label>
                                    <input type="text" class="form-control" id="descripcion" placeholder="Descripción...">
                                </div>
                            </div>
                        </div>
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#bloques">
                            Agregar Bloque.
                        </button>
                        <hr>
                        <div class="row">
                            <div class="col-6">
                                <ul id="sortable" class="list-group">

                                </ul>
                                <br>
                                <!-- Button trigger modal -->
                                <button type="button" class="btn btn-primary btn-sm" onclick="saveBlocks()">
                                    Guardar
                                </button>
                            </div>
                            <div class="col-6">


                                <div class="alert alert-success" role="alert">
                                    <h4 class="alert-heading">Ejemplo:</h4>
                                    <p id="sentenceTest"></p>
                                </div>
                            </div>
                        </div>



                    </div>
                </div>
            </div>
            <div class="card">
                <div class="card-header" id="headingTwo">
                    <h5 class="mb-0">
                        <button class="btn btn-link collapsed" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                            Editar
                        </button>
                    </h5>
                </div>
                <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion">
                    <div class="card-body">
                        <div class="form-group">
                            <div class="container">
                                <div class="row">
                                    <div class="ui-widget">
                                        <label>Seleccione el Mensaje Inicial: </label>
                                        <select id="combobox" onchange="selectMsf(this.value)">
                                            <option value="">Seleccione Mensaje...</option>
                                            <c:forEach items="${listMesajes}" var="listMesaje">
                                                <option value="${listMesaje.id}">${listMesaje.nombreMsg} - ${fn:substring(listMesaje.id, 18, 24)} </option>
                                            </c:forEach>  
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <div class="row">
                            <div class="col-lg-4">
                                <input type="hidden" id="id-update">

                                <div class="form-group">
                                    <label>Nombre del Mensaje*</label>
                                    <input type="text" class="form-control" id="nombreMsg-update" placeholder="Nombre del Mensaje">
                                </div>
                            </div>
                            <div class="col-lg-4">

                                <div class="form-group">
                                    <label for="exampleInputEmail1">Descripción</label>
                                    <input type="text" class="form-control" id="descripcion-update" placeholder="Descripción...">
                                </div>
                            </div>
                        </div>
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#bloquesUpdate">
                            Agregar Bloque.
                        </button>
                        <hr>
                        <div class="row">
                            <div class="col-6">
                                <ul id="sortable-update" class="list-group">

                                </ul>
                                <br>
                                <!-- Button trigger modal -->
                                <button type="button" class="btn btn-primary btn-sm" onclick="updateBlocks()">
                                    Actualizar
                                </button>
                                <button type="button" class="btn btn-danger btn-sm">Borrar</button>
                            </div>
                            <div class="col-6">


                                <div class="alert alert-success" role="alert">
                                    <h4 class="alert-heading">Ejemplo:</h4>
                                    <p id="sentenceTest-update"></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<span style="display: none;" id="listMesajesJson">
${listMesajesJson}
</span>
<style>
    #sortable {cursor: pointer;}
    #sortable-update {cursor: pointer;}
    .custom-combobox {
        position: relative;
        display: inline-block;
    }
    .custom-combobox-toggle {
        position: absolute;
        top: 0;
        bottom: 0;
        margin-left: -1px;
        padding: 0;
    }
    .custom-combobox-input {
        margin: 0;
        padding-top: 2px;
        padding-bottom: 5px;
        padding-right: 5px;
        width: auto;
    }
</style>
<script src="<c:url value='/public/js/campaign/campaignsInitialMessages.js'/>"></script>

<!-- Modal -->
<div class="modal fade" id="bloques" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Agregar Campo </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" align="center">
                <div class="btn-group btn-group-vertical btn-group-toggle" data-toggle="buttons">
                    <label class="btn btn-outline-primary">
                        <input value="[Texto-Libre]" type="radio" name="options" id="option-update" autocomplete="off">[Texto Libre]
                    </label>
                    <c:forEach items="${messagesVars}" var="msg">
                        <label class="btn btn-outline-info">
                            <input value="${msg.nameVar}" type="radio" name="options" id="options-update" autocomplete="off">${msg.nameVar}
                        </label>
                    </c:forEach>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                <button type="button" class="btn btn-success" data-dismiss="modal" onclick="addBlock()">Agregar</button>
            </div>
        </div>
    </div>
</div>


<!-- Modal -->
<div class="modal fade" id="bloquesUpdate" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Agregar Campo Update</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body" align="center">
                <div class="btn-group btn-group-vertical btn-group-toggle" data-toggle="buttons">
                    <label class="btn btn-outline-primary">
                        <input value="[Texto-Libre]" type="radio" name="options-update" id="option" autocomplete="off">[Texto Libre]
                    </label>
                    <c:forEach items="${messagesVars}" var="msg">
                        <label class="btn btn-outline-info">
                            <input value="${msg.nameVar}" type="radio" name="options-update" id="options" autocomplete="off">${msg.nameVar}
                        </label>
                    </c:forEach>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                <button type="button" class="btn btn-success" data-dismiss="modal" onclick="addBlockUpdate()">Agregar</button>
            </div>
        </div>
    </div>
</div>

