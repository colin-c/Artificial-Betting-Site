package persistence;

import model.Bet;
import model.Game;
import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Game game = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            Game game = reader.read();
            assertEquals("game", game.getTitle());
            assertTrue(game.getBets().isEmpty());
            assertTrue(game.getPlayers().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            Game game = reader.read();
            assertEquals("game", game.getTitle());
            ArrayList<Bet> bets = game.getBets();
            ArrayList<Player> players = game.getPlayers();
            assertEquals(1, bets.size());
            assertEquals(2, players.size());
            checkBet("b", "d", new ArrayList<>(), bets.get(0));
            checkPlayer("n2",10, players.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

//    @Test
//    void testReader() {
//        JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
//        try {
//            Game game = reader.read();
//            assertEquals("game", game.getTitle());
//            ArrayList<Bet> bets = game.getBets();
//            ArrayList<Player> players = game.getPlayers();
//            checkBet("b1","d1", players, bets.get(0));
//        } catch (IOException e) {
//            fail("Exception should not have been thrown");
//        }
//    }
}