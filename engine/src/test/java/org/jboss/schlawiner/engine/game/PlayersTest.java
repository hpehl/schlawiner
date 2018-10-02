package org.jboss.schlawiner.engine.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayersTest {
    private Player me;
    private Player computer;
    private Players players;

    @BeforeEach
    void setUp() {
        players = new Players();
        me = new Player("Harald", true);
        computer = new Player("MacBook", false);
    }

    @Test
    void newInstance() {
        assertFalse(players.isFirst());
        assertFalse(players.isLast());
        assertTrue(players.isEmpty());
        assertNull(players.current());
    }

    @Test
    void lifecycle() {
        players.add(me);
        players.add(computer);
        assertFalse(players.isFirst());
        assertFalse(players.isLast());
        assertEquals(2, players.size());
        assertNull(players.current());

        players.reset();
        assertFalse(players.isFirst());
        assertFalse(players.isLast());
        assertEquals(2, players.size());
        assertNull(players.current());

        assertEquals(me, players.next());
        assertTrue(players.isFirst());
        assertFalse(players.isLast());
        assertEquals(2, players.size());
        assertEquals(me, players.current());

        assertEquals(computer, players.next());
        assertFalse(players.isFirst());
        assertTrue(players.isLast());
        assertEquals(2, players.size());
        assertEquals(computer, players.current());

        assertEquals(me, players.next());
        assertTrue(players.isFirst());
        assertFalse(players.isLast());
        assertEquals(2, players.size());
        assertEquals(me, players.current());

        players.remove(computer);
        assertEquals(1, players.size());
        assertEquals(me, players.current());

        players.remove(me);
        assertTrue(players.isEmpty());
        assertNull(players.current());
    }
}
