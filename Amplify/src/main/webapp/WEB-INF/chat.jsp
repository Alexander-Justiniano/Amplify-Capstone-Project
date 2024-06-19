<!DOCTYPE html>
<html>
<head>
    <title>Chat</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <style>
        *{
            box-sizing: border-box;
            font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
            margin: 0;
        }
        body{
            background-color: rgb(22, 22, 22);
        }
        .element-container{
            display: flex;
            flex-direction: column;
            background-color: rgb(22, 22, 22);
        }
        .header{
            position: fixed;
            z-index: 10000;
            width: 100%;
            display: flex;
            justify-content: flex-start;
            background-color: rgb(43, 43, 43);
            color: white;
            align-items: center;
        }
        .back-btn{
            width: 25%;
            display: flex;
            align-items: center;
            padding: 5px;
            margin: 5px;
        }
        .back-img{
            width: 30px;
            height: 30px;
        }
        .username{
            display: flex;
            width: 50%;
            text-align: center;
            justify-content: center;
        }
        .chats-container{
            margin-top: 50px;
            display: flex;
            flex-direction: column;
            width: 100%;
            margin-bottom: 60px;
            background-color: rgb(22, 22, 22);
        }
        .message{
            min-width: 10%;
            min-height: 20px;
            max-width: 50%;
            padding: 10px;
            margin: 10px;
            color: white;
        }
        .sender {
            background-color: #0051ff;
            align-self: flex-end;
            border-top-left-radius: 20px;
            border-top-right-radius: 20px;
            border-bottom-left-radius: 20px;
        }
        .receiver {
            background-color: #ff5e00;
            text-align: left;
            align-self: flex-start;
            border-top-left-radius: 20px;
            border-top-right-radius: 20px;
            border-bottom-right-radius: 20px;
        }
        .footer{
            position: fixed;
            bottom: 0;
            width: 100%;
            display: flex;
            justify-content: center;
            padding: 10px;
            z-index: 200000;
            background-color: rgb(43, 43, 43);
        }
        .message-input-container{
            width: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
            max-height: 100px;
            overflow-y: auto;
        }
        .message-input {
            width: 50%;
            height: auto; 
            border-radius: 50px;
            outline: none;
            border: none;
            padding: 10px;
            box-sizing: border-box; 
            resize: vertical;
            margin: 5px;
        }
        .send-btn{
            width: 40px;
            height: 40px;
        }
    </style>
    <script>
        let stompClient = null

        const getChatIdFromUrl=()=> {
            const params = new URLSearchParams(window.location.search)
            return params.get('chatId')
        }

        let chatId = getChatIdFromUrl()

        const connect=()=> {
            let socket = new SockJS('/ws')
            stompClient = Stomp.over(socket)
            stompClient.connect({}, function (frame) {
                console.log('Connected: ' + frame)
                stompClient.subscribe("/topic/chat/"+chatId, function (messageOutput) {
                    showMessage(JSON.parse(messageOutput.body))
                })
            })
        }

        function sendMessage() {
            let messageContent = document.getElementById('message').value
            if(messageContent == ""){
                return
            }
            let userId = localStorage.getItem('userId')
            stompClient.send("/app/chat/"+chatId, {}, JSON.stringify({
                'content': messageContent,
                'senderId': userId
            }))                     
            document.getElementById('message').value = '' 
        }
        function showMessage(message) {
            const response = document.getElementById('response')
            let p = document.createElement('p')
            let userId = localStorage.getItem('userId')
        
            if (message.senderId == userId) {
                p.className = 'sender message'
            } else {
                p.className = 'receiver message'
            }
            p.appendChild(document.createTextNode(message.content))
            response.appendChild(p)
            scrollToBottom()
        }
        const fetchChatMessages = async () => {
            try {
                const chatId = getChatIdFromUrl()
                const url = '/messages/getMessages/' + chatId
        
                const response = await fetch(url)
        
                if (response.ok) {
                    const messages = await response.json()
                    const chatContainer = document.getElementById('response')
                    const userId = localStorage.getItem('userId')
                    messages.forEach(message => {
                        const chat = message.chat
                        if(chat.receiverId != userId){
                            fetchUser(chat.receiverId)
                        }else if(chat.senderId != userId){
                            fetchUser(chat.senderId)
                        }
                        const messageElement = document.createElement('p')
                        messageElement.textContent = message.content
                        if (userId == message.senderId) {
                            messageElement.className = 'sender message'
                        } else {
                            messageElement.className = 'receiver message'
                        }
                        chatContainer.appendChild(messageElement)
                    })
                    scrollToBottom()
                } else {
                    console.log("An error occurred:", await response.json())
                }
            } catch (e) {
                console.log("An error occurred:", e)
            }
        }
        const fetchUser = async(userId)=>{
            try{
                const url = "/api/users/getuser/" + userId
                const response = await fetch(url)
                if(response.ok){
                    const user = await response.json()
                    if(!user || user.length == 0){
                        return
                    }else{
                        document.getElementById('username').textContent = user.userName
                    }
                }else{
                    console.log(await response.json())
                }
            }catch(e){
                console.log(console.log(e))
            }       
        }
        const scrollToBottom = () => {
            const messages = document.querySelectorAll('.message')
            if (messages.length > 0) {
                const lastMessage = messages[messages.length - 1]
                lastMessage.scrollIntoView()
            }
        }
        const fetchUserUserName = async (userId) => {
            try {
                const response = await fetch('/api/users/' + userId)
                if (response.ok) {
                    const user = await response.json()
                    return user.userName
                } else {
                    console.error('Error fetching user:', await response.json())
                    return 'Unknown User'
                }
            } catch (error) {
                console.error('Error:', error)
                return 'Unknown User'
            }
        }

        const fetchChats = async (userId) => {
            try {
                const url = "/api/chats/" + userId
                const response = await fetch(url)
                const chats = await response.json()
                const chatsContainer = document.getElementById('chats-container')
                
                if (!chats || chats.length === 0) {
                    chatsContainer.textContent = "No chats found"
                    return
                }
                
                chatsContainer.innerHTML = ''
                for (const chat of chats) {
                    const chatElement = document.createElement('div')
                    const chatId = chat.chatId
                    
                    if (userId == chat.senderId) {
                        chatElement.textContent = await fetchUserUserName(chat.receiverId)
                    } else if (userId == chat.receiverId) {
                        chatElement.textContent = await fetchUserUserName(chat.senderId)
                    }
                    
                    chatElement.classList.add('chat-item')
                    chatElement.addEventListener('click', () => {
                        window.location.href = '/chat?chatId=' + chatId
                    })
                    chatElement.classList.add('chat-element')
                    chatsContainer.appendChild(chatElement)
                }
            } catch (e) {
                console.log(e)
            }
        }
        window.onload = () => {
            fetchChatMessages()
            connect()
        }
    </script>
</head>
<body>
    <div class="element-container" id="element-container">
        <div class="header">
            <div class="back-btn">
               <img class="back-img" src="/imgs/back.png" alt="back" onclick="window.location.href = '/chats'">
            </div>
            <div class="username">
                <h1 id="username"></h1>
            </div>
        </div>
        <div class="main">
            <div id="response" class="chats-container">
               
            </div>
        </div>
        <div class="footer">
            <div class="message-input-container">
                <input class="message-input" type="text" id="message" autocomplete="off" placeholder="Type your Message"/>
                <img class="send-btn" src="/imgs/send.png" alt="" onclick="sendMessage()">
            </div>
        </div>   
    </div>
</body>
</html>