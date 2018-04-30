package us.neuner.clo.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import us.neuner.clo.message.ChatEntry;
import us.neuner.clo.message.ChatMessage;
import us.neuner.clo.message.ChatMessageHistory;

public class CloGameSession {
    
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
		
		players.add(chat);
	}
	
	public void chatMessageHandler(ChatMessage chat) {

		msgList.add(new ChatEntry(chat.getMsg()));
                
        for (PlayerDetail pd : players) {
        	String psid = pd.getPsid();
        	if (psid != null) {
	        	ChatMessageHistory h = new ChatMessageHistory(psid, msgList); 
	        	server.sendToClient(pd, h);
        	}
        }
	}
}
