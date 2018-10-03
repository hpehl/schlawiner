package org.jboss.schlawiner.engine.score;

import org.jboss.schlawiner.engine.game.Numbers;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Players;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreboardTest {
    private Player foo;
    private Player bar;
    private Players players;
    private Numbers numbers;
    private Scoreboard scoreboard;

    @BeforeEach
    void setUp() {
        foo = new Player("foo", true);
        bar = new Player("bar", true);
        players = new Players(asList(foo, bar));
        numbers = new Numbers(8);
        scoreboard = new Scoreboard(players, numbers);
    }

    @Test
    void newInstance() {
        assertFalse(scoreboard.getNumberScores().isEmpty());
        assertFalse(scoreboard.getPlayerScores().isEmpty());
    }

    @Test
    void setScore() {
        scoreboard.setScore(0, foo, "1", 1);
        assertEquals(1, scoreboard.getNumberScores().get(0).getScore(foo).getDifference());
    }

    @Test
    void oneWinner() {
        scoreboard.setScore(0, foo, "0", 0);
        scoreboard.setScore(0, bar, "1", 1);
        scoreboard.setScore(1, foo, "1", 1);
        scoreboard.setScore(1, bar, "1", 1);

        assertEquals(1, scoreboard.getWinners().size());
        assertEquals(foo, scoreboard.getWinners().get(0));
    }

    @Test
    void twoWinner() {
        scoreboard.setScore(0, foo, "0", 0);
        scoreboard.setScore(0, bar, "0", 0);
        scoreboard.setScore(1, foo, "1", 1);
        scoreboard.setScore(1, bar, "1", 1);

        assertEquals(2, scoreboard.getWinners().size());
        assertTrue(scoreboard.getWinners().contains(foo));
        assertTrue(scoreboard.getWinners().contains(bar));
    }
}
