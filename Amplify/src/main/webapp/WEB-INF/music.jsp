<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Amplify</title>
    <style>
        * {
            margin: 0;
            font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
            box-sizing: border-box;
        }
        .header {
            display: flex;
            width: 100%;
            justify-content: space-around;
            border-bottom: 1px solid black;
            background-color:rgb(43, 43, 43); 
            padding: 10px;
            align-items: center;
        }
        body{
            background-color: black;
        }
        .link {
            background-color: orangered;
            height: 40px;
            width: 150px;
            text-decoration: none;
            color: white;
            margin: 5px;
            display: flex;
            justify-content: center;
            align-items: center;
            text-align: center;
            border-radius: 10px;
        }
        .link:hover {
            background-color: rgb(189, 50, 0);
        }
        .main {
            width: 100%;
            display: flex;
            flex-direction: column;
            color: white;
            align-items: center;
        }
        .music-container{
            display: flex;
            flex-direction: column;
            width: 100%;
            align-items: center;
        }
        .music{
            background-color: rgb(218, 218, 218);
            width: 90%;
            border-radius: 20px;
            color: black;
            margin: 5px;
            padding: 5px;
            height: 100px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            padding-left: 10px;
        }
        .music:hover{
            background-color: rgb(46, 46, 46);
            color: rgb(0, 253, 0);
        }
       
        .footer {
            display: flex;
            width: 100%;
            justify-content: center;
            position: fixed;
            bottom: 0;
            width: 100%;
            background-color: aliceblue;
            border-top: 1px solid black;
            padding: 10px;
            background-color: rgb(43, 43, 43);
        }
        .footer-text{
            margin: 5px;
        }
        .player-container{
            width: 70%;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            color: white;
        }
        .player{
            width: 100%;
        }
    </style>
</head>
<body>
    <div class="header">
        <a class="link" href="/chats">Messages</a>
        <a class="link" href="/dashboard">My music</a>
        <a class="link" href="/logout">Logout</a>
    </div>
    <div class="main">
        <h2>Songs</h2>
        <div class="music-container" id="songContainer"></div>
    </div>
    <div class="footer">
        <div class="player-container">
            <div class="footer-text">
                <h3 id="song-text"></h3>
            </div>
            <audio class="player" id="audioPlayer" controls>
                Your browser does not support the audio element.
            </audio>
        </div>
    </div>
    <script>
        const getSongs = async () => {
            try {
                const response = await fetch('/allSongs');
                const songs = await response.json();
                const songContainer = document.getElementById('songContainer');

                songs.forEach(song => {
                    const music = document.createElement('div')
                    const musicTitle = document.createElement('h3')
                    const musicArtist = document.createElement('p')
                    const musicAlbum = document.createElement('p')
                    const musicGenre = document.createElement('p')
                
                    music.classList.add('music')
                    musicTitle.classList.add('title')
                    musicArtist.classList.add('artist')
                    musicAlbum.classList.add('album')
                    musicGenre.classList.add('genre')

                    musicTitle.textContent = song.title
                    musicArtist.textContent = song.artist
                    musicAlbum.textContent = song.album
                    musicGenre.textContent = song.genre

                    music.addEventListener('click',()=>{
                        playSong(song)
                    })

                    music.appendChild(musicTitle)
                    music.appendChild(musicArtist)
                    music.appendChild(musicAlbum)
                    music.appendChild(musicGenre)
                    songContainer.appendChild(music)
                });
            } catch (error) {
                console.error('Error fetching songs:', error);
            }
        };

        const playSong = (song) => {
            const audioPlayer = document.getElementById('audioPlayer');
            const songDetails = document.getElementById('song-text')
            songDetails.textContent = song.title +' | ' + song.artist
            audioPlayer.src = `/playSong/` + song.id;
            audioPlayer.load();
            audioPlayer.play();
        };

        getSongs();
    </script>
</body>
</html>
