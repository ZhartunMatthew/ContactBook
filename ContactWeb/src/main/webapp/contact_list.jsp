<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title> Список контактов </title>
</head>
    <body>
        <div class="nav-buttons">
            <a class="nav-button home" href="/controller?command=show_contact_list&page=1"> </a>
            <a class="nav-button add" href="/controller?command=show_contact"> </a>
            <a class="nav-button delete" href="/controller?command=delete_contact"></a>
            <a class="nav-button search" href="/controller?command=search_contact"></a>
            <a class="nav-button send-mail" href="/controller?command=send_mail"></a>
        </div>
        <div class="pagination">
            <c:set var="firstPage" value="${1}"/>
            <c:set var="lastPage" value="${pagination.pageCount}"/>
            <c:set var="activePage" value="${pagination.activePage}"/>
            <ul>
                <li>
                    <a href="/controller?command=show_contact_list&page=${firstPage}">
                        <div class="page-n first-page">
                            <<
                        </div>
                    </a>
                </li>
                <li>
                    <a href="/controller?command=show_contact_list&page=${activePage <= 1 ? 1 : activePage - 1}">
                        <div class="page-n prev-page">
                            <
                        </div>
                    </a>
                </li>
                <c:forEach var="number" begin="${firstPage}" end="${activePage-1}">
                    <li>
                        <a href="/controller?command=show_contact_list&page=${number}">
                            <div class="page-n">
                                <c:out value="${number}"/>
                            </div>
                        </a>
                    </li>
                </c:forEach>
                <li>
                    <a href="/controller?command=show_contact_list&page=${activePage}">
                        <div class="page-n active-page">
                            <c:out value="${activePage}"/>
                        </div>
                    </a>
                </li>
                <c:forEach var="number" begin="${activePage + 1}" end="${lastPage}">
                    <li>
                        <a href="/controller?command=show_contact_list&page=${number}">
                            <div class="page-n">
                                <c:out value="${number}"/>
                            </div>
                        </a>
                    </li>
                </c:forEach>
                <li>
                    <a href="/controller?command=show_contact_list&page=${activePage >= lastPage ? lastPage : activePage + 1}">
                        <div class="page-n next-page">
                            >
                        </div>
                    </a>
                </li>
                <li>
                    <a href="/controller?command=show_contact_list&page=${lastPage}">
                        <div class="page-n last-page">
                            >>
                        </div>
                    </a>
                </li>
            </ul>
        </div>
            <div class="first-row">
                <div class="column column-1"> # </div>
                <div class="column column-3"> Фамилия, имя </div>
                <div class="column column-3"> Дата рождения </div>
                <div class="column column-4"> Адрес проживания </div>
                <div class="column column-x"> Место работы </div>
            </div>
            <div id="contact-list">
                <c:forEach var="contact" items="${contacts}">
                    <div class="one-row id=${contact.id}">
                        <label for="check-${contact.id}">
                            <div class="row-info">
                                <div class="column column-1">
                                    <input type="checkbox" name="contact-check" id="check-${contact.id}" value="${contact.id}">
                                </div>
                                <div class="column column-3">
                                    <a href="/controller?command=show_contact&contact_id=${contact.id}">
                                        <c:out value=" ${contact.lastName} ${contact.firstName}"/>
                                    </a>
                                </div>
                                <div class="column column-3">
                                    <c:out value="${contact.birthDate}"/>
                                </div>
                                <div class="column column-4">
                                    <c:out value=" г. ${contact.city}
                                                  ул. ${contact.street}
                                                  д. ${contact.houseNumber}
                                                  кв. ${contact.flat}"/>
                                </div>
                                <div class="column column-x">
                                    <c:out value="${contact.job}"/>
                                </div>
                            </div>
                        </label>
                    </div>
                </c:forEach>
            </div>
        <link rel="stylesheet" type="text/css" href="style/column-style.css">
        <link rel="stylesheet" type="text/css" href="style/button-style.css">
        <link rel="stylesheet" type="text/css" href="style/common-style.css">
    </body>
</html>
