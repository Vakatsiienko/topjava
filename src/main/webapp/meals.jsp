<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="ru.javawebinar.topjava.util.TimeUtil" %>
<html>
<head>
    <title>Meal list</title>
    <style type="text/css">
        .exceed {
            color: red;
        }

        .notExceed {
            color: green;
        }
    </style>
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<table border="1">
    <tr>
        <th>Datetime</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Action</th>
    </tr>
    <c:forEach items="${mealList}" var="meal">
        <jsp:useBean id="meal" scope="page"
                     type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr class="${meal.exceed ? exceed : notExceed}">
            <td>
                    ${fn:formatDateTime(meal.dateTime)}
            </td>
            <td>
                    ${meal.description}
            </td>
            <td>
                    ${meal.calories}
            </td>
            <td>
                <form method="POST" action="/meals">
                    <input hidden name="method" value="DELETE">
                    <input hidden name="id" type="number" value="${meal.id}">
                    <input type="submit" value="Delete">
                </form>
                <a href="/meals?id=${meal.id}">Edit</a>
            </td>
        </tr>
    </c:forEach>
</table>
<div id="createOrUpdateForm">
    <form method="POST" action="/meals">
        <fieldset>
            <c:choose>
                <c:when test="${empty updatingMeal}">
                    <input hidden name="method" value="POST">
                </c:when>
                <c:otherwise>
                    <input hidden name="method" value="PUT">
                    <label for="id">Id:</label>
                    <input id="id" readonly name="id" value="${updatingMeal.id}">
                </c:otherwise>
            </c:choose>
            <label for="datetime">Datetime:</label>
            <span><input id="datetime" name="datetime" type="datetime-local"
            <c:choose>
                <c:when test="${empty updatingMeal}">
                    <jsp:useBean id="now" class="java.util.Date"/>
                         value="<fmt:formatDate value="${now}" pattern="yyyy-MM-dd'T'HH:mm"/>"
                </c:when>
            <c:otherwise>
                         value="${updatingMeal.dateTime}"
            </c:otherwise>
            </c:choose>
                         required></span>

            <label for="description">Description:</label>
            <span><input id="description" name="description" type="text" min="4"
                         <c:set var="emptyString" value=""/>
                         value="${!empty updatingMeal ? updatingMeal.description : emptyString}" required></span>

            <label for="calories">Calories:</label>
            <span><input id="calories" name="calories" type="number" min="1" step="1"
                         value="${!empty updatingMeal ? updatingMeal.calories : 1}"
                         required></span>

            <span><input type="submit"></span>
        </fieldset>
    </form>
</div>
</body>
</html>
