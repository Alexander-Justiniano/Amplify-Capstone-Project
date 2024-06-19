<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Amplify</title>
    <style>
        *{
            box-sizing: border-box;
            margin: 0;
            font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
        }
        .header{
            position: fixed;
            top: 0;
            background-color: white;
            width: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 10px;
            z-index: 1000;
        }
       
        .users{
            display: flex;
            justify-content: center;
            width: 100%;
        }
        .user-element{
            margin: 5px;
            display: flex;
            align-items: center;
            justify-items: center;
            background-color: orangered;
            color: white;
            padding: 10px;
            width: 100px;
            height: 50px;
            text-overflow: ellipsis;
            font-size: 20px;
            text-align: center;
            border-radius: 10px;
        }
        .user-element:hover{
            cursor: pointer;
        }
        .chats{
            margin-top: 150px;
            width: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .chat-element{
            display: flex;
            width: 75%;
            background-color: rgb(0, 174, 255);
            margin: 5px;
            height: 50px;
            align-items: center;
            padding: 10px;
            border-radius: 10px;
            font-size: 20px;
            text-align: center;
            justify-content: center;
        }
        .chat-element:hover{
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>Chats</h1>
        <div class="users" id="user-container"></div>
    </div>
    <div class="main">
        <div class="chats" id="chats-container"></div>
    </div>
    <div class="footer">

    </div>
    <script>
        const userId = localStorage.getItem('userId')

        const fetchUsers = async () => {
            try {
                const userContainer = document.getElementById('user-container')
                const response = await fetch('/api/users/getall')
                const users = await response.json()
                userContainer.innerHTML = ''
                
                if (!users || users.length === 0) {
                    userContainer.textContent = "No users present"
                    return
                }
        
                users.forEach(user => {
                    if (user.id == userId) {
                        return
                    }
                    const userElement = document.createElement('div')
                    userElement.classList.add('user-item')
                    userElement.textContent = user.userName
                    
                    userElement.addEventListener('click', async () => {
                        try {
                            const chatResponse = await fetch('/api/chats', {
                                method: 'POST',
                                headers: {
                                    'Content-Type': 'application/json'
                                },
                                body: JSON.stringify({
                                    senderId: userId,
                                    receiverId: user.id
                                })
                            })
        
                            if (chatResponse.ok) {
                                const newChat = await chatResponse.json()
                                console.log(newChat)
                                console.log(newChat.chatId)
                                window.location.href = '/chat?chatId=' + newChat.chatId
                            } else {
                                console.log("Error creating chat")
                            }
                        } catch (error) {
                            console.error('Error:', error)
                        }
                    })
                    userElement.classList.add('user-element')
                    userContainer.appendChild(userElement)
                })
            } catch (e) {
                console.log(e)
            }
        }
        
        const fetchChats = async () => {
            try {
                const url = "/api/chats/" + userId;
                const response = await fetch(url);
                const chats = await response.json();
                const chatsContainer = document.getElementById('chats-container');
                
                if (!chats || chats.length === 0) {
                    chatsContainer.textContent = "No chats found";
                    return;
                }
                
                chatsContainer.innerHTML = '';
                for (const chat of chats) {
                    const chatElement = document.createElement('div');
                    const chatId = chat.chatId;
                    
                    if (userId == chat.senderId) {
                        chatElement.textContent = await fetchUserUserName(chat.receiverId);
                    } else if (userId == chat.receiverId) {
                        chatElement.textContent = await fetchUserUserName(chat.senderId);
                    }
                    
                    chatElement.classList.add('chat-item');
                    chatElement.addEventListener('click', () => {
                        window.location.href = '/chat?chatId=' + chatId;
                    });
                    chatElement.classList.add('chat-element');
                    chatsContainer.appendChild(chatElement);
                }
            } catch (e) {
                console.log(e);
            }
        };
        const fetchUserUserName = async(userId)=>{
            try{
                url = "/api/users/getuser/" + userId
                const response = await fetch(url)
                if(response.ok){
                    const user = await response.json()
                    if(!user || user.length == 0){
                        return
                    }else{
                        return user.userName
                    }
                }else{
                    console.log(await response.json())
                }
            }catch(e){
                console.log(console.log(e))
            }       
        }
        fetchChats()
        fetchUsers()        
    </script>
</body>
</html>