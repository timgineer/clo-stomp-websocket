package us.neuner.clo.common;

public class PlayerInfo {
	
	private String playerName;
	private GameEntityId pieceName;
	private Boolean active;
	private Boolean hasAccused;

	public PlayerInfo(String playerName) {
		setPlayerName(playerName);
		setPieceName(GameEntityId.InvalidValue);
		setActive(false);
		setHasAccused(false);
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public GameEntityId getPieceName() {
		return pieceName;
	}

	public void setPieceName(GameEntityId pieceName) {
		this.pieceName = pieceName;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getHasAccused() {
		return hasAccused;
	}

	public void setHasAccused(Boolean hasAccused) {
		this.hasAccused = hasAccused;
	}

}
