package us.neuner.clo.message;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import us.neuner.clo.common.GameEntityId;
import us.neuner.clo.common.SuggestionInfo;

public class EndGameMessageTest {

	@Test
	public void testEndGameMessageSerialization() throws JsonProcessingException {
		String psid = "ja84fgjk30f";
		String victor = "Roger Rabbit";
		SuggestionInfo si = new SuggestionInfo(GameEntityId.InvalidValue, GameEntityId.MissScarlet, GameEntityId.InvalidValue);
		EndGameMessage cm = new EndGameMessage(psid, victor, si);
		
		String json = new ObjectMapper().writeValueAsString(cm);
		assertThat(json, containsString("endGame"));
		assertThat(json, containsString(psid));
		assertThat(json, containsString(victor));
		assertThat(json, containsString(si.getLocation().toString()));
		assertThat(json, containsString(si.getSuspect().toString()));
		assertThat(json, containsString(si.getWeapon().toString()));
	}

	@Test
	public void testEndGameMessageDeserialization() throws IOException {
		String psid = "ja84fgjk31f";
		String victor = "Roger Rabbit";
		SuggestionInfo si = new SuggestionInfo(GameEntityId.InvalidValue, GameEntityId.MissScarlet, GameEntityId.InvalidValue);
		String jsonFormat = "{''type'':''endGame'',''psid'':''%s'',''victor'':''%s'', ''solution'': { ''location'': ''%s'', ''suspect'': ''%s'', ''weapon'': ''%s'' }}";
		String json = String.format(jsonFormat, psid, victor, GameEntityId.InvalidValue, GameEntityId.MissScarlet, GameEntityId.InvalidValue);
		
		json = json.replace("''", "\"");
		// json = {"type":"endGame","psid":"ja84fgjk31f","victor":"Roger Rabbit","solution":{"location":""Invalid Value","suspect":"Miss Scarlet","weapon":"Invalid Value"}}";

		EndGameMessage eg = new ObjectMapper().readerFor(EndGameMessage.class).readValue(json);
		assertEquals(psid, eg.getPsid());
		assertEquals(victor, eg.getVictor());
		assertEquals(si, eg.getSolution());
	}

}
