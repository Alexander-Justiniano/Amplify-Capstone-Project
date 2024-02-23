<!-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> -->
<!-- c:out ; c:forEach etc. --> 
<!-- <%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %> -->
<!-- Formatting (dates) --> 
<!-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %> -->
<!-- form:form -->
<!-- <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> -->
<!-- for rendering errors on PUT routes -->
<!-- <%@ page isErrorPage="true" %> -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" href="/register.css"> <!-- change to match your file/naming structure -->
</head>
<body>
    <div class="container">
        <div id="col-1">
            <div id="logo">
                <img src="logo.png" alt="plain logo">
            </div>
                <!-- *******************ADD INPUT VALIDATION FOR ALL FORM FIELDS******************* -->
            <div id="form">
                <div id="elements-container">
                    <div class="form-element">
                        <label for="email">Email:</label>
                        <input type="email" placeholder="youraddress@email.com">
                    </div>
                    <div class="form-element">
                        <label for="username">Username:</label>
                        <input type="text" placeholder="userName123">
                    </div>
                    <div class="form-element">
                        <label for="password">Password:</label>
                        <input type="password" placeholder="password456">
                    </div>
                    <div class="form-element">
                        <label for="cnfm-password">Confirm Password:</label>
                        <input type="password" placeholder="password456">
                    </div>
                </div>
                <div id="form-extras">
                    <h3>Sign up with:</h3>
                <!-- *******************CLICKING ON THE IMAGE NEEDS TO MAKE AN API CALL TO THE GOOGLE AUTHENTICATION SERVICE******************* -->
                    <a href="#">
                        <img src="Google Logo.png" alt="google logo">
                    </a>
                    <div>
                        <!-- *******************FORM NEEDS TO SUBMIT DATA FOR ACCOUNT CREATION AND REDIRECT TO DASHBOARD******************* -->
                        <button id="submit-btn">Create Account</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>