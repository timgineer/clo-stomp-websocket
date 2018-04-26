package us.neuner.clo.server;

import java.lang.AutoCloseable;
import java.security.SecureRandom;
import java.util.SortedMap;
import java.util.TreeMap;

import java.util.Collections;

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
	 * Convert an array of bytes to a hexadecimal @see String.
	 * @param bytes	An array of bytes.
	 * @return A hexadecimal @see String.
	 */
	private static String ByteArrayToString(byte[] bytes)
	{
	    char[] c = new char[bytes.length * 2];
	    int b;
	    for (int i = 0; i < bytes.length; i++) {
	        b = bytes[i] >> 4;
	        c[i * 2] = (char)(55 + b + (((b-10)>>31)&-7));
	        b = bytes[i] & 0xF;
	        c[i * 2 + 1] = (char)(55 + b + (((b-10)>>31)&-7));
	    }
	    return new String(c);
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
	private String sid;
	
	/**
	 * Creates a new PlayerDetail object.
	 * @param sid the initial session ID for this PlayerDetail object.
	 */
	public PlayerDetail(CloGameSession session, String sid) {
		
		SecureRandom rng = new SecureRandom();
		byte[] b = new byte[16];
		rng.nextBytes(b);
		
		this.session = session;
		this.sid = sid;
		this.psid = ByteArrayToString(b);
		
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
	 * Get the most recently session ID, which maps to specific WebSocket connections.
	 * @return a session ID String
	 */
	public String getSid() {
		return sid;
	}

	/**
	 * Get the player session ID, which identifies a unique player-session relationship.
	 * @return a player session id String
	 */
	public String getPsid() {
		return psid;
	}
}
