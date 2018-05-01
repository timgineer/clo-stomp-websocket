package us.neuner.clo.server;

import java.lang.AutoCloseable;
import java.security.SecureRandom;
import java.util.SortedMap;
import java.util.TreeMap;

import java.util.Collections;
import javax.xml.bind.DatatypeConverter;

/**
 * PlayerDetail models player-specific game state within the context of a game session.
 * 
 * @author Jarod Neuner <jarod@neuner.us>
 */
public class PlayerDetail implements AutoCloseable {
	
	/**
	 * Static singleton map for @see {@link #getPlayerDetail(String)}.
	 */
	private static SortedMap<String, PlayerDetail> players;
	
	static {
		players = Collections.synchronizedSortedMap(new TreeMap<String, PlayerDetail>());
	} 
	
	/**
	 * Allows access to the singleton map for @see PlayerDetail instances.
	 * @param psid the 
	 * @return
	 */
	public static PlayerDetail getPlayerDetail(String psid) {
		return psid == null ? null : players.get(psid);
	}
	
	private final CloGameSession session;
	private final String psid;
	private final PlayerInfo playerInfo;

	private String sid;
	
	/**
	 * Creates a new PlayerDetail object.
	 * @param sid the initial session ID for this PlayerDetail object.
	 */
	public PlayerDetail(CloGameSession session, String sid, String playerName) {
		
		SecureRandom rng = new SecureRandom();
		byte[] b = new byte[15];
		rng.nextBytes(b);
		
		this.session = session;
		this.sid = sid;
		this.psid = DatatypeConverter.printBase64Binary(b);
		this.playerInfo = new PlayerInfo(playerName);
		
		players.put(psid, this);
	}

	/**
	 * Closes this resource, relinquishing any underlying resources.

	 * @throws Exception if this resource cannot be closed
	 */
	@Override
	public void close() throws Exception {
		players.remove(psid);
	}

	/**
	 * Get the @see CloGameSession that is associated with the current PlayerDetail object.
	 * @return a @see CloGameSession instance.
	 */
	public CloGameSession getSession() {
		return session;
	}

	/**
	 * Get the most recently session ID, which maps to a specific WebSocket connection.
	 * @return a session ID String
	 */
	public String getSid() {
		return sid;
	}

	/**
	 * Set the most recently session ID, which maps to a specific WebSocket connection.
	 * @param sid a session ID String
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}

	/**
	 * Get the player session ID, which identifies a unique player-session relationship.
	 * @return a player session id String
	 */
	public String getPsid() {
		return psid;
	}
	
	/**
	 * Get the @see PlayerInfo object for this instance.
	 * @return a @PlayerInfo object
	 */
	public PlayerInfo getPlayerInfo() {
		return playerInfo;
	}
}
