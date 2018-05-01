package us.neuner.clo.server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import us.neuner.clo.message.ChatEntry;
import us.neuner.clo.message.ChatMessage;
import us.neuner.clo.message.ChatMessageHistory;
import us.neuner.clo.message.ClientJoinMessage;

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
	public Boolean clientJoinHandler(ClientJoinMessage join, String sid, PlayerDetail pd) {

		//TODO: handling for session that has reached max player count
		
		if (pd == null) {
			//TODO: handling for playerName collision
			pd = new PlayerDetail(this, sid, join.getPlayerName());
			players.add(pd);
		}
		
		//TODO: sendGameSetup
		sendChatHistory(pd);
		
		return true;
	}
	
	public void chatMessageHandler(ChatMessage chat) {
		String msg = chat.getMsg();

		if ((msg != null) && !msg.isEmpty())
			msgList.add(new ChatEntry(msg));
                
        for (PlayerDetail pd : players)
        	sendChatHistory(pd);
	}
	
	/**
	 * Sends the session chat history to the specified player.
	 * @param pd the player which will receive a @see ChatMessageHistory
	 */
	private void sendChatHistory(PlayerDetail pd) {
		
    	String psid = pd.getPsid();
    	if (psid != null) {
        	ChatMessageHistory h = new ChatMessageHistory(psid, msgList); 
        	server.sendToClient(pd, h);
    	}
	}
}
