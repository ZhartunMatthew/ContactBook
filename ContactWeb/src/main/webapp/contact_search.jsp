<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
    <head>
        <title> Поиск контактов </title>
        <link rel="stylesheet" type="text/css" href="style/form-style.css">
        <link rel="stylesheet" type="text/css" href="style/search-page.css">
        <link rel="stylesheet" type="text/css" href="style/popup-style.css">
    </head>
    <body>
        <form id="contact-search-form" method="post" action="${pageContext.request.contextPath}/controller?command=search_contacts">
            <h3> Введите данные о контакте </h3>
            <label> Фамилия
                <input id="last-name" type="text" name="last-name">
            </label>
            <label> Имя
                <input id="first-name" type="text" name="first-name">
            </label>
            <label> Отчество
                <input id="patronymic" type="text" name="patronymic">
            </label>
            <label> Дата 'от'
                <div class="date-filed">
                    <input id="from-year" class="date date-year" type="text" name="from-birth-date-year" placeholder="Год">
                </div>
                <div class="date-filed">
                    <input id="from-month" class="date date-month" type="text" name="from-birth-date-month" placeholder="Мес.">
                </div>
                <div class="date-filed">
                    <input id="from-day" class="date date-day" type="text" name="from-birth-date-day" placeholder="День">
                </div>
            </label>
            <label> Дата 'до'
                <div class="date-filed">
                    <input id="to-year" class="date date-year" type="text" name="to-birth-date-year" placeholder="Год">
                </div>
                <div class="date-filed">
                    <input id="to-month" class="date date-month" type="text" name="to-birth-date-month" placeholder="Мес.">
                </div>
                <div class="date-filed">
                    <input id="to-day" class="date date-day" type="text" name="to-birth-date-day" placeholder="День">
                </div>
            </label>
            <label> Пол
                <select id="sex" name="sex">
                    <option selected value="X"> Не выбран </option>
                    <option value="M"> Мужчина </option>
                    <option value="F"> Женщина </option>
                </select>
            </label>
            <label> Гражданство
                <select id="nationality" name="nationality">
                    <option selected value="0"> Не выбрано </option>
                    <c:forEach var="nationality" items="${nationalities}">
                        <option ${contact.nationality == nationality.id ? 'selected' : ''} value="${nationality.id}">
                                ${nationality.name}
                        </option>
                    </c:forEach>
                </select>
            </label>
            <label> Семейное положение
                <select id="marital-status" name="marital-status">
                    <option selected value="0"> Не выбрано </option>
                    <c:forEach var="maritalStatus" items="${martialStatuses}">
                        <option ${contact.maritalStatus == maritalStatus.id ? 'selected' : ''} value="${maritalStatus.id}">
                                ${maritalStatus.name}
                        </option>
                    </c:forEach>
                </select>
            </label>

            <h3>Адрес</h3>
            <label> Индекс
                <input id="postcode" name="postcode" type="text">
            </label>
            <label> Страна
                <select id="country" name="country">
                    <option selected value="0"> Не выбрано </option>
                    <c:forEach var="country" items="${countries}">
                        <option ${contact.country == country.id ? 'selected' : ''} value="${country.id}">
                                ${country.name}
                        </option>
                    </c:forEach>
                </select>
            </label>
            <label> Город
                <input id="city" name="city" type="text">
            </label>
            <label> Улица
                <input id="street" name="street" type="text">
            </label>
            <label> Дом
                <input id="house" name="house-number" type="text">
            </label>
            <label> Квартира
                <input id="flat" name="flat" type="text">
            </label>
            <div class="search-buttons">
                <a class="search-button" id="start-search"> Поиск </a>
                <a class="search-button" id="clear-search"> Очистить </a>
                <a class="search-button" href="${pageContext.request.contextPath}/controller" id="return-home"> Домой </a>
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
        <script src="scripts/contact-search-script.js"></script>
    </body>
</html>
