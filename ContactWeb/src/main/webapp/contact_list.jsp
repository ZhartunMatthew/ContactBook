<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Список контактов </title>
    <link rel="stylesheet" type="text/css" href="style/column-style.css">
    <link rel="stylesheet" type="text/css" href="style/button-style.css">
</head>
    <body>
        <div>
            <%--TODO: pagination--%>
        </div>
        <div class="nav-buttons">
            <a class="nav-button add-contact-button" href="/controller?command=add_contact"> </a>
            <a class="nav-button delete-contact-button" href="/controller?command=delete_contact"></a>
            <a class="nav-button search-contact-button" href="/controller?command=search_contact"></a>
            <a class="nav-button send-mail-contact-button" href="/controller?command=send_mail"></a>
        </div>
        <div class="first-row">
            <div class="column column-1"><b> # </b></div>
            <div class="column column-3"><b> Фамилия, имя </b></div>
            <div class="column column-3"><b> Дата рождения </b></div>
            <div class="column column-4"><b> Адрес проживания </b></div>
            <div class="column column-x"><b> Место работы </b></div>
        </div>
        <c:forEach var="contact" items="${contacts}">
            <div class="one-row">
                <label for="check-${contact.id}">
                    <div class="column column-1">
                        <input type="checkbox" name="contact-check" id="check-${contact.id}" value="${contact.id}">
                    </div>
                    <div class="column column-3">
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
                    <div class="column column-x">
                        <c:out value="${contact.job}"/>
                    </div>
                </label>
            </div>
        </c:forEach>

    </body>
</html>
