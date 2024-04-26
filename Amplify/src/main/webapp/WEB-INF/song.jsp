<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="th" uri="http://jakarta.apache.org/taglibs/standard/permittedTaglibs" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Audio Player</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.24/css/dataTables.bootstrap4.min.css">

    <style>
        /* Customizing DataTables pagination */
        #myTable_wrapper .dataTables_paginate .paginate_button {
            padding: 0.375rem 0.75rem;
            margin-left: 0.25rem;
            font-size: 0.875rem;
            line-height: 1.5;
            border-radius: 0.25rem;
            color: #007bff;
            background-color: #fff;
            border-color: #007bff;
        }

        #myTable_wrapper .dataTables_paginate .paginate_button:hover {
            color: #fff;
            background-color: #007bff;
            border-color: #007bff;
        }

        #myTable_wrapper .dataTables_paginate .paginate_button.current {
            color: #fff;
            background-color: #007bff;
            border-color: #007bff;
        }

        #myTable_wrapper .dataTables_paginate .paginate_button.disabled {
            color: #6c757d;
            pointer-events: none;
            background-color: #fff;
            border-color: #dee2e6;
        }
    </style>

</head>
<body>

<div class="container mt-5">
    <div class="row mt-1">
        <div class="col-md-12 d-flex justify-content-end">
            <a href="/logout" class="btn btn-link">Logout</a>
        </div>
    </div>
    <h2>Add Song</h2>
    <form action="/saveSong" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="title">Title:</label>
            <input type="text" class="form-control" id="title" name="title" required>
        </div>
        <div class="form-group">
            <label for="genre">Genre:</label>
            <input type="text" class="form-control" id="genre" name="genre" required>
        </div>
        <div class="form-group">
            <label for="album">Album:</label>
            <input type="text" class="form-control" id="album" name="album" required>
        </div>
        <div class="form-group">
            <label for="artist">Artist:</label>
            <input type="text" class="form-control" id="artist" name="artist" required>
        </div>
        <div class="form-group">
            <label for="file">Upload Audio (MP3 and WAV Files Only!):</label>
            <input type="file" class="form-control-file" id="file" name="file" required>
        </div>

        <button type="submit" class="btn btn-primary mt-1">Add Song</button>
    </form>

        <h2 class="mt-5" style="margin-bottom:20px;">Song List</h2>
        <table class="table mt-3" id="myTable">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Title</th>
                    <th>Genre</th>
                    <th>Album</th>
                    <th>Artist</th>
                    <th>Song</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${songList}" var="song" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td>${song.title}</td>
                        <td>${song.genre}</td>
                        <td>${song.album}</td>
                        <td>${song.artist}</td>
                        <td>
                            <audio controls>
                                <source src="/uploads/${song.fileName}" type="audio/mpeg">
                                Your browser does not support the audio element.
                             </audio>
                        </td>
                        <td>
                            <form action="/deleteSong/${song.id}" method="post">
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.min.js"></script>

<script>
    $(document).ready(function () {
        $('#myTable').DataTable();
    });
</script>

</body>
</html>
