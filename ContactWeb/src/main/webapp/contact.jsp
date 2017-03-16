<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Контакт</title>
</head>
<body>
    <br> Это страница контакта <c:out value="${contact.id}"/> <br>
    <br> Это страница контакта <c:out value="${contact.firstName}"/> <br>
    <br> Это страница контакта <c:out value="${contact.lastName}"/> <br>
    <br> Это страница контакта <c:out value="${contact.job}"/> <br>
</body>
</html>
