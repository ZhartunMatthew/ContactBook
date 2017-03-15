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
        <div class="one-row">
            <div class="column column-1"><b> # </b></div>
            <div class="column column-2"><b> Фамилия, имя </b></div>
            <div class="column column-3"><b> Дата рождения </b></div>
            <div class="column column-4"><b> Адрес проживания </b></div>
            <div class="column column-5"><b> Место работы </b></div>
        </div>
        <c:forEach var="contact" items="${contacts}">
            <div class="one-row">
                <label for="check${contact.id}">
                    <div class="column column-1">
                        <input type="checkbox" name="contact-check" id="check${contact.id}" value="${contact.id}">
                    </div>
                    <div class="column column-2">
                        <a href="/controller?command=show_contact&contact_id=${contact.id}">
                            <c:out value=" ${contact.lastName} ${contact.firstName}"/>
                        </a>
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
                </label>
            </div>
        </c:forEach>

    </body>
</html>
