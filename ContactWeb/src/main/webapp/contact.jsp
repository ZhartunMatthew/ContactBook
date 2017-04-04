<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<c:if test="${not empty contact}">
    <c:set var="titleName" value="${contact.lastName} ${contact.firstName}"/>
    <c:set var="actionOnSubmit" value="${pageContext.request.contextPath}/controller?command=update_contact&id=${contact.id}"/>
    <c:if test="${not empty contactPhoto}">
        <c:set var="photoPath" value="${pageContext.request.contextPath}/controller?command=get_image&name=${contactPhoto}"/>
    </c:if>
    <c:if test="${empty contactPhoto}">
        <c:set var="photoPath" value="image/default.png"/>
    </c:if>
    <c:set var="contactID" value="${contact.id}"/>
</c:if>
<c:if test="${empty contact}">
    <c:set var="titleName" value="Создание контакта"/>
    <c:set var="actionOnSubmit" value="${pageContext.request.contextPath}/controller?command=add_contact"/>
    <c:set var="photoPath" value="image/default.png"/>
    <c:set var="contactID" value="${null}"/>
</c:if>
<html>
    <head>
        <title> <c:out value="${titleName}"/> </title>
        <link rel="stylesheet" type="text/css" href="style/column-style.css">
        <link rel="stylesheet" type="text/css" href="style/contact-page-style.css">
        <link rel="stylesheet" type="text/css" href="style/popup-style.css">
        <link rel="stylesheet" type="text/css" href="style/button-style.css">
    </head>
    <body>
        <form id="contact-form" class="contact" method="post" action="${actionOnSubmit}" enctype="multipart/form-data">
            <input type="hidden" name="id" value="${contactID}">
            <div class="nav-buttons">
                <a title="Сохранить контакт" class="nav-button save" id="submit-contact-button"></a>
                <a title="Вернуться к списку" class="nav-button home" href="${pageContext.request.contextPath}/controller"></a>
            </div>
            <div class="main-info">
                <h2>Основная информация</h2>
                <div title="Изменить фото" id="contact-photo" class="contact-photo-area">
                    <img src="${photoPath}" id="contact-photo-image" class="contact-photo-image">
                    <input type="file" class="hidden" id="uploaded-contact-photo">
                </div>
                <input id="old-contact-photo" type="hidden" name="photo-path" value="${contact.photoPath}">
                <label> Фамилия
                    <input id="last-name" type="text" name="last-name" value="${contact.lastName}">
                </label>
                <label> Имя
                    <input id="first-name" type="text" name="first-name" value="${contact.firstName}">
                </label>
                <label> Отчество
                    <input id="patronymic" type="text" name="patronymic" value="${contact.patronymic}">
                </label>
                <label> Дата рождения
                    <div class="date-filed">
                        <input id="year" class="date date-year" type="text" name="birth-date-year" placeholder="Год" value="${year}">
                    </div>
                    <div class="date-filed">
                        <input id="month" class="date date-month" type="text" name="birth-date-month" placeholder="Мес." value="${month}">
                    </div>
                    <div class="date-filed">
                        <input id="day" class="date date-day" type="text" name="birth-date-day" placeholder="День" value="${day}">
                    </div>
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
                    <input id="website" name="website" type="text" value="${contact.website}">
                </label>
                <label> Email
                    <input id="email" name="email" type="text" value="${contact.email}">
                </label>
                <label> Текущее место работы
                    <input id="job" name="job" type="text" value="${contact.job}">
                </label>

                <h3>Адрес</h3>
                <label> Индекс
                    <input id="postcode" name="postcode" type="text" value="${contact.postCode}">
                </label>
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
                    <input id="city" name="city" type="text" value="${contact.city}">
                </label>
                <label> Улица
                    <input id="street" name="street" type="text" value="${contact.street}">
                </label>
                <label> Дом
                    <input id="house" name="house-number" type="text" value="${contact.houseNumber}">
                </label>
                <label> Квартира
                    <input id="flat" name="flat" type="text" value="${contact.flat}">
                </label>
            </div>

            <div class="nav-buttons">
                <div title="Добавить телефон" class="nav-button add" id="add-phone-button"></div>
                <div title="Удалить отмеченные телефоны" class="nav-button delete" id="delete-phone-button"></div>
                <div title="Редактировать отмеченный телефон" class="nav-button edit" id="edit-checked-phone-button"></div>
            </div>
            <h3>Контактные телефоны</h3>
            <div class="first-row">
                <div class="column column-1"> # </div>
                <div class="column column-4"> Номер телефона </div>
                <div class="column column-4"> Описание номера </div>
                <div class="column column-5"> Комментарий </div>
            </div>
            <div id="contact-phones">
                <c:forEach var="phone" items="${contact.phones}">
                    <div class="one-row contact-phone" id="contact-phone-${phone.id}">
                        <label for="phone-check-${phone.id}">
                            <div class="column column-1">
                                <input type="checkbox" name="phone-check" id="phone-check-${phone.id}" value="${phone.id}">
                            </div>
                            <div class="column column-4" id="contact-phone-number-${phone.id}">
                                <c:out value="+${phone.countryCode} (${phone.operatorCode}) ${phone.number}"/>
                            </div>
                            <div class="column column-4" id="contact-phone-type-${phone.id}">
                                ${phone.type == 1 ? 'Домашний' : 'Мобильный'}
                            </div>
                            <div class="column column-5" id="contact-phone-comment-${phone.id}">
                                ${phone.comment}
                            </div>
                        </label>
                    </div>
                </c:forEach>
            </div>

            <div class="nav-buttons">
                <div title="Добавить файл" id="add-attachment-button" class="nav-button add"></div>
                <div title="Удалить отмеченные файлы" id="delete-attachment-button" class="nav-button delete"></div>
                <div title="Редактировать отмеченный файл" id="edit-checked-attachment-button" class="nav-button edit"></div>
            </div>
            <h3>Прикрепленные файлы</h3>
            <div class="first-row">
                <div class="column column-1"> # </div>
                <div class="column column-4"> Имя файла </div>
                <div class="column column-4"> Дата загрузки </div>
                <div class="column column-5"> Комментарий </div>
            </div>
            <div id="contact-attachments">
                <c:forEach var="attachment" items="${contact.attachments}">
                    <div class="one-row contact-attachment" id="contact-attachment-${attachment.id}">
                        <label for="attachment-check-${attachment.id}">
                            <div class="column column-1">
                                <input type="checkbox" name="attachment-check" id="attachment-check-${attachment.id}" value="${attachment.id}">
                            </div>
                            <div class="column column-4" id="contact-attachment-file-name-${attachment.id}">
                                <c:out value="${attachment.fileName}"/>
                            </div>
                            <div class="column column-4" id="contact-attachment-upload-date-${attachment.id}">
                                <c:out value="${attachment.getDateString()}"/>
                            </div>
                            <div class="column column-5" id="contact-attachment-comment-${attachment.id}">
                                <c:out value="${attachment.comment}"/>
                            </div>
                            <a href="${pageContext.request.contextPath}/controller?command=download_attachment&id=${attachment.id}">
                                <div class="nav-button download"></div>
                            </a>
                        </label>
                    </div>
                </c:forEach>
            </div>
            <div id="attachment-input-field">

            </div>
        </form>

        <div id="popup-window-photo" class="popup-window">
            <div class="popup-content">
                <div class="popup-name"> Выбор фото
                </div>
                <label id="popup-photo-label"> Путь к фото
                    <input type="file" id="photo-file-input" value="Выберите фото" accept="image/jpeg,image/png">
                </label>
                <div class="popup-buttons">
                    <div id="save-photo-button" class="popup-button photo-popup-button accept">Применить</div>
                    <div id="cancel-photo-button" class="popup-button photo-popup-button cancel">Отменить</div>
                    <div id="delete-photo-button" class="popup-button photo-popup-button remove">Удалить</div>
                </div>
            </div>
        </div>

        <div id="popup-window-phone" class="popup-window">
            <div class="popup-content">
                <div class="popup-name"> Подменю телефонов
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
                    <div id="popup-submit-phone-button" class="popup-button accept"> Применить </div>
                    <div id="popup-cancel-phone-button" class="popup-button cancel"> Отмена </div>
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
                <label> Имя
                    <input type="text" class="modal-attachment-field" id="attachment-name">
                </label>
                <label> Комментарий
                    <input type="text" class="modal-attachment-field" id="attachment-comment">
                </label>
                <div class="popup-buttons">
                    <div id="popup-submit-attachment-button" class="popup-button accept"> Применить </div>
                    <div id="popup-cancel-attachment-button" class="popup-button cancel"> Отмена </div>
                </div>
            </div>
        </div>

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
        <script src="scripts/contact-page-scripts.js"></script>
    </body>
</html>