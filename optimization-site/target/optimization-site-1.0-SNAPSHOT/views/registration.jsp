<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style-login.css" />">
</head>
<<body>
<div class="wrapper">
    <div class="login">
        <div id="login-form">
            <h1>РЕГИСТРАЦИЯ</h1>
            <fieldset>
                <form name="user" action="registration" method="POST">
                    <c:if test="${not empty error}">
                        <span>${error}</span>
                    </c:if>
                    <input type="text" name='user_login'  value="Логин" onBlur="if(this.value=='')this.value='Логин'" onFocus="if(this.value=='Логин')this.value='' ">
                    <input type="email" name='email'  value="E-mail" onBlur="if(this.value=='')this.value='E-mail'" onFocus="if(this.value=='E-mail')this.value='' ">
                    <input type="password"name='password_login' value="Пароль" onBlur="if(this.value=='')this.value='Пароль'" onFocus="if(this.value=='Пароль')this.value='' ">
                    <input type="password"name='confirmPassword' value="Пароль" onBlur="if(this.value=='')this.value='Пароль'" onFocus="if(this.value=='Пароль')this.value='' ">
                    <input type="submit" value="ОТПРАВИТЬ">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <div class ="footer" style="text-align: center">
                        <a href="login">Авторизация</a>
                    </div>
                </form>
            </fieldset>
        </div>
    </div>
</div>
</body>
</html>
