var stompClient = null;
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
        //stompClient.subscribe('/user/queue/chat', function (chat) {
        //    showChat(JSON.parse(chat.body).msg);
        //});
        stompClient.subscribe('/user/queue/chathistory', function (chathistory) {
        	
        	console.log(chathistory.body);
        	
        	var msg = JSON.parse(chathistory.body);
        	if ((typeof msg == "object") && (typeof msg.psid == "string"))
        		psid = msg.psid;
        	showChatHistory(msg);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendChat() {
	var message = {'msg': $("#msg").val()};
	if (psid != null)
		message.psid = psid;
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
	    messages[0].lastChild.scrollIntoViewIfNeeded();
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
