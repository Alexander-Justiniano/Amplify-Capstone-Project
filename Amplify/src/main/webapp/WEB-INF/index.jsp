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
    <title>Dashboard</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/main.css"> <!-- change to match your file/naming structure -->
    <script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/js/app.js"></script><!-- change to match your file/naming structure -->
</head>
<body>
    <div class="container mt-5">
        <div class="row mt-1">
            <div class="col-md-12 d-flex justify-content-end">
                <a href="/logout" class="btn btn-link" onclick="localStorage.clear()">Logout</a>
            </div>
        </div>

        <div class="row mt-4">
            <div class="col-md-6 d-flex align-items-center">
                <!-- Left Column (Logo) -->
                <img src="/imgs/logo_with_name.png" alt="Logo">
            </div>
            <div class="col-md-6">
                <!-- Right Column (Button and Description) -->
                <div class="row">
                    <div class="col-md-12">
                        <!-- Button -->
                        <div class="text-center" style="margin-top:250px;">
                            <a href="/song" class="btn btn-success btn-lg rounded-pill py-3 px-4">Manage Your Songs</a>
                        </div>
                        <!-- Description -->
                        <p class="text-center mt-2">Click here to begin your listening experience!</p>
                    </div>
                    <div class="col-md-12">
                        <!-- Button -->
                        <div class="text-center" style="margin-top:250px;">
                            <a href="/music" class="btn btn-success btn-lg rounded-pill py-3 px-4">Songs</a>
                        </div>
                        <!-- Description -->
                        <p class="text-center mt-2">Click here to listen to others uploaded music</p>
                    </div>
                    <div class="col-md-12">
                        <!-- Button -->
                        <div class="text-center" style="margin-top:250px;">
                            <a href="/chats" class="btn btn-success btn-lg rounded-pill py-3 px-4">Chats</a>
                        </div>
                        <!-- Description -->
                        <p class="text-center mt-2">Click here to begin chatting with your friends</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        const urlParams = new URLSearchParams(window.location.search);
        const userId = urlParams.get('userId');
        // Check if the username is not null or empty
        if (userId) {
            // Save the username to localStorage
            localStorage.setItem('userId', userId);
        }
    </script>
</body>
</html>