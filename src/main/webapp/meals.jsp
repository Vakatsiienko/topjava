<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<form action="users" method="POST">
    <label for="userSelect">User:</label>
    <select id="userSelect" name="userSelect">
        <option
                <c:if test="${!empty userType && userType.equals('admin')}">
                    selected
                </c:if>
        >admin
        </option>
        <option
                <c:if test="${!empty userType && userType.equals('user')}">
                    selected
                </c:if>
        >user
        </option>
    </select>
    <input type="text" name="redirectUri" value="/meals" hidden>
    <input type="text" name="action" value="switchUser" hidden>
    <input type="submit" value="Change"/>
</form>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h2>Meal list</h2>
    <a href="meals?action=create">Add Meal</a>
    <hr>
    <form id="dateAndTimeFilterForm" action="meals">
        <fieldset style="width:420px;">
            <label for="startDate">Start Date:</label>
            <input id="startDate" name="startDate" type="date"
                   value="${param.startDate}"
            <%--<c:if test="${!empty param.startDate}">--%>
            <%--value="${fn:formatDate(param.startDate)}"--%>
            <%--</c:if>--%>
            >
            <label for="startTime">Start Time:</label>
            <input id="startTime" name="startTime" type="time"
                   value="${param.startTime}"
            <%--<c:if test="${!empty param.startTime}">--%>
            <%--value="${fn:formatTime(param.startTime)}"--%>
            <%--</c:if>--%>
            >
            <br>
            <label for="endDate">End Date:</label>
            <input id="endDate" name="endDate" type="date"
            <c:if test="${!empty param.endDate}">
                   value="${fn:formatDate(param.endDate)}"
            </c:if>>
            <label for="endTime">End Time:</label>
            <input id="endTime" name="endTime" type="time"
            <c:if test="${!empty param.endTime}">
                   value="${fn:formatTime(param.endTime)}"
            </c:if>>
            <br>
            <input type="text" name="action" value="filter" hidden>
            <input type="submit">
            <a href="meals">Cancel</a>
        </fieldset>
    </form>
    <table border="1" cellpadding="8" cellspacing="0" width="450px">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page"
                         type="ru.javawebinar.topjava.model.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
