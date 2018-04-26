package us.neuner.clo.message;

import java.util.*;

public class ChatMessageHistory extends Message {

    private List<ChatEntry> msgList;

    public ChatMessageHistory(String psid) {
    	super(psid);
        this.msgList = new LinkedList<ChatEntry>();
    }

    public ChatMessageHistory(String psid, Collection<ChatEntry> msgList) {
    	super(psid);
        this.msgList = new LinkedList<ChatEntry>(msgList);
    }

    public ChatEntry getMsg(int index) {
        return msgList.get(index);
    }
}
