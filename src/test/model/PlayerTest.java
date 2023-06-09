package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    Player player;

    @BeforeEach
    public void runBefore() {
        player = new Player("A", 0);
    }

    @Test
    void testConstructor() {
        assertEquals("A", player.getName());
        assertEquals(0, player.getFund());
    }

    @Test
    void testManuallyAddFund() {
        assertFalse(player.manuallyAddFund(-1));

        assertTrue(player.manuallyAddFund(15));
        assertEquals(15, player.getFund());

        assertTrue(player.manuallyAddFund(0));
        assertEquals(15,player.getFund());
    }

    @Test
    void testManuallyAddFundOne() {
        assertTrue(player.manuallyAddFund(1));
        assertEquals(1, player.getFund());
    }

    @Test
    void testManuallyAddFundMultiple() {
        assertTrue(player.manuallyAddFund(5));
        assertTrue(player.manuallyAddFund(1));
        assertTrue(player.manuallyAddFund(151));

        assertEquals(157, player.getFund());

        assertFalse(player.manuallyAddFund(-5));
        assertFalse(player.manuallyAddFund(-10));
        assertFalse(player.manuallyAddFund(-1));

        assertEquals(157, player.getFund());


    }

    @Test
    void testManuallyRemoveFund() {
        player.manuallyAddFund(100);

        assertFalse(player.manuallyRemoveFund(101));
        assertTrue(player.manuallyRemoveFund(50));
        assertEquals(50, player.getFund());
    }

    @Test
    void testManuallyRemoveFundOne() {
        player.manuallyAddFund(100);

        assertTrue(player.manuallyRemoveFund(1));
        assertEquals(99, player.getFund());
    }

    @Test
    void testManuallyRemoveFundMultiple() {
        player.manuallyAddFund(100);

        assertFalse(player.manuallyRemoveFund(101));
        assertTrue(player.manuallyRemoveFund(50));
        assertEquals(50, player.getFund());

        assertTrue(player.manuallyRemoveFund(49));
        assertEquals(1, player.getFund());
    }

    @Test
    void testChangeName() {
        assertEquals("A", player.getName());

        player.changeName("C");
        assertEquals("C", player.getName());

    }
}
