<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title> Отправка сообщений </title>
</head>
<body>
    <form id="send-mail-form" method="post" action="controller?command=send_email">
        <h2> Отправка email </h2>
        <h3> Получатели </h3>
        <div class="recipients-block">
        <c:forEach var="recipient" items="${recipients}">
            <label> ${recipient.firstName} ${recipient.lastName}
                <input class="recipient-email" type="text" disabled value="${recipient.email}">
            </label>
            <input type="hidden" value="${recipient.id}" name="recipient-id">
        </c:forEach>
        </div>
        <div>
            <h3> Параметры сообщения </h3>
            <div class="email-subject-block">
                <label> Тема сообщения
                    <input class="subject-input" id="email-subject" name="email-subject" type="text">
                </label>
            </div>
            <div class="email-template-block">
                <label> Шаблон
                    <select class="email-template-select" id="template-select" name="template-input">
                        <option value=""> Без шаблона </option>
                        <c:forEach var="item" items="${templates}" varStatus="loop">
                            <option value="${item}"> Шаблон №${loop.index + 1} </option>
                        </c:forEach>
                    </select>
                </label>
            </div>
            <div class="email-text-block">
                <label> Текст сообщения
                    <textarea class="email-input" id="email-text" name="email-text" rows="7"></textarea>
                </label>
            </div>
        </div>
        <div class="send-mail-buttons">
            <div class="button-block">
                <a class="send-mail-button" id="send-mail-button"> Отправить </a>
                <a class="send-mail-button" href="controller?command=show_contact_list&page=1" id="return-home"> Домой </a>
            </div>
        </div>
    </form>
    <link rel="stylesheet" type="text/css" href="style/form-style.css">
    <link rel="stylesheet" type="text/css" href="style/send-mail-page.css">
    <script src="scripts/send-mail-script.js"></script>
</body>
</html>
