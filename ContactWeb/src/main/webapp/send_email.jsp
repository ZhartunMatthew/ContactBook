<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title> Отправка сообщений </title>
    <link rel="stylesheet" type="text/css" href="style/common-style.css">
    <link rel="stylesheet" type="text/css" href="style/form-style.css">
    <link rel="stylesheet" type="text/css" href="style/send-mail-page.css">
    <link rel="stylesheet" type="text/css" href="style/popup-style.css">
</head>
<body>
    <form id="send-mail-form" method="post" action="${pageContext.request.contextPath}/controller?command=send_email">
        <h2> Отправка email </h2>
        <h3> Получатели </h3>
        <div class="recipients-block">
            <c:choose>
                <c:when test="${recipientsCount > 0}">
                    <c:forEach var="recipient" items="${recipients}">
                        <label> ${recipient.firstName} ${recipient.lastName}
                            <input class="recipient-email" type="text" disabled value="${recipient.email}">
                        </label>
                        <input type="hidden" value="${recipient.id}" name="recipient-id">
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <p class="no-recipients"> Ошибка! </p>
                    <p class="no-recipients"> У выбранных контатов не указан email </p>
                    <p class="no-recipients"> Отправка писем невозможна. </p>
                </c:otherwise>
            </c:choose>
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
                <a class="send-mail-button" href="${pageContext.request.contextPath}/controller" id="return-home"> Домой </a>
            </div>
        </div>
    </form>
    <div id="popup-window-error" class="popup-window error-window">
        <div class="popup-content">
            <div class="popup-name">
                Некорректный ввод
            </div>
            <div id="error-message"></div>
            <div class="popup-buttons">
                <div id="popup-window-error-accept" class="popup-button accept"> Принять </div>
            </div>
        </div>
    </div>
    <script src="scripts/send-mail-script.js"></script>
</body>
</html>
