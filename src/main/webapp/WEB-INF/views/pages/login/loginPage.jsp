<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--<html>
<body onload='document.loginForm.username.focus();'>
        <h3>JournalDEV Tutorials</h3>

<c:if test="${not empty error}"><div>${error}</div></c:if>
<c:if test="${not empty message}"><div>${message}</div></c:if>

<form name='login' action="<c:url value='/loginPage' />" method='POST'>
        <table>
                <tr>
                        <td>UserName:</td>
                        <td><input type='text' name='username' value=''></td>
                </tr>
                <tr>
                        <td>Password:</td>
                        <td><input type='password' name='password' /></td>
                </tr>
                <tr>
                        <td colspan='2'><input name="submit" type="submit" value="submit" /></td>
                </tr>
        </table>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
</body>
</html>-->

<div class="container">
    <div class="card card-login mx-auto mt-5">
        <div class="card-header">Login</div>
        <div class="card-body">
            <form name='login' action="<c:url value='loginPage' />" method='POST'>
                <c:if test="${not empty error}"><div>${error}</div></c:if>
                <c:if test="${not empty message}"><div>${message}</div></c:if>

                    <div class="form-group">
                        <label for="Usuario">Usuario</label>
                        <input class="form-control" name='username' id="exampleInputEmail1" type="text" aria-describedby="UsuarioHelp" placeholder="Usuario">
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input class="form-control" name='password' id="exampleInputPassword1" type="password" placeholder="Password">
                    </div>
                    <div class="form-group">
                        <div class="form-check">
                            <label class="form-check-label">
                                <input class="form-check-input" type="checkbox"> Remember Password</label>
                        </div>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <input class="btn btn-primary btn-block" name="submit" type="submit" value="Login" />
            </form>
            <div class="text-center">
                <a class="d-block small mt-3" href="register.html">Register an Account</a>
                <a class="d-block small" href="forgot-password.html">Forgot Password?</a>
            </div>
        </div>
    </div>
</div>
