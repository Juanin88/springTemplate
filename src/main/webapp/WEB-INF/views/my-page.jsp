<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
    <body>
        <p>URI: ${uri} <br/>
            User :  ${user} <br/>
            roles:  ${roles} <br/><br/>
        </p>
        <form action="<c:url value='/logout' />" method="post">
            <input type="hidden"
                   name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
            <input type="submit" value="Logout">
        </form>
    </body>
</html>