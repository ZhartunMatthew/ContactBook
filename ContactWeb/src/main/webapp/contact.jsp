<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty contact}">
    <c:set var="titleName" value="${contact.lastName} ${contact.firstName}"/>
    <c:set var="actionOnSubmit" value="/controller?command=update_contact&id=${contact.id}"/>
</c:if>
<c:if test="${empty contact}">
    <c:set var="titleName" value="Создание контакта"/>
    <c:set var="actionOnSubmit" value="/controller?command=add_contact"/>
</c:if>
<html>
    <head>
        <title> <c:out value="${titleName}"/> </title>
    </head>
    <body>
        <form class="contact" method="post" action="${actionOnSubmit}">
            <input type="submit" value="SAVE CHANGES">
            <div class="main-info">
                <h3>Основная информация</h3>
                <label> Фамилия
                    <input type="text" name="last-name" value="${contact.lastName}">
                </label>
                <label> Имя
                    <input type="text" value="${contact.firstName}">
                </label>
                <label> Отчество
                    <input type="text" value="${contact.patronymic}">
                </label>
                <label> Дата рождения
                    <input type="text" value="${contact.birthDate}">
                </label>
                <label> Пол
                    <input type="text" value="${contact.sex}">
                </label>
                <label> Гражданство
                    <input type="text" value="${contact.nationality}">
                </label>
                <label> Семейное положение
                    <input type="text" value="${contact.maritalStatus}">
                </label>
                <label> Website
                    <input type="text" value="${contact.website}">
                </label>
                <label> Email
                    <input type="text" value="${contact.email}">
                </label>
                <label> Текущее место работы
                    <input type="text" value="${contact.job}">
                </label>

                <h3>Адрес</h3>
                <label> Страна
                    <input type="text" value="${contact.country}">
                </label>
                <label> Город
                    <input type="text" value="${contact.city}">
                </label>
                <label> Улица
                    <input type="text" value="${contact.street}">
                </label>
                <label> Дом
                    <input type="text" value="${contact.houseNumber}">
                </label>
                <label> Квартира
                    <input type="text" value="${contact.flat}">
                </label>
            </div>

            <%--TODO: action on buttons--%>
            <div class="nav-buttons">
                <div class="nav-button add" id="add-phone-button"></div>
                <div class="nav-button delete" id="delete-phone-button"></div>
                <div class="nav-button edit" id="edit-checked-phone-button"></div>
            </div>
            <h3>Контактные телефоны</h3>
            <div class="first-row">
                <div class="column column-2"> Выбрать </div>
                <div class="column column-3"> Номер телефона </div>
                <div class="column column-3"> Описание номера </div>
                <div class="column column-x"> Комментарий </div>
            </div>
            <div id="contact-phones">
                <c:forEach var="phone" items="${contact.phones}">
                    <div class="one-row" id="contact-phone-${phone.phoneID}">
                        <label for="phone-check-${phone.phoneID}">
                            <div class="column column-2">
                                <input type="checkbox" name="phone-check" id="phone-check-${phone.phoneID}" value="${phone.phoneID}">
                            </div>
                            <div class="column column-3" id="contact-phone-number-${phone.phoneID}">
                                <c:out value="+${phone.countryCode} (${phone.operatorCode}) ${phone.number}"/>
                            </div>
                            <div class="column column-3" id="contact-phone-type-${phone.phoneID}">
                                ${phone.type}
                            </div>
                            <div class="column column-x" id="contact-phone-comment-${phone.phoneID}">
                                ${phone.comment}
                            </div>
                        </label>
                    </div>
                </c:forEach>
            </div>

            <div class="nav-buttons">
                <div class="nav-button add" id="add-attachment-button"></div>
                <div class="nav-button delete" id="delete-attachment-button"></div>
                <div class="nav-button edit" id="edit-attachment-button"></div>
            </div>
            <h3>Прикрепленные файлы</h3>
            <div class="first-row">
                <div class="column column-2"> Выбрать </div>
                <div class="column column-3"> Имя файла </div>
                <div class="column column-3"> Дата загрузки </div>
                <div class="column column-x"> Комментарий </div>
            </div>
            <div id="contact-attachments">
                <c:forEach var="attachment" items="${contact.attachments}">
                    <div class="one-row" id="contact-attachment-${attachment.fileID}">
                        <label for="attachment-check-${attachment.fileID}">
                            <div class="column column-2">
                                <input type="checkbox" name="attachment-check" id="attachment-check-${attachment.fileID}" value="${attachment.fileID}">
                            </div>
                            <div class="column column-3">
                                <c:out value="${attachment.filePath}"/>
                            </div>
                            <div class="column column-3">
                                <c:out value="${attachment.uploadDate}"/>
                            </div>
                            <div class="column column-x">
                                <c:out value="${attachment.comment}"/>
                            </div>
                        </label>
                    </div>
                </c:forEach>
            </div>
        </form>
        <div id="popup-window-phone" class="popup-window">
            <div class="popup-content">
                <div class="popup-name">
                    Подменю телефонов
                </div>
                <label> Код страны
                    <input type="text" id="country-code">
                </label>
                <label> Код оператора
                    <input type="text" id="operator-code">
                </label>
                <label> Номер
                    <input type="text" id="phone-number">
                </label>
                <label> Тип
                    <input type="text" id="phone-type">
                </label>
                <label> Коментарий
                    <input type="text" id="phone-comment">
                </label>
                <div class="popup-buttons">
                    <div id="popup-submit-phone-button" class="popup-button"> Применить </div>
                    <div id="popup-cancel-phone-button" class="popup-button "> Отмена </div>
                </div>
            </div>
        </div>

        <%--TODO: modal window for attachments--%>
        <div id="popup-window-attachment">
            <div class="popup-window">
                <div class="popup-content">
                    <p>Подменю файлов</p>
                </div>
            </div>
        </div>
        <link rel="stylesheet" type="text/css" href="style/column-style.css">
        <link rel="stylesheet" type="text/css" href="style/contact-page-style.css">
        <link rel="stylesheet" type="text/css" href="style/common-style.css">
        <link rel="stylesheet" type="text/css" href="style/button-style.css">
        <script src="/scripts/contact-page-scripts.js"></script>
    </body>
</html>