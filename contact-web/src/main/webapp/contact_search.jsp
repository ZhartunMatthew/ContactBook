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
                <input id="last-name" type="text" name="last-name" tabindex="1">
            </label>
            <label> Имя
                <input id="first-name" type="text" name="first-name" tabindex="2">
            </label>
            <label> Отчество
                <input id="patronymic" type="text" name="patronymic" tabindex="3">
            </label>
            <label> Дата рождения с
                <div class="date-filed">
                    <input id="from-year" class="date date-year" type="text" name="from-birth-date-year" placeholder="Год" tabindex="6">
                </div>
                <div class="date-filed">
                    <input id="from-month" class="date date-month" type="text" name="from-birth-date-month" placeholder="Мес." tabindex="5">
                </div>
                <div class="date-filed">
                    <input id="from-day" class="date date-day" type="text" name="from-birth-date-day" placeholder="День" tabindex="4">
                </div>
            </label>
            <label> Дата рождения до
                <div class="date-filed">
                    <input id="to-year" class="date date-year" type="text" name="to-birth-date-year" placeholder="Год" tabindex="9">
                </div>
                <div class="date-filed">
                    <input id="to-month" class="date date-month" type="text" name="to-birth-date-month" placeholder="Мес." tabindex="8">
                </div>
                <div class="date-filed">
                    <input id="to-day" class="date date-day" type="text" name="to-birth-date-day" placeholder="День" tabindex="7">
                </div>
            </label>
            <label> Пол
                <select id="sex" name="sex" tabindex="10">
                    <option selected value="X"> Не выбран </option>
                    <option value="M"> Мужчина </option>
                    <option value="F"> Женщина </option>
                </select>
            </label>
            <label> Гражданство
                <select id="nationality" name="nationality" tabindex="11">
                    <option selected value="0"> Не выбрано </option>
                    <c:forEach var="nationality" items="${nationalities}">
                        <option ${contact.nationality == nationality.id ? 'selected' : ''} value="${nationality.id}">
                                <c:out value="${nationality.name}"/>
                        </option>
                    </c:forEach>
                </select>
            </label>
            <label> Семейное положение
                <select id="marital-status" name="marital-status" tabindex="12">
                    <option selected value="0"> Не выбрано </option>
                    <c:forEach var="maritalStatus" items="${martialStatuses}">
                        <option ${contact.maritalStatus == maritalStatus.id ? 'selected' : ''} value="${maritalStatus.id}">
                                <c:out value="${maritalStatus.name}"/>
                        </option>
                    </c:forEach>
                </select>
            </label>

            <h3>Адрес</h3>
            <label> Индекс
                <input id="postcode" name="postcode" type="text" tabindex="13">
            </label>
            <label> Страна
                <select id="country" name="country" tabindex="14">
                    <option selected value="0"> Не выбрано </option>
                    <c:forEach var="country" items="${countries}">
                        <option ${contact.country == country.id ? 'selected' : ''} value="${country.id}">
                                <c:out value="${country.name}"/>
                        </option>
                    </c:forEach>
                </select>
            </label>
            <label> Город
                <input id="city" name="city" type="text" tabindex="15">
            </label>
            <label> Улица
                <input id="street" name="street" type="text" tabindex="16">
            </label>
            <label> Дом
                <input id="house" name="house-number" type="text" tabindex="17">
            </label>
            <label> Квартира
                <input id="flat" name="flat" type="text" tabindex="18">
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
