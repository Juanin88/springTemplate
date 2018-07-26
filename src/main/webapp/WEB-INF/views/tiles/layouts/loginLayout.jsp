<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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
        <script src="<c:url value='/public/vendor/jquery/jquery.js'/>"></script>

    </head>

    
    <body class="bg-dark">
                        <tiles:insertAttribute name="body" />

  <!-- Bootstrap core JavaScript-->
        <script src="<c:url value='/public/vendor/bootstrap/js/bootstrap.bundle.js' />"></script>
  <!-- Core plugin JavaScript-->
        <script src="<c:url value='/public/vendor/jquery-easing/jquery.easing.js'/>"></script>
</body>

</html>
