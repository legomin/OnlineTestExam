<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${test.title}</title>
</head>
<body>
<c:choose>
    <c:when test="${not empty error}">
        <p>Authorization error! </p>
    </c:when>
 </c:choose>

<h1>${test.title}</h1>

<p>${test.text}</p>

<form method="post" action="/spring-hibernate-mysql/krams/main/auth">
    <p> login: <input type="text" name="login"></p>
    <p> pass: <input type="password" name="pass"></p>
    <p><input type="submit" name="submit"></p>
</form>


</body>
</html>