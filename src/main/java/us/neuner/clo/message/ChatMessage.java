package us.neuner.clo.message;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ChatMessage extends Message {

    private String msg;

    @JsonCreator
    public ChatMessage(String psid, String msg) {
    	super(psid);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
