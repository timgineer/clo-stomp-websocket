package us.neuner.clo.message;

public class ChatMessage extends Message {

    private String msg;

    public ChatMessage(String psid, String msg) {
    	super(psid);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
