<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="success">

    <div class="alert alert-success" role="alert"> 
        Se ha cargado exitosamente el archivo: <strong><%= request.getParameter("fileName")%>
        </strong>
    </div>

</div>
