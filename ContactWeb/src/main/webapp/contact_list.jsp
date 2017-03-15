<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Список контактов </title>
    <link rel="stylesheet" type="text/css" href="style/list_style.css">
</head>
    <body>
        <div>
            <p> Control buttons </p>
        </div>
        <c:forEach var="contact" items="${contacts}">
            <div class="one-row">
                <div class="column column-1">
                    <label>
                        <input type="checkbox" name="contact-check" value="${contact.id}">
                    </label>
                </div>
                <div class="column column-2">
                    <a href="link_to_edit_contact"> <c:out value="${contact.firstName} ${contact.lastName}"/> </a>
                </div>
                <div class="column column-3">
                    <c:out value="${contact.birthDate}"/>
                </div>
                <div class="column column-4">
                    <c:out value="${contact.country} г.
                                  ${contact.city} ул.
                                  ${contact.street} д.
                                  ${contact.houseNumber} кв.
                                  ${contact.flat}"/>
                </div>
                <div class="column column-5">
                    <c:out value="${contact.job}"/>
                </div>
            </div>
        </c:forEach>

    </body>
</html>
