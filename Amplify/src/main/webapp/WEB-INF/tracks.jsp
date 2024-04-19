<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tracks</title>
    <link rel="stylesheet" type="text/css" href="/CSS/tracks.css"> <!-- Link to your CSS file -->
</head>
<body>
    <h2>Tracks</h2>
    
    <!-- Display track list -->
    <ul class="track-list">
        <c:forEach items="${tracks}" var="track">
            <li class="track-item">
                ${track.title} <!-- Display track title -->
                <!-- Embed audio player for streaming -->
                <audio controls>
                  <source src="${pageContext.request.contextPath}/tracks/stream/${track.id}" type="audio/mp3">
                  Your browser does not support the audio element.
                </audio>
                <a href="/tracks/${track.id}">Download</a> <!-- Download link for each track -->
            </li>
        </c:forEach>
    </ul>
    
    <!-- Form for uploading new track -->
    <form action="${pageContext.request.contextPath}/tracks/upload" method="post" enctype="multipart/form-data" class="upload-form">
        <h3>Upload New Track</h3>
        <label for="file">Select Track File:</label>
        <input type="file" id="file" name="file" accept="audio/*">
        <br>
        <button type="submit">Upload</button>
    </form>
</body>
</html>
