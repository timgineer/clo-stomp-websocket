package us.neuner.clo.message;

import java.util.*;

public class ChatMessageHistory {

    private List<ChatEntry> msgList;

    public ChatMessageHistory() {
        this.msgList = Collections.synchronizedList(new LinkedList<ChatEntry>());
    }

    public ChatMessageHistory(Collection<ChatEntry> msgList) {
        this.msgList = Collections.synchronizedList(new LinkedList<ChatEntry>(msgList));
    }

    public ChatEntry getMsg(int index) {
        return msgList.get(index);
    }
}
