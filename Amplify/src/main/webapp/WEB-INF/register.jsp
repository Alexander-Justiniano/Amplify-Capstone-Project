<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. --> 
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!-- Formatting (dates) --> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" href="/CSS/register.css">
</head>
<body>
    <div class="container">
        <div id="col-1">
            <div id="logo">
                <img src="/imgs/logo.png" alt="plain logo">
            </div>
                <!-- *******************ADD INPUT VALIDATION ERRORS FOR ALL FORM FIELDS******************* -->
            <div id="form">
                <div id="elements-container">
                	<form:form method="post" action="/register" modelAttribute="newUser">
	                    <div class="form-element">
	                        <form:label path="email">Email:</form:label>
	                        <form:input type="email" path="email" placeholder="youraddress@email.com"></form:input>
	                    </div>
	                    <div class="error-container"><form:errors path="email" cssClass="error"/></div>
	                    <div class="form-element">
	                        <form:label path="userName">Username:</form:label>
	                        <form:input type="text" path="userName" placeholder="userName123"></form:input>
	                    </div>
	                    <div class="error-container"><form:errors path="userName" cssClass="error"/></div>
	                    <div class="form-element">
	                        <form:label path="password">Password:</form:label>
	                        <form:input type="password" path="password" placeholder="password456"></form:input>
	                    </div>
	                    <div class="error-container"><form:errors path="password" cssClass="error"/></div>
	                    <div class="form-element">
	                        <form:label path="confirm">Confirm Password:</form:label>
	                        <form:input type="password" path="confirm" placeholder="password456"></form:input>
	                    </div>
	                    <div class="error-container"><form:errors path="confirm" cssClass="error"/></div>
                	</form:form>
                </div>
                <div id="form-extras">
                    <h3>Sign up with:</h3>
                <!-- *******************CLICKING ON THE IMAGE NEEDS TO MAKE AN API CALL TO THE GOOGLE AUTHENTICATION SERVICE******************* -->
                    <a href="#">
                        <img src="/imgs/google_logo.png" alt="google logo">
                    </a>
                    <div>
                        <input id="submit-btn" type="submit" value="Create Account">
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>