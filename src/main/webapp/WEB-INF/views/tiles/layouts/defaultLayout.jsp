<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <!-- Bootstrap core CSS-->
        <link href="<c:url value='/public/vendor/bootstrap/css/bootstrap.min.css' />" rel="stylesheet">

        <!-- Custom fonts for this template-->
        <link href="<c:url value='/public/vendor/font-awesome/css/font-awesome.min.css'/>" rel="stylesheet" type="text/css">
        <!-- Custom styles for this template-->
        <link href="<c:url value='/public/css/sb-admin.css'/>" rel="stylesheet">
        <link href="<c:url value='/public/datepicker-1.6.4/css/bootstrap-datepicker3.min.css'/>" rel="stylesheet">
        <script src="<c:url value='/public/vendor/jquery/jquery.js'/>"></script>
        <script src="<c:url value='/public/js/moment.min.js'/>"></script>
        <script src="<c:url value='/public/js/moment.es.js'/>"></script>
        <script src="<c:url value='/public/js/bootbox.min.js'/>"></script>
        <script src="<c:url value='/public/js/jquery.blockUI.js'/>"></script>
        
        <script src="//cdnjs.cloudflare.com/ajax/libs/d3/4.7.2/d3.min.js"></script>
        <script src="<c:url value='/public/js/d3/d3pie.min.js'/>"></script>


        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

        <base href="<c:url value='/'/>" />
        <link href="<c:url value='/public/css/utiles.css' />" rel="stylesheet">
        
    </head>

    <body class="fixed-nav sticky-footer bg-dark" id="page-top">
        <tiles:insertAttribute name="header" />
        <div class="content-wrapper">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12">
                        <tiles:insertAttribute name="body" />
                    </div>
                </div>
            </div>
            <!-- /.container-fluid-->
            <tiles:insertAttribute name="footer" />
        </div>

        <script src="<c:url value='/public/vendor/bootstrap/js/bootstrap.bundle.js' />"></script>
        <script src="<c:url value='/public/vendor/jquery-easing/jquery.easing.js'/>"></script>
        <script src="<c:url value='/public/vendor/datatables/jquery.dataTables.js'/>"></script>
        <script src="<c:url value='/public/vendor/datatables/dataTables.bootstrap4.js'/>"></script>
        <script src="<c:url value='/public/js/sb-admin.min.js'/>"></script>
        <script src="<c:url value='/public/js/sb-admin-datatables.js'/>"></script>
        <script src="<c:url value='/public/datepicker-1.6.4/js/bootstrap-datepicker.min.js'/>"></script>
        <script src="<c:url value='/public/datepicker-1.6.4/locales/bootstrap-datepicker.es.min.js'/>"></script>


    </body>
</html>
