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
    <title>Amplify Sign In</title>
    <link rel="stylesheet" href="/CSS/login.css"> 
</head>
<body>
    <div class="container">
            <div class="col-1">
                <div id="login-logo">
                    <img src="/imgs/logo_with_name.png" alt="login-logo">
                </div>
                <div id="slogan">
                    <p>Amplify Your Listening Experience, Anytime, Anywhere.</p>
                </div>
            </div>
            <div class="col-2">
                <div>
                    <h2>Sign In</h2>
                </div>
                <!-- *******************ADD INPUT VALIDATION FOR USERNAME AND PASSWORD******************* -->
                <div id="form">
                    <form:form method="post" action="/login" modelAttribute="newLogin">
                        <div class="form-element">
                            <form:label path="email">Email:</form:label>
                            <form:input type="email" path="email" placeholder="youraddress@email.com"></form:input>
                        </div>
                        <div class="error-container"><form:errors path="email" cssClass="error"/></div>
                        <div class="form-element">
                            <form:label path="password">Password:</form:label>
                            <form:input type="password" path="password" placeholder="password456"></form:input>
                        </div>
                        <div class="error-container"><form:errors path="password" cssClass="error"/></div>

                        <div>
                            <input id="submit-btn" type="submit" value="Login">
                        </div>
                    </form:form>
                   <%-- <div class="form-element">
                        <label for="username">Username:</label>
                        <input type="text" placeholder="userName123">
                    </div>
                    <div class="form-element">
                        <label for="password">Password:</label>
                        <input type="password" placeholder="password456">
                    </div>
                    <!-- *******************FORM NEEDS TO SUBMIT DATA FOR LOGIN VERIFICATION AND REDIRECT TO DASHBOARD******************* -->
                    <div id="submit">
                        <button id="submit-btn"><span>Log In</span></button>
                    </div>--%>
                </div>
                <div id="third-party-login">
                    <!-- *******************CLICKING ON THE IMAGE NEEDS TO MAKE AN API CALL TO THE GOOGLE AUTHENTICATION SERVICE******************* -->
                    <a href="#">
                        <img src="/imgs/google_logo.png" alt="Google Sign in">
                    </a>
                </div>
                <div id="form-extras">
                    <!-- *******************NEEDS TO REDIRECT USER TO THE "FORGOT PASSWORD" PAGE******************* -->
                    <div>
                        <a href="#">Forgot Password?</a>
                    </div>
                    <!-- *******************NEEDS TO REDIRECT USER TO THE "REGISTRATION" PAGE******************* -->
                    <p>Don't have an account already? <a href="/register">Sign up now!</a></p>
                </div>
            </div>
    </div>
</body>
</html>