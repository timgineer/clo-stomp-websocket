package us.neuner.clo.message;

import static org.junit.Assert.*;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatMessageTest {

	@Test
	public void testChatMessageSerialization() throws JsonProcessingException {
		String psid = "ja84fgjk29f";
		String msg = "TEST MESSAGE";
		ChatMessage cm = new ChatMessage(psid, msg);
		
		String json = new ObjectMapper().writeValueAsString(cm);
		assertThat(json, containsString("chat"));
		assertThat(json, containsString(psid));
		assertThat(json, containsString(msg));
	}

	@Test
	public void testChatMessageDeserialization() throws IOException {
		String psid = "ja84fgjk29f";
		String msg = "TEST MESSAGE";
		String json = "{\"type\":\"chat\",\"psid\":\"" + psid + "\",\"msg\":\"" + msg + "\"}";

		ChatMessage cm = new ObjectMapper().readerFor(ChatMessage.class).readValue(json);
		assertEquals(psid, cm.getPsid());
		assertEquals(msg, cm.getMsg());
	}
}
