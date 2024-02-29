<!-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
c:out ; c:forEach etc. --> 
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
    <title>Amplify Sign In</title>
    <link rel="stylesheet" href="/login.css"> <!-- change to match your file/naming structure -->
</head>
<body>
    <div class="container">
            <div class="col-1">
                <div id="login-logo">
                    <img src="logo_with_name.png" alt="login-logo">
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
                    <div class="form-element">
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
                    </div>
                </div>
                <div id="third-party-login">
                    <!-- *******************CLICKING ON THE IMAGE NEEDS TO MAKE AN API CALL TO THE GOOGLE AUTHENTICATION SERVICE******************* -->
                    <a href="#" id="googleSignIn">
                        <img src="Google Logo.png" alt="Google Sign in">
                    </a>
                </div>
                <div id="form-extras">
                    <!-- *******************NEEDS TO REDIRECT USER TO THE "FORGOT PASSWORD" PAGE******************* -->
                    <div>
                        <a href="#">Forgot Password?</a>
                    </div>
                    <!-- *******************NEEDS TO REDIRECT USER TO THE "REGISTRATION" PAGE******************* -->
                    <p>Don't have an account already? <a href="#">Sign up now!</a></p>
                </div>
            </div>
    </div>
    
    <script>
        // Function to handle the click event on the Google logo
        function handleGoogleSignIn() {
            // Make an API call to the Google Authentication service
            // Replace the following with actual API call logic

            console.log("Making API call to Google Authentication service...");
        }

        // Add a click event listener to the Google logo
        document.getElementById('googleSignIn').addEventListener('click', handleGoogleSignIn);
    </script>
</body>
</html>