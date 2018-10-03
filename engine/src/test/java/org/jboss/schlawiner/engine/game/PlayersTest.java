package org.jboss.schlawiner.engine.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayersTest {
    private Player foo;
    private Player bar;
    private Players players;

    @BeforeEach
    void setUp() {
        foo = new Player("foo", true);
        bar = new Player("bar", true);
        players = new Players(asList(foo, bar));
    }

    @Test
    void newInstance() {
        assertFalse(players.isFirst());
        assertFalse(players.isLast());
        assertFalse(players.isEmpty());
        assertNull(players.current());
    }

    @Test
    void lifecycle() {
        assertFalse(players.isFirst());
        assertFalse(players.isLast());
        assertEquals(2, players.size());
        assertNull(players.current());

        assertEquals(foo, players.next());
        assertTrue(players.isFirst());
        assertFalse(players.isLast());
        assertEquals(2, players.size());
        assertEquals(foo, players.current());

        assertEquals(bar, players.next());
        assertFalse(players.isFirst());
        assertTrue(players.isLast());
        assertEquals(2, players.size());
        assertEquals(bar, players.current());

        assertEquals(foo, players.next());
        assertTrue(players.isFirst());
        assertFalse(players.isLast());
        assertEquals(2, players.size());
        assertEquals(foo, players.current());
    }
}
