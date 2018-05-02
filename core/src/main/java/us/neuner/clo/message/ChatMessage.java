package us.neuner.clo.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChatMessage extends Message {

    private String msg;

    @JsonCreator
    public ChatMessage(@JsonProperty("psid") String psid, @JsonProperty("msg") String msg) {
    	super(psid);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
