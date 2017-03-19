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

            <div class="nav-buttons">
                <a class="nav-button add" id="add-phone-button"></a>
                <a class="nav-button delete" id="delete-phone-button"></a>
                <a class="nav-button edit" id="edit-checked-phone-button"></a>
            </div>
            <h3>Контактные телефоны</h3>
            <div class="first-row">
                <div class="column column-2"> Выбрать </div>
                <div class="column column-3"> Номер телефона </div>
                <div class="column column-3"> Описание номера </div>
                <div class="column column-x"> Комментарий </div>
            </div>
            <c:forEach var="phone" items="${contact.phones}">
                <div class="one-row">
                    <label for="phone-check-${phone.phoneID}">
                        <div class="column column-2">
                            <input type="checkbox" name="phone-check" id="phone-check-${phone.phoneID}" value="${phone.phoneID}">
                        </div>
                        <div class="column column-3">
                            <c:out value="+${phone.countryCode} (${phone.operatorCode}) ${phone.number}"/>
                        </div>
                        <div class="column column-3">
                            ${phone.type}
                        </div>
                        <div class="column column-x">
                            ${phone.comment}
                        </div>
                    </label>
                </div>
            </c:forEach>

            <div class="nav-buttons">
                <a class="nav-button add" id="add-attachment-button"></a>
                <a class="nav-button delete" id="delete-attachment-button"></a>
                <a class="nav-button edit" id="edit-attachment-button"></a>
            </div>
            <h3>Прикрепленные файлы</h3>
            <div class="first-row">
                <div class="column column-2"> Выбрать </div>
                <div class="column column-3"> Имя файла </div>
                <div class="column column-3"> Дата загрузки </div>
                <div class="column column-x"> Комментарий </div>
            </div>
            <c:forEach var="attachment" items="${contact.attachments}">
                <div class="one-row">
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
        </form>
        <link rel="stylesheet" type="text/css" href="style/column-style.css">
        <link rel="stylesheet" type="text/css" href="style/contact-page-style.css">
        <link rel="stylesheet" type="text/css" href="style/common-style.css">
        <link rel="stylesheet" type="text/css" href="style/button-style.css">
    </body>
</html>