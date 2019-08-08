<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="from" uri="http://www.springframework.org/tags/form" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">

</head>

<body>
<div class="container h-100">
    <div class="row h-100 justify-content-center align-items-center">
        <h1>Welcome to Shop</h1>
    </div>
</div>
<br>
<div class="container" style="width: 300px;">
    <form action="/registration" method="post">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="text" class="form-control" name="username" placeholder="Username (email)">
        <input type="password" class="form-control" name="password" placeholder="Password (6 to 20 char)">
        <input type="password" class="form-control" name="confirmPassword" placeholder="Confirm password">
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign up</button>
        <c:if test="${!empty wrong}">
            <font color="red">${wrong}</font>
        </c:if>
        <a href="/login">Login</a>
    </form>
</div>

<script src="webjars/jquery/3.3.1/dist/jquery.min.js"></script>
<script src="webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>
</html>