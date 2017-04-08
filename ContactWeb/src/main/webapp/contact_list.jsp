<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<c:choose>
    <c:when test="${param.command == 'search_contacts'}">
        <c:set var="titleName" value="Результаты поиска"/>
    </c:when>
    <c:otherwise>
        <c:set var="titleName" value="Список контактов"/>
    </c:otherwise>
</c:choose>
<html>
<head>
    <title> <c:out value="${titleName}"/> </title>
    <link rel="stylesheet" type="text/css" href="style/column-style.css">
    <link rel="stylesheet" type="text/css" href="style/button-style.css">
    <link rel="stylesheet" type="text/css" href="style/popup-style.css">
</head>
    <body>
        <div class="nav-buttons">
            <a title="Вернуться к списку" class="nav-button home" href="${pageContext.request.contextPath}/controller"> </a>
            <a title="Добавить контакт" class="nav-button add" href="${pageContext.request.contextPath}/controller?command=show_contact"> </a>
            <a title="Удалть отмеченне контакты" id="delete-contact-button" class="nav-button delete"></a>
            <a title="Поиск контактов" class="nav-button search" href="${pageContext.request.contextPath}/controller?command=show_contact_search"></a>
            <a title="Отправить email" id="show-email-page-button" class="nav-button send-mail"></a>
        </div>
        <c:if test="${not empty pagination && pagination.pageCount > 1 }">
            <div class="pagination">
                <c:set var="firstPage" value="${1}"/>
                <c:set var="lastPage" value="${pagination.pageCount}"/>
                <c:set var="activePage" value="${pagination.activePage}"/>
                <ul>
                    <li>
                        <a href="${pageContext.request.contextPath}/controller?command=show_contact_list&page=${firstPage}">
                            <div class="page-n first-page">
                                <<
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/controller?command=show_contact_list&page=${activePage <= 1 ? 1 : activePage - 1}">
                            <div class="page-n prev-page">
                                <
                            </div>
                        </a>
                    </li>
                    <c:forEach var="number" begin="${firstPage}" end="${activePage-1}">
                        <li>
                            <a href="${pageContext.request.contextPath}/controller?command=show_contact_list&page=${number}">
                                <div class="page-n">
                                    <c:out value="${number}"/>
                                </div>
                            </a>
                        </li>
                    </c:forEach>
                    <li>
                        <a href="${pageContext.request.contextPath}/controller?command=show_contact_list&page=${activePage}">
                            <div class="page-n active-page">
                                <c:out value="${activePage}"/>
                            </div>
                        </a>
                    </li>
                    <c:forEach var="number" begin="${activePage + 1}" end="${lastPage}">
                        <li>
                            <a href="${pageContext.request.contextPath}/controller?command=show_contact_list&page=${number}">
                                <div class="page-n">
                                    <c:out value="${number}"/>
                                </div>
                            </a>
                        </li>
                    </c:forEach>
                    <li>
                        <a href="${pageContext.request.contextPath}/controller?command=show_contact_list&page=${activePage >= lastPage ? lastPage : activePage + 1}">
                            <div class="page-n next-page">
                                >
                            </div>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/controller?command=show_contact_list&page=${lastPage}">
                            <div class="page-n last-page">
                                >>
                            </div>
                        </a>
                    </li>
                </ul>
            </div>
        </c:if>
        <c:if test="${contactsCount == 0}">
            <br> <div class="not-found"> Контактов с заданными параметрами не найдено! </div>
        </c:if>
        <c:if test="${not null and not empty criteria}">
            <div class="search-criteria-block">
                <div class="search-criteria"> Критерий поиска: </div>
                <c:forEach var="parameter" items="${criteria}">
                    <input class="parameter-name" type="text" disabled value="${parameter}">
                </c:forEach>
            </div>
        </c:if>
        <div class="first-row">
            <div class="column column-1"> # </div>
            <div class="column column-3"> Фамилия, имя </div>
            <div class="column column-2"> Дата рождения </div>
            <div class="column column-5"> Адрес проживания </div>
            <div class="column column-4"> Место работы </div>
        </div>
        <form id="contact-list-form" method="post">
            <div id="contact-list">
                <c:forEach var="contact" items="${contacts}">
                    <div class="one-row id=${contact.id}">
                        <label for="check-${contact.id}">
                            <div class="row-info">
                                <div class="column column-1">
                                    <input type="checkbox" name="contact-check" id="check-${contact.id}" value="${contact.id}">
                                </div>
                                <div class="column column-3">
                                    <a href="${pageContext.request.contextPath}/controller?command=show_contact&contact_id=${contact.id}">
                                        <c:out value=" ${contact.lastName} ${contact.firstName}"/>
                                    </a>
                                </div>
                                <div class="column column-2">
                                    <c:out value="${contact.getDateString()}"/>
                                </div>
                                <div class="column column-5">
                                    <c:if test="${not null and not empty contact.city}"> г. </c:if>
                                    <c:out value="${contact.city}" />

                                    <c:if test="${not null and not empty contact.street}"> ул. </c:if>
                                    <c:out value="${contact.street}" />

                                    <c:if test="${not null and not empty contact.houseNumber}"> д. </c:if>
                                    <c:out value="${contact.houseNumber}" />

                                    <c:if test="${not null and not empty contact.flat}"> кв. </c:if>
                                    <c:out value="${contact.flat}" />

                                </div>
                                <div class="column column-4">
                                    <c:out value="${contact.job}"/>
                                </div>
                            </div>
                        </label>
                    </div>
                </c:forEach>
            </div>
            <input type="hidden" id="path-context" value="${pageContext.request.contextPath}">
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
        <script src="scripts/contact-list-script.js"></script>
    </body>
</html>
