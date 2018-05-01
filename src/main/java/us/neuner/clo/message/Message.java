package us.neuner.clo.message;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
@JsonSubTypes({ 
	@Type(value = ChatMessage.class, name = "chat"), 
	@Type(value = ChatMessageHistory.class, name = "chatHistory"),
})
public abstract class Message {

    private String psid;

    protected Message(String psid) {
        this.psid = psid;
    }

    public String getPsid() {
        return psid;
    }
}