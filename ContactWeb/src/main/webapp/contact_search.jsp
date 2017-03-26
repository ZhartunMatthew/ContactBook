<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
    <head>
        <title> Поиск контактов </title>
    </head>
    <body>
        <form method="post" action="/controller?command=search_contacts">
            <h3> Введите данные о контакте </h3>
            <label> Фамилия
                <input type="text" name="last-name">
            </label>
            <label> Имя
                <input type="text" name="first-name">
            </label>
            <label> Отчество
                <input type="text" name="patronymic">
            </label>
            <label> Дата рождения

            </label>
            <label> Пол
                <select name="sex">
                    <option selected value="X"> Не выбран </option>
                    <option value="M"> Мужчина </option>
                    <option value="F"> Женщина </option>
                </select>
            </label>
            <label> Гражданство
                <select name="nationality">
                    <option selected value="0"> Не выбрано </option>
                    <c:forEach var="nationality" items="${nationalities}">
                        <option ${contact.nationality == nationality.id ? 'selected' : ''} value="${nationality.id}">
                                ${nationality.name}
                        </option>
                    </c:forEach>
                </select>
            </label>
            <label> Семейное положение
                <select name="marital-status">
                    <option selected value="0"> Не выбрано </option>
                    <c:forEach var="maritalStatus" items="${martialStatuses}">
                        <option ${contact.maritalStatus == maritalStatus.id ? 'selected' : ''} value="${maritalStatus.id}">
                                ${maritalStatus.name}
                        </option>
                    </c:forEach>
                </select>
            </label>

            <h3>Адрес</h3>
            <label> Страна
                <select name="country">
                    <option selected} value="0"> Не выбрано </option>
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
            <input type="submit" value="SUBMIT">
        </form>
        <link rel="stylesheet" type="text/css" href="style/search-page.css">
    </body>
</html>