package us.neuner.clo.message;

public abstract class Message {

    private String psid;

    public Message(String psid) {
        this.psid = psid;
    }

    public String getPsid() {
        return psid;
    }
}
