package us.neuner.clo.server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import us.neuner.clo.common.PlayerInfo;
import us.neuner.clo.message.ChatEntry;
import us.neuner.clo.message.ChatMessage;
import us.neuner.clo.message.ChatMessageHistory;
import us.neuner.clo.message.ClientJoinMessage;
import us.neuner.clo.message.GameSetupMessage;

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
		else {
			pd.getPlayerInfo().setPlayerName(join.getPlayerName());
		}
		
		sendGameSetup();
		sendChatHistory(pd);
		
		return true;
	}
	
	/**
	 * Handles chat messages from clients.
	 * @param chat the @see ChatMessage that the server received
	 * @param playerName player name for the client that sent the message
	 */
	public void chatMessageHandler(ChatMessage chat, String playerName) {
		String msg = chat.getMsg();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

		if ((msg != null) && !msg.isEmpty())
			msgList.add(new ChatEntry(playerName, msg, sdf.format(date)));
                
        for (PlayerDetail pd : this.players)
        	sendChatHistory(pd);
	}
	
	private void sendGameSetup() {
	    List<PlayerInfo> pInfoList = new ArrayList<PlayerInfo>(players.size());

        for (PlayerDetail pd : this.players)
        	pInfoList.add(pd.getPlayerInfo());
        
        for (PlayerDetail pd : this.players) {
        	String psid = pd.getPsid();
        	GameSetupMessage gsm = new GameSetupMessage(psid, pInfoList);
        	server.sendToClient(pd, gsm);
        }
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
