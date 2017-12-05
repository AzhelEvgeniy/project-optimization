<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Авторизация</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style-login.css" />">
</head>

<body>
<div class="wrapper">
    <div class="login">
        <div id="login-form">
            <h1>АВТОРИЗАЦИЯ</h1>
            <fieldset>
                <form action="<c:url value='/login' />" method="POST">
                    <c:if test="${not empty error}">
                        <span>${error}</span>
                    </c:if>
                    <input type="text" name='user_login'  value="Логин" onBlur="if(this.value=='')this.value='Логин'" onFocus="if(this.value=='Логин')this.value='' ">
                    <input type="password"name='password_login' value="Пароль" onBlur="if(this.value=='')this.value='Пароль'" onFocus="if(this.value=='Пароль')this.value='' ">
                    <input type="submit" value="ВОЙТИ">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                   <div class ="footer">
                       <a href="/registration">Регистрация</a>
                        <div class="rm"><input id="remember-me" name="remember-me" type="checkbox"> <label for="remember-me">Запомнить меня</label></div>
                   </div>
                </form>
            </fieldset>
        </div>
    </div>
</div>
</body>
</html>