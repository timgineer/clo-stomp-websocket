package us.neuner.clo.message;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import us.neuner.clo.server.PlayerInfo;

public class GameSetupMessage extends Message {

	@JsonProperty
    private List<PlayerInfo> players; 
	
    @JsonCreator
	public GameSetupMessage(String psid, List<PlayerInfo> players) {
		super(psid);
		this.players = new ArrayList<PlayerInfo>(players);
	}

    public PlayerInfo getPlayers(int index) {
        return players.get(index);
    }
}
