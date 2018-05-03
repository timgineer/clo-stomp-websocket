package us.neuner.clo.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Jarod Neuner <jarod@neuner.us>
 *
 */
@JsonPropertyOrder({ "psid", "msg" })
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
