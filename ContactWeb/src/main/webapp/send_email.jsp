<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title> Отправка сообщений </title>
</head>
<body>
    <form id="send-mail-form" method="post" action="controller?command=send_email">
        <h1> Отправка email </h1>
        <h3> Получатели </h3>
        <c:forEach var="recipient" items="${recipients}">
            <label> ${recipient.firstName} ${recipient.lastName}
                <input type="text" disabled value="${recipient.email}">
            </label>
            <input type="hidden" value="${recipient.id}" name="recipient-id">
        </c:forEach>
        <div>
            <h4> Параметры сообщения </h4>
            <label> Тема сообщения
                <input id="email-subject" name="email-subject" type="text" >
            </label>
            <label> Текст сообщения
                <textarea id="email-text" name="email-text" cols="50" rows="7"></textarea>
            </label>
        </div>
        <div class="send-mail-buttons">
            <a class="send-mail-button" id="send-mail-button"> Отправить </a>
            <a class="send-mail-button" href="controller?command=show_contact_list&page=1" id="return-home"> Домой </a>
        </div>
    </form>
    <link rel="stylesheet" type="text/css" href="style/form-style.css">
    <link rel="stylesheet" type="text/css" href="style/send-mail-page.css">
    <script src="scripts/send-mail-script.js"></script>
</body>
</html>
