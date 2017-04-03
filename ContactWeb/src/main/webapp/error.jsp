<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Ошибка </title>
</head>
<body>
    <img src="${pageContext.request.contextPath}/image/error.png">
    <h3> Упс, что-то пошло не так... </h3>
    <a href="${pageContext.request.contextPath}/controller">
        <h2> Домой </h2>
    </a>
</body>
</html>
