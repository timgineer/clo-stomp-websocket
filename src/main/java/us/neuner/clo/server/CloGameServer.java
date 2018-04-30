package us.neuner.clo.server;

import us.neuner.clo.message.ChatMessage;
import us.neuner.clo.message.Message;
import us.neuner.clo.message.ChatEntry;

import java.util.Set;
import java.util.TreeSet;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Controller
public class CloGameServer {

    private Log log = LogFactory.getLog(CloGameServer.class);

    private CloGameSession session = new CloGameSession(this);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    
    public CloGameServer() {
    	
    	session = new CloGameSession(this);
    }
    
    @MessageMapping("/message")
    public void messageResourceHandler(ChatMessage chat, SimpMessageHeaderAccessor sha) {
    	
    	PlayerDetail pd = PlayerDetail.getPlayerDetail(chat.getPsid());
    	String sid = sha.getSessionId();

    	assert(sid != null && !sid.isEmpty());
    	
        if (pd == null) {
        	pd = new PlayerDetail(session, sid);
        	session.clientJoinHandler(pd);
        } else if (!sid.equals(pd.getSid())) {
        	pd.setSid(sid);
        }
        
        pd.getSession().chatMessageHandler(chat);
        
    	// reverse compatibility
        chatMessageHandler(chat, sha);
    }

    //TODO: Create test for this class with a {PlayerDetail, ChatHistoryMessage} signature.
    public void sendToClient(PlayerDetail pd, Message msg) {
    	
        SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
        accessor.setSessionId(pd.getSid());
        accessor.setLeaveMutable(true);

        // /queue/chathistory - broadcasts individual messages as they arrive
        messagingTemplate.convertAndSendToUser(pd.getSid(), "/queue/chathistory", msg, accessor.getMessageHeaders());
    }

    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent e) {
    	String sid = e.getSessionId();
    	log.info("Client disconnect: " + sid);
    	
    }
    
    ////////////// START REVERSE COMPATIBILITY ////////////// 
    private Set<String> sidSet = Collections.synchronizedSet(new TreeSet<String>());
    
    //client sends messages to "/client/message"
    //client subscribes to /user/topic/chat 
    public void chatMessageHandler(ChatMessage chat, SimpMessageHeaderAccessor sha) {

        sidSet.add(sha.getSessionId());

        for (String sid : sidSet.toArray(new String[sidSet.size()])) {

            SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
            accessor.setSessionId(sid);
            accessor.setLeaveMutable(true);

            // /queue/chat - broadcasts individual messages as they arrive
        	ChatMessage msg = new ChatMessage("asdf", chat.getMsg());
            messagingTemplate.convertAndSendToUser(sid, "/queue/chat", msg, accessor.getMessageHeaders());
        }
    }

    @EventListener
    public void onDisconnectEventDeleteMe(SessionDisconnectEvent e) {
    	String sid = e.getSessionId();
    	//log.info("Client disconnect: " + sid);
        sidSet.remove(sid);
    	
    }
    //////////////  END REVERSE COMPATIBILITY  //////////////
}
