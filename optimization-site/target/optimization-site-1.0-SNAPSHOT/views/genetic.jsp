<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Генетический алгоритм</title>
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
        <p id="optimization">Генетический алгоритм</p>
        <p>Генети́ческий алгори́тм (англ. genetic algorithm) — это эвристический алгоритм поиска, используемый для решения задач оптимизации и моделирования путём случайного подбора, комбинирования и вариации искомых параметров с использованием механизмов, аналогичных естественному отбору в природе.</p>
        <div class="function">
            Математическая модель
            <form id="functionForm" action="genetic" method="post" onsubmit="return validate();">
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
                    <input type="button" name="btnCountLimit" value="Далее" onClick="return addInputTextLimits();">
                    <span id="errorCountLimit" class="error"></span>
                </c:if>
                    <input type="hidden" id="countGenerationHidden" name="countGeneration" value="350">
                    <input type="hidden" id="countChromosomeInGenerationHidden" name="countChromosomeInGeneration" value="2100">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <c:if test="${not empty countLimit}">
                    <div>Ограничения:</div>
                    <c:forEach var="limit" items="${limits}">
                        <input type="text" name="limit${limits.indexOf(limit)}" value="${limit}"/><br />
                    </c:forEach>
                </c:if>
            </form>
        </div>
        <div id="setting-algotithm">
            <p class="headerSetting">Настройки алгоритма</p>
            <p>
                <label>Колличество хромосом в поколении</label>
                <input type="number" id="countChromosomeInGeneration" name="countChromosomeInGeneration" min="1"  value="2100" oninput="changeSetting();">
            </p>
            <p>
                <label>Колличество поколений</label>
                <input type="number" id="countGeneration" name="countGeneration" min="1" value="350" oninput="changeSetting();">
            </p>
            <!--<input type="button" name="" value="Использовать значения по умочанию"/><br />-->
        </div>
        <c:if test="${not empty answer}">
            <div id="solution">
                <div class="headerSolution">Решение</div>
                <div class="wrapperSolution">
                    <center><strong>Лучшие хромосомы в поколении</strong></center>
                    <%int i = 0;%>
                    <c:forEach var="generation" items="${progressGeneration}" >
                        <table>
                             <tr>
                                 <td>№</td>
                                 <c:forEach var="varieble" items="${variables}">
                                     <td>${varieble}</td>
                                 </c:forEach>
                                 <td>f(x)</td>
                             </tr>
                             <tr>
                                 <td><%=++i%></td>
                        <c:forEach var="argument" items="${generation}">
                                    <td>${argument}</td>
                        </c:forEach>
                                 <td>${function.f(generation)}</td>
                            </tr>
                        </table>
                    </c:forEach>
                </div>
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
                <br/>
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
