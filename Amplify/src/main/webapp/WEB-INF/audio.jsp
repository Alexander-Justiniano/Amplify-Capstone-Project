<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="th" uri="http://jakarta.apache.org/taglibs/standard/permittedTaglibs" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Audio Player</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>

<div class="container">
    <h1>Uploaded Audio Files</h1>
    <form action="/audio/upload" method="post" enctype="multipart/form-data">
        <input type="file" name="file">
        <button type="submit" class="btn btn-primary">Upload</button>
    </form>

    <hr>

    <div class="container">
        <h1>Audio Playlist</h1>
        <ul class="list-unstyled">
            <c:forEach items="${audioFiles}" var="audioFile">
                <li>
                    <audio controls>
                        <source src="/audio/${audioFile.name}" type="audio/mpeg">
                        Your browser does not support the audio element.
                    </audio>
                    <a href="/audio/delete/${audioFile.id}" class="btn btn-danger">Delete</a>
                </li>
            </c:forEach>

        </ul>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
