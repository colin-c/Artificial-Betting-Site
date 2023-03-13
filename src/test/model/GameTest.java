package model;

import model.Bet;
import model.Game;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Player p1;
    private Bet b1;
    private Game game;


    @BeforeEach
    public void runBefore() {
        game = new Game("One", new ArrayList<>(), new ArrayList<>());
    }

    @Test
    void testConstructor() {
        assertEquals("One", game.getTitle());
        assertTrue(game.getBets().isEmpty());
        assertTrue(game.getPlayers().isEmpty());
    }

    @Test
    void testAddBet() {
        Bet b2 = new Bet("a","a", new ArrayList<>());

        game.addBet(b1);
        assertEquals(1, game.getBets().size());
        game.addBet(b2);
        assertEquals(2, game.getBets().size());
    }

    @Test
    void testAddPlayer() {
        Player p2 = new Player("a", 0);

        game.addPlayer(p1);
        assertEquals(1, game.getPlayers().size());
        game.addPlayer(p2);
        game.addPlayer(p1);
        assertEquals(3,game.getPlayers().size());
    }




}
