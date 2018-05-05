package us.neuner.clo.message;

import static org.junit.Assert.*;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
//import us.neuner.clo.common.GameEntityId;
import us.neuner.clo.common.PlayerInfo;

public class GameSetupMessageTest {

	@Test
	public void testGameSetupMessageSerialization() throws JsonProcessingException {
		
                String playerName1 = "Billy Bob";
                String playerName2 = "Uncle Sam";
                PlayerInfo player1 = new PlayerInfo(playerName1);
                PlayerInfo player2 = new PlayerInfo(playerName2);
//                player1.setPieceName(GameEntityId.MissScarlet);
//                player2.setPieceName(GameEntityId.LeadPipe);
//                player1.setActive(false);
//                player2.setActive(false);
//                player1.setHasAccused(false);
//                player2.setHasAccused(false);                
                        
                List<PlayerInfo> players = new ArrayList<>();
                players.add(player1);
                players.add(player2);         
                String psid = "ja84fgjk29f";
                
		
		GameSetupMessage gsm = new GameSetupMessage(psid, players);
		String json = new ObjectMapper().writeValueAsString(gsm);

                assertThat(json, containsString("gameSetup"));
		assertThat(json, containsString(psid));
		assertThat(json, containsString(player1.getPlayerName()));
                assertThat(json, containsString(player2.getPlayerName()));
		assertThat(json, containsString(player1.getPieceName().toString()));
                assertThat(json, containsString(player2.getPieceName().toString()));
		assertThat(json, containsString(player1.getActive().toString()));
                assertThat(json, containsString(player2.getActive().toString()));                
		assertThat(json, containsString(player1.getHasAccused().toString()));
                assertThat(json, containsString(player2.getHasAccused().toString()));
        }

	@Test
	public void testGameSetupMessageDeserialization() throws IOException {
                String name1 = "Billy Bob";
                String name2 = "Uncle Sam";
                String piece1 = "Miss Scarlet";
                String piece2 = "Mr. Green";                   
                String psid = "ja84fgjk29f";
                
		String json = "{\"type\":\"gameSetup\",\"psid\":\"" + psid + "\",\"players\":[" + 
                        "{\"playerName\":\"" + name1 + "\",\"pieceName\":\"" + piece1 +
                        "\",\"active\":\"false\",\"hasAccused\":\"false\"}," + 
                        "{\"playerName\":\"" + name2 + "\",\"pieceName\":\"" + piece2 +
                        "\",\"active\":\"false\",\"hasAccused\":\"false\"}]";


                GameSetupMessage gsm = new ObjectMapper().readerFor(GameSetupMessage.class).readValue(json);
		             
                assertEquals(psid, gsm.getPsid());
                PlayerInfo player1 = gsm.getPlayers(0);
                PlayerInfo player2 = gsm.getPlayers(1);
		assertEquals(name1, player1.getPlayerName());
                assertEquals(name2, player2.getPlayerName());
		assertEquals(false, player1.getActive());
                assertEquals(false, player2.getActive());
		assertEquals(false, player1.getHasAccused());
                assertEquals(false, player2.getHasAccused());
             
	}
}
