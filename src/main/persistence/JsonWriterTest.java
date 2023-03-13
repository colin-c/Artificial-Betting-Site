package persistence;

import model.Bet;
import model.Game;
import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {


    @Test
    void testWriterInvalidFile() {
        try {
            Game game = new Game("game", new ArrayList<>(), new ArrayList<>());
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Game game = new Game("game", new ArrayList<>(), new ArrayList<>());
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            game = reader.read();
            assertEquals("game", game.getTitle());
            assertTrue(game.getBets().isEmpty());
            assertTrue(game.getPlayers().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Game game = new Game("game", new ArrayList<>(), new ArrayList<>());
            game.addBet(new Bet("b", "d", new ArrayList<>()));
            game.addPlayer(new Player("n1", 0));
            game.addPlayer(new Player("n2", 10));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            game = reader.read();
            assertEquals("game", game.getTitle());
            ArrayList<Bet> bets = game.getBets();
            ArrayList<Player> players = game.getPlayers();
            assertEquals(1, bets.size());
            assertEquals(2, players.size());
            checkPlayer("n1", 0, players.get(0));
            checkBet("b", "d", new ArrayList<>(), bets.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}