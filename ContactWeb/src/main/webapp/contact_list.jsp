<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Список контактов </title>
</head>
    <body>
        <div>
            <p> Control buttons </p>
        </div>
        <c:forEach var="contact" items="${contacts}">
            <div class="one-row">
                <div class="cell cell-1">
                    <label>
                        <input type="checkbox" name="contact-check" value="${contact.id}">
                    </label>
                </div>
                <div class="cell cell-2">
                    <a href="link_to_edit_contact"> <c:out value="${contact.firstName} ${contact.lastName}"/> </a>
                </div>
                <div class="cell cell-3">
                    <c:out value="${contact.birthDate}"/>
                </div>
                <div class="cell cell-4">
                    <c:out value="${contact.country} ${contact.city} ${contact.street} ${contact.houseNumber}"/>
                </div>
                <div class="cell cell-5">
                    <c:out value="${contact.job}"/>
                </div>
            </div>
        </c:forEach>
        <link rel="stylesheet" type="text/css" href="style/list_style.css">
    </body>
</html>
