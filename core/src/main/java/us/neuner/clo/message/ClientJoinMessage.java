package us.neuner.clo.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientJoinMessage extends Message {

	private String sessionPassword;
	private String playerName;

    @JsonCreator
	public ClientJoinMessage(@JsonProperty("psid") String psid, @JsonProperty("sessionPassword") String sessionPassword, @JsonProperty("playerName") String playerName) {
		super(psid);
		this.setSessionPassword(sessionPassword);
		this.setPlayerName(playerName);
	}

	public String getSessionPassword() {
		return sessionPassword;
	}

	public void setSessionPassword(String sessionPassword) {
		this.sessionPassword = sessionPassword;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

}
