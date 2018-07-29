<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!-- Navigation-->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top" id="mainNav">
    <a class="navbar-brand" href="index.htm">Módulo de Gestión de Campañas</a>
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav navbar-sidenav" id="exampleAccordion">
            <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Dashboard">
                <a class="nav-link" href="<c:url value='/' />">
                    <i class="fa fa-fw fa-home"></i>
                    <span class="nav-link-text">Home</span>
                </a>
            </li>
            <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Campaï¿½as">
                <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseCampanas" data-parent="#accordionCampanas">
                    <i class="fa fa-fw fa-calendar"></i>
                    <span class="nav-link-text">Campañas</span>
                </a>
                <ul class="sidenav-second-level collapse" id="collapseCampanas">
                    <li>
                        <a href="#">Consultar</a>
                    </li>
                    <li>
                        <a href="#">Nueva Campaña</a>
                    </li>
                    <li>
                        <a href="#">Mensaje Inicial</a>
                    </li>
                </ul>
            </li>
            <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Clientes">
                <a class="nav-link nav-link-collapse collapsed" data-toggle="collapse" href="#collapseClientes" data-parent="#accordionClientes">
                    <i class="fa fa-users fa-wrench"></i>
                    <span class="nav-link-text">Clientes</span>
                </a>
                <ul class="sidenav-second-level collapse" id="collapseClientes">
                    <li>
                        <a href="#">Consultar</a>
                    </li>
                </ul>
            </li>
            <li class="nav-item" data-toggle="tooltip" data-placement="right" title="Charts">
                <a class="nav-link" href="#">
                    <i class="fa fa-fw fa-area-chart"></i>
                    <span class="nav-link-text">Reportes</span>
                </a>
            </li>
        </ul>
        <ul class="navbar-nav sidenav-toggler">
            <li class="nav-item">
                <a class="nav-link text-center" id="sidenavToggler">
                    <i class="fa fa-fw fa-angle-left"></i>
                </a>
            </li>
        </ul>
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <a class="nav-link" href="#">
                    ${pageContext.request.userPrincipal.name}</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="modal" data-target="#exampleModal">
                    <i class="fa fa-fw fa-sign-out"></i>Logout</a>
            </li>
        </ul>
    </div>
</nav>