package us.neuner.clo.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import us.neuner.clo.message.ChatEntry;
import us.neuner.clo.message.ChatMessage;
import us.neuner.clo.message.ChatMessageHistory;

public class CloGameSession {
    private Log log = LogFactory.getLog(CloGameSession.class);
    
	private final CloGameServer server;
	private final List<PlayerDetail> players; 
    private List<ChatEntry> msgList;

	public CloGameSession(CloGameServer server) {
		this.server = server;
		this.players = new ArrayList<PlayerDetail>();
        this.msgList = new LinkedList<ChatEntry>();
	}
	
	// TODO: Refactor this to the ClientJoinMessage...
	public void clientJoinHandler(PlayerDetail chat) {
		log.info("CloGameSession.clientJoinHandler");
		players.add(chat);
	}
	
	public void chatMessageHandler(ChatMessage chat) {
		log.info("CloGameSession.chatMessageHandler");

		msgList.add(new ChatEntry(chat.getMsg()));
                
        for (PlayerDetail pd : players) {
        	ChatMessageHistory h = new ChatMessageHistory(pd.getPsid(), msgList); 
        	server.sendToClient(pd, h);
        }
	}
}