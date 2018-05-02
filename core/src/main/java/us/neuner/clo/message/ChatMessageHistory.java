package us.neuner.clo.message;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ChatMessageHistory extends Message {

	@JsonProperty
    private List<ChatEntry> msgList;

    public ChatMessageHistory(String psid) {
    	super(psid);
        this.msgList = new ArrayList<ChatEntry>();
    }

    @JsonCreator
    public ChatMessageHistory(@JsonProperty("psid") String psid, @JsonProperty("msgList") List<ChatEntry> msgList) {
    	super(psid);
        this.msgList = new ArrayList<ChatEntry>(msgList);
    }

    public ChatEntry getMsg(int index) {
        return msgList.get(index);
    }
}
