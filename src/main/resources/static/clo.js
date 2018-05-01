var stompClient = null;
var psid = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#nameInput").prop("disabled", connected);
    $("#msgInput").prop("disabled", !connected);
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
        
        stompClient.subscribe('/user/queue/server', function (serverMessage) {
        	
        	var msg = JSON.parse(serverMessage.body);
        	if ((typeof msg == "object") && (typeof msg.psid == "string"))
        		psid = msg.psid;
        	if ((typeof msg.type == "string") && (msg.type == "chatHistory"))
        		showChatHistory(msg);
        });
        
        sendClientJoin($("#nameInput").val());
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
	message.msg = $("#msgInput").val();
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
	    	var from = ch.msgList[i].playerName;
	    	var msg = ch.msgList[i].msg;
	    	if ((typeof from == "string") && (typeof msg == "string"))
	    		messages.append("<tr><td>[" + from + "] "+ msg + "</td></tr>");
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
