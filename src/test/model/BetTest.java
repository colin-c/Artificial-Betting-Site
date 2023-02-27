package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class BetTest {

    Bet bet;
    String title;
    String description;
    ArrayList<Player> players;

    Player p1 = new Player("A", 100);
    Player p2 = new Player("B", 1000);
    Player p3 = new Player("C", 500);

    @BeforeEach
    public void runBefore() {
        title = "Bet Title";
        description = "A mini-description of the bet";
        players = new ArrayList<>();
        bet = new Bet(title, description, new ArrayList<>());

    }

    @Test
    void testConstructor() {
        assertEquals("Bet Title", bet.getBetTitle());
        assertEquals("A mini-description of the bet", bet.getBetDescription());
        assertEquals(0, bet.getTotalPot());
        assertEquals(new ArrayList<>(), bet.getPlayers());
    }

    @Test
    void testAddPlayer() {

        assertEquals(0, bet.getPlayers().size());

        assertTrue(bet.addPlayer(p1));
        assertEquals(1, bet.getPlayers().size());

        assertFalse(bet.addPlayer(p1));
        assertEquals(1, bet.getPlayers().size());

        assertTrue(bet.addPlayer(p2));
        assertEquals(2, bet.getPlayers().size());

    }

    @Test
    void testRemovePlayer() {
        bet.addPlayer(p1);
        bet.addPlayer(p2);

        assertEquals(2, bet.getPlayers().size());

        assertTrue(bet.removePlayer(p1));
        assertEquals(1, bet.getPlayers().size());

        assertFalse(bet.removePlayer(p3));
        assertEquals(1, bet.getPlayers().size());
        assertFalse(bet.removePlayer(p1));
        assertEquals(1, bet.getPlayers().size());

        assertTrue(bet.removePlayer(p2));
        assertEquals(0, bet.getPlayers().size());
    }

    @Test
    void testAddTotalPot() {
        assertEquals(0, bet.getTotalPot());

        bet.addTotalPot(0);
        assertEquals(0, bet.getTotalPot());

        bet.addTotalPot(100);
        assertEquals(100, bet.getTotalPot());

        bet.addTotalPot(50);
        bet.addTotalPot(25);
        assertEquals(175, bet.getTotalPot());

    }

    @Test
    void testWinBet() {
        bet.addPlayer(p1);
        bet.addTotalPot(100);

        assertEquals(100, p1.getFund());

        bet.winBet(p1);
        assertEquals(0, bet.getTotalPot());
        assertEquals(200, p1.getFund());

    }

    @Test
    void testChangeBetTitle() {
        assertEquals("Bet Title", bet.getBetTitle());

        bet.changeBetTitle("Winner Winner");
        assertEquals("Winner Winner", bet.getBetTitle());

    }

    @Test
    void testChangeBetDescription() {
        assertEquals("A mini-description of the bet", bet.getBetDescription());

        bet.changeBetDescription("This is a new description for the bet");
        assertEquals("This is a new description for the bet", bet.getBetDescription());

    }

}
