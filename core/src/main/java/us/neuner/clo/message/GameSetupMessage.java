package us.neuner.clo.message;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import us.neuner.clo.common.PlayerInfo;

public class GameSetupMessage extends Message {

    private List<PlayerInfo> players; 
	
    @JsonCreator
	public GameSetupMessage(@JsonProperty("psid") String psid, @JsonProperty("players") List<PlayerInfo> players) {
		super(psid);
		this.players = new ArrayList<PlayerInfo>(players);
	}

    public PlayerInfo getPlayers(int index) {
        return players.get(index);
    }
}
