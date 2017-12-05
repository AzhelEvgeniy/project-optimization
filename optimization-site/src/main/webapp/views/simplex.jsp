<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Симплекс метод</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />">
    <script src="<c:url value="/resources/scripts/script.js" />"></script>
</head>
<body>
    <div class="wrapper">
        <div class="header">
            <div class="menu">
                <ul>
                    <li><a href="main">Главная</a></li>
                    <li><a href="simplex">Симплекc Метод</a></li>
                    <li><a href="genetic">Генетический алгоритм</a></li>
                </ul>
                <div class="user">
                    Добро пожаловать, ${username}.<br/>
                    <c:url var="logoutUrl" value="/logout"/>
                    <form id="formLogout" action="${logoutUrl}" method="post">
                        <input type="submit" value="Выйти" />
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </div>
            </div>
        </div>
        <div class="content">
            <p id="optimization">Симплекс Метод</p>
            <p>Симплекс-метод - это итеративный процесс направленного решения системы уравнений по шагам, который начинается с опорного решения и в поисках лучшего варианта движется по угловым точкам области допустимого решения, улучшающих значение целевой функции до тех пор, пока целевая функция не достигнет оптимального значения.</p>
            <div class="function">
                Математическая модель
                <form id="functionForm" name="functionForm" action="simplex" method="post" onsubmit="return validate();">
                    <p><label>Целевая функция: </label>
                        <input type="text" id="targetFunction" name="targetFunction" value="${targetFunction}"/>
                        <span id="errorTargetFunction" class="error"></span>
                        <select name="MaxMin" >
                            <option value="max">max</option>
                            <c:choose>
                                <c:when test="${maxMin eq 'min'}">
                                    <option value="min" selected>min</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="min">min</option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </p>
                    <c:if test="${empty answer}">
                        <label>Количество ограниений: </label>
                        <input type="number" id="countLimit" name="countLimit"  step="1" max="7" min="0" value="${countLimit + 0}" />
                        <input type="button" name="btnCountLimit" value="Далее" onClick="addInputTextLimits()">
                    </c:if>
                        <c:if test="${not empty countLimit}">
                        <div>Ограничения:</div>
                        <c:forEach var="limit" items="${limits}">
                            <input type="text" name="limit${limits.indexOf(limit)}" value="${limit}"/><br />
                        </c:forEach>
                    </c:if>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                </form>
            </div>
            <c:if test="${not empty answer}">
                <div id="solution">
                    <div class="headerSolution">Решение</div>
                    <c:forEach var="table" items="${processTable}" >
                        <table id="simplexTable">
                            <c:choose>
                                <c:when test="${processTable.indexOf(table) == 0}">
                                    <caption>Базисное решение</caption>
                                </c:when>
                                <c:otherwise>
                                    <caption>Итерация ${processTable.indexOf(table)}</caption>
                                </c:otherwise>
                            </c:choose>
                        <c:forEach var="row" items="${table}">
                            <tr>
                                <c:forEach var="elem" items="${row}">
                                    <td>
                                    ${elem}
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                        </table>
                    </c:forEach>
                </div>
                <div id="answer">
                    <p class="headerSetting">Ответ</p>
                    <table id="answerTable">
                        <tr>
                            <td>Переменные</td>
                            <td>Результат</td>
                        </tr>

                        <c:forEach var="i" begin="0" end="${variables.size()-1}">
                            <tr>
                                <td>${variables[i]}</td>
                                <td>${answerArgument[i]}</td>
                            </tr>
                        </c:forEach>
                    </table>
                    F(${variablesJoin}) = ${targetFunction} = ${answer}
                </div>
            </c:if>
        </div>
    </div>
    <div class="footer">
        &#169; 2017 Copyright. All Rights Reserved.
    </div>
</body>
</html>
