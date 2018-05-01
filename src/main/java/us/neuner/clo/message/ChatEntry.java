package us.neuner.clo.message;

public class ChatEntry {

	private final String playerName;
    private final String msg;
    private final String time;

    public ChatEntry(String playerName, String msg, String time) {
    	this.playerName = playerName;
        this.msg = msg;
        this.time = time;
    }
    
    public String getPlayerName() {
    	return playerName;
    }

    public String getMsg() {
        return msg;
    }
    
    public String getTime() {
    	return time;
    }
}
