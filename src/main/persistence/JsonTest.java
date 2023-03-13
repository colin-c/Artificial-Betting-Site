package persistence;

import model.Bet;
import model.Player;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonTest {
    protected void checkPlayer(String name, int fund, Player player) {
        assertEquals(name, player.getName());
        assertEquals(fund, player.getFund());
    }

    protected void checkBet(String title, String description, ArrayList<Player> players, Bet bet) {
        assertEquals(title, bet.getBetTitle());
        assertEquals(description, bet.getBetDescription());
        assertIterableEquals(players, bet.getPlayers());
    }

}