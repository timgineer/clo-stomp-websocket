package us.neuner.clo.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import us.neuner.clo.common.SuggestionInfo;

/**
 * @author Jarod Neuner <jarod@neuner.us>
 * Encapsulates the endGame message.
 */
@JsonPropertyOrder({ "psid", "victor", "solution" })
public class EndGameMessage extends Message {

	private final String victor;
	private final SuggestionInfo solution;
	
	/**
     * Creates a new @see EndGameMessage object.
     * @param psid the player-session identifier associated with this message
	 * @param victor the winning player (may be null - no victor)
	 * @param solution the solution cards for this game session
	 */
    @JsonCreator
	public EndGameMessage(
			@JsonProperty("psid") String psid, 
			@JsonProperty("victor") String victor, 
			@JsonProperty("solution") SuggestionInfo solution) {
		super(psid);
		this.victor = victor;
		this.solution = solution;
	}

    /**
     * Returns the location of the murder
     * @return The @see GameEntityId of a weapon
     */
	public String getVictor() {
		return victor;
	}

    /**
     * Returns the solution for a game session.
     * @return A @see SuggestionInfo that contains the game session solution
     */
	public SuggestionInfo getSolution() {
		return this.solution;
	}

}
