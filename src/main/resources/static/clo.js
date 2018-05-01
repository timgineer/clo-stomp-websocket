var stompClient = null;
var chatHistorySubscription = null
var psid = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#messages").html("");
}

function connect() {
    var socket = new SockJS('/clo');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        
        chatHistorySubscription = stompClient.subscribe('/user/queue/chathistory', function (chathistory) {
        	
        	var msg = JSON.parse(chathistory.body);
        	if ((typeof msg == "object") && (typeof msg.psid == "string"))
        		psid = msg.psid;
        	showChatHistory(msg);
        });
        
        sendClientJoin("changeThisName");
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function makeMessage(type) {
	var message = {};
	message.type = type;
	if (typeof psid == "string")
		message.psid = psid;
	return message;
}

function sendClientJoin(playerName, sessionPassword = null) {
	var message = makeMessage("clientJoin");
	if (typeof sessionPassword == "string")
		message.sessionPassword = sessionPassword;
	message.playerName = playerName
	stompClient.send("/client/message", {}, JSON.stringify(message));
}

function sendChat() {
	var message = makeMessage("chat");
	message.msg = $("#msg").val();
    stompClient.send("/client/message", {}, JSON.stringify(message));
}

//function showChat(message) {
//    $("#messages").append("<tr><td>" + message + "</td></tr>");
//}

function showChatHistory(ch) {
	var messages = $("#messages"); 
	if ((ch != null) && (typeof ch.msgList == "object")) {
	    messages.html("");
	    for (var i = 0; i < ch.msgList.length; i++) {
	    	var msg = ch.msgList[i].msg;
	    	if (typeof msg == "string")
	    		messages.append("<tr><td>" + msg + "</td></tr>");
	    }
	    if (messages[0].childElementCount > 0)
	    	messages[0].lastChild.scrollIntoView();
	}
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendChat(); });
});
