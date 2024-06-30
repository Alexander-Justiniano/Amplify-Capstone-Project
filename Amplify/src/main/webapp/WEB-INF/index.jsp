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
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <link rel="stylesheet" href="/CSS/carouselStyle.css">
    </head>
    <body>
        
        <div class="slider">
            <div class="item">
                <h1>My Account</h1>
                <h2>
                    Access and make change to your account details.
                </h2>
            </div>
            <div class="item">
                <h1>DJ</h1>
                <h2>
                    Not sure what you're in the mood for? Try out our smart DJ to find you something to groove to. 
                </h2>
            </div>
            <div class="item">
                <h1>Shuffle Play</h1>
                <h2>
                    Shuffle a to and through a random playlist in your library.
                </h2>
            </div>
            <div class="item">
                <h1>My Library</h1>
                <h2>
                    Browse and customize your playlists. Collaberate with others to make facinating song combinations you can enjoy together.
                </h2>
            </div>
            <div class="item">
                <h1>Discover</h1>
                <h2>
                    Discover new content creators and genres from our vast library of finest sounds.  
                </h2>
            </div>
            <div class="item">
                <h1>Settings</h1>
                <h2>
                    Adjust your experience on the Amplify app.
                </h2>
            </div>
            <div class="item">
                <h1>Technical Support</h1>
                <h2>
                    Need help? Want to report a bug? Contact our tech support experts via our ticketing system. 
                </h2>
            </div>
            <button id="next">></button>
            <button id="prev"><</button>
        </div>
    
        <script src="/JS/carousel.js"></script>
    </body>
    </html>