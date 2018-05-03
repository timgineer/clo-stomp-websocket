package us.neuner.clo.message;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({ 
	@Type(value = ClientJoinMessage.class, name = "clientJoin"), 
	@Type(value = GameSetupMessage.class, name = "gameSetup"),
	@Type(value = EndGameMessage.class, name = "endGame"),  
	@Type(value = ChatMessage.class, name = "chat"), 
	@Type(value = ChatMessageHistory.class, name = "chatHistory"),
})
public abstract class Message {

    private final String psid;

    /**
     * Creates a new @see Message object.
     * @param psid The player-session identifier associated with this message.
     */
    protected Message(String psid) {
        this.psid = psid;
    }

    public String getPsid() {
        return this.psid;
    }
}