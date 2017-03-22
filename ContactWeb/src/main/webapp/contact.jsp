<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<c:if test="${not empty contact}">
    <c:set var="titleName" value="${contact.lastName} ${contact.firstName}"/>
    <c:set var="actionOnSubmit" value="/controller?command=update_contact&id=${contact.id}"/>
    <c:set var="photoPath" value="/image/${not empty contact.photoPath ? contact.photoPath : 'default.png'}"/>
</c:if>
<c:if test="${empty contact}">
    <c:set var="titleName" value="Создание контакта"/>
    <c:set var="actionOnSubmit" value="/controller?command=add_contact"/>
    <c:set var="photoPath" value="/image/default.png"/>
</c:if>
<html>
    <head>
        <title> <c:out value="${titleName}"/> </title>
    </head>
    <body>
        <form id="contact-form" class="contact" method="post" action="${actionOnSubmit}" enctype="multipart/form-data">
            <input type="submit" value="SAVE CHANGES" id="submit-contact-button">
            <div class="main-info">
                <h3>Основная информация</h3>
                <div id="contact-photo" class="contact-photo-area">
                    <img src="${photoPath}" class="contact-photo-image">
                </div>
                <label> Фамилия
                    <input type="text" name="last-name" value="${contact.lastName}">
                </label>
                <label> Имя
                    <input type="text" name="first-name" value="${contact.firstName}">
                </label>
                <label> Отчество
                    <input type="text" name="patronymic" value="${contact.patronymic}">
                </label>
                <label> Дата рождения
                    <input type="text" name="birth-date" value="${contact.birthDate}">
                </label>
                <label> Пол
                    <select name="sex">
                        <option ${empty contact.sex ? 'selected' : ''} value="X"> Не выбран </option>
                        <option ${contact.sex == 'M' ? 'selected' : ''} value="M"> Мужчина </option>
                        <option ${contact.sex == 'F' ? 'selected' : ''} value="F"> Женщина </option>
                    </select>
                </label>
                <label> Гражданство
                    <select name="nationality">
                        <option ${empty contact.nationality ? 'selected' : ''} value="0"> Не выбрано </option>
                        <c:forEach var="nationality" items="${nationalities}">
                            <option ${contact.nationality == nationality.id ? 'selected' : ''} value="${nationality.id}">
                                    ${nationality.name}
                            </option>
                        </c:forEach>
                    </select>
                </label>
                <label> Семейное положение
                    <select name="marital-status">
                        <option ${empty contact.maritalStatus ? 'selected' : ''} value="0"> Не выбрано </option>
                        <c:forEach var="maritalStatus" items="${martialStatuses}">
                            <option ${contact.maritalStatus == maritalStatus.id ? 'selected' : ''} value="${maritalStatus.id}">
                                    ${maritalStatus.name}
                            </option>
                        </c:forEach>
                    </select>
                </label>
                <label> Website
                    <input name="website" type="text" value="${contact.website}">
                </label>
                <label> Email
                    <input name="email" type="text" value="${contact.email}">
                </label>
                <label> Текущее место работы
                    <input name="job" type="text" value="${contact.job}">
                </label>

                <h3>Адрес</h3>
                <label> Страна
                    <select name="country">
                        <option ${empty contact.country ? 'selected' : ''} value="0"> Не выбрано </option>
                        <c:forEach var="country" items="${countries}">
                            <option ${contact.country == country.id ? 'selected' : ''} value="${country.id}">
                                    ${country.name}
                            </option>
                        </c:forEach>
                    </select>
                </label>
                <label> Город
                    <input name="city" type="text" value="${contact.city}">
                </label>
                <label> Улица
                    <input name="street" type="text" value="${contact.street}">
                </label>
                <label> Дом
                    <input name="house-number" type="text" value="${contact.houseNumber}">
                </label>
                <label> Квартира
                    <input name="flat" type="text" value="${contact.flat}">
                </label>
            </div>

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
                    <div class="one-row contact-phone" id="contact-phone-${phone.phoneID}">
                        <label for="phone-check-${phone.phoneID}">
                            <div class="column column-2">
                                <input type="checkbox" name="phone-check" id="phone-check-${phone.phoneID}" value="${phone.phoneID}">
                            </div>
                            <div class="column column-3" id="contact-phone-number-${phone.phoneID}">
                                <c:out value="+${phone.countryCode} (${phone.operatorCode}) ${phone.number}"/>
                            </div>
                            <div class="column column-3" id="contact-phone-type-${phone.phoneID}">
                                ${phone.type == 1 ? 'Домашний' : 'Мобильный'}
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
                <div class="nav-button edit" id="edit-checked-attachment-button"></div>
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
                    <div class="one-row contact-attachment" id="contact-attachment-${attachment.fileID}">
                        <label for="attachment-check-${attachment.fileID}">
                            <div class="column column-2">
                                <input type="checkbox" name="attachment-check" id="attachment-check-${attachment.fileID}" value="${attachment.fileID}">
                            </div>
                            <div class="column column-3" id="contact-attachment-file-path-${attachment.fileID}">
                                <c:out value="${attachment.filePath}"/>
                            </div>
                            <div class="column column-3" id="contact-attachment-upload-date-${attachment.fileID}">
                                <c:out value="${attachment.uploadDate}"/>
                            </div>
                            <div class="column column-x" id="contact-attachment-comment-${attachment.fileID}">
                                <c:out value="${attachment.comment}"/>
                            </div>
                        </label>
                    </div>
                </c:forEach>
            </div>
            <div id="attachment-input-field">

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
                    <select id="phone-type">
                        <option value="1">Домашний</option>
                        <option value="2" selected>Мобильный</option>
                    </select>
                </label>
                <label> Коментарий
                    <input type="text" id="phone-comment">
                </label>
                <div class="popup-buttons">
                    <div id="popup-submit-phone-button" class="popup-button"> Применить </div>
                    <div id="popup-cancel-phone-button" class="popup-button"> Отмена </div>
                </div>
            </div>
        </div>

        <div id="popup-window-attachment" class="popup-window">
            <div class="popup-content">
                <div class="popup-name">
                    Подменю прикрепленных файлов
                </div>
                <label id="path-to-file"> Путь к файлу
                    <input type="file" id="attachment-path">
                </label>
                <input type="hidden" id="attachment-upload-date">
                <label> Комментарий
                    <input type="text" class="modal-attachment-comment" id="attachment-comment">
                </label>
                <div class="popup-buttons">
                    <div id="popup-submit-attachment-button" class="popup-button"> Применить </div>
                    <div id="popup-cancel-attachment-button" class="popup-button"> Отмена </div>
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