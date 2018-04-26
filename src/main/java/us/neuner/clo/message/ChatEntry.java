package us.neuner.clo.message;

public class ChatEntry {

    private String msg;

    public ChatEntry() {
    	this.msg = "";
    }

    public ChatEntry(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
