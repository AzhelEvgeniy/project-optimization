<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Главная</title>
    <link rel="stylesheet" href="<c:url value="resources/css/style.css" />">
</head>
<body>
<div class="wrapper">
    <div class="header">
        <div class="menu">
            <ul>
                <li><a href="">Главная</a></li>
                <li><a href="simplex">Симплекc Метод</a></li>
                <li><a href="genetic">Генетический алгоритм</a></li>
            </ul>
        </div>
    </div>
    <div class="content">
        <p id="optimization">Оптимизация</p>
        <p>Оптимизация - целенаправленная деятельность, заключающаяся в получении наилучших результатов при соответствующих условиях.</p>

        <img id="graph" src="<c:url value="resources/image/graph.PNG" />"/>
    </div>
</div>
<div class="footer">
    &#169; 2017 Copyright. All Rights Reserved.
</div>
</body>
</html>