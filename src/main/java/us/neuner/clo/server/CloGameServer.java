package us.neuner.clo.server;

import us.neuner.clo.message.ChatMessage;
import us.neuner.clo.message.Message;
import us.neuner.clo.message.ChatEntry;

import java.util.Set;
import java.util.TreeSet;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

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

        if (pd == null) {
        	pd = new PlayerDetail(session, sha.getSessionId());
        	session.clientJoinHandler(pd);
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
    
    ////////////// START REVERSE COMPATIBILITY ////////////// 
    private Set<String> sidSet = Collections.synchronizedSet(new TreeSet<String>());
    
    //client sends messages to "/client/message"
    //client subscribes to /user/topic/chat 
    public void chatMessageHandler(ChatMessage chat, SimpMessageHeaderAccessor sha) {

        ChatEntry payload = new ChatEntry(chat.getMsg());

        sidSet.add(sha.getSessionId());

        for (String sid : sidSet.toArray(new String[sidSet.size()])) {

            SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
            accessor.setSessionId(sid);
            accessor.setLeaveMutable(true);

            // /queue/chat - broadcasts individual messages as they arrive
            try { 
                messagingTemplate.convertAndSendToUser(sid, "/queue/chat", payload, accessor.getMessageHeaders()); 
            }
            catch (Exception e) {
                //TODO: this doesn't get triggered... why not?  Probably a memory leak somewhere...
                log.info("Connection closed: " + sid, e);
                sidSet.remove(sid);
            }
        }
    }
    //////////////  END REVERSE COMPATIBILITY  //////////////
}
