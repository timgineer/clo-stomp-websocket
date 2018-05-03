package us.neuner.clo.common;

/**
 * @author Jarod Neuner <jarod@neuner.us>
 * Maps a game piece to a game board location.
 */
public class PieceInfo {

	private final GameEntityId id;
	private GameEntityId loc;
	
	public PieceInfo(GameEntityId id, GameEntityId loc) {

		this.id = id;
		this.loc = loc;
	}

	public GameEntityId getLoc() {
		return this.loc;
	}

	public void setLoc(GameEntityId loc) {
		this.loc = loc;
	}

	public GameEntityId getId() {
		return this.id;
	}

	static Boolean isSuspect(GameEntityId id) {
		switch (id) {
			//TODO: Add remaining suspects 
			case MissScarlet:
				return true;
			default:
				break;
		}
		return false;
	}

	static Boolean isWeapon(GameEntityId id) {
		switch (id) {
			//TODO: Add remaining weapons
			case LeadPipe:
				return true;
			default:
				break;
		}
		return false;
	}

	static Boolean isLocation(GameEntityId id) {
		return isRoom(id) || isHallway(id) || isStartingLocation(id);
	}

	static Boolean isRoom(GameEntityId id) {
		switch (id) {
			//TODO: Add remaining Rooms
			case BilliardRoom:
				return true;
			default:
				break;
		}
		return false;
	}

	static Boolean isHallway(GameEntityId id) {
		switch (id) {
			//TODO: Add remaining hallways
			case H01:
				return true;
			default:
				break;
		}
		return false;
	}

	static Boolean isStartingLocation(GameEntityId id) {
		switch (id) {
			//TODO: Add remaining starting locations
			case MissScarletStart:
				return true;
			default:
				break;
		}
		return false;
	}
}
