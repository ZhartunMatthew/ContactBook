<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Index</title>
        <meta charset="utf-8">
    </head>
    <body>
        <h2>привет!</h2>
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <input type="hidden" name="command" value="show_contact_list">
            <button type="submit">
                Show contacts
            </button>
        </form>
    </body>
</html>
