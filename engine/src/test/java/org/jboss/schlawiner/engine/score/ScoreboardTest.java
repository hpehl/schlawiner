package org.jboss.schlawiner.engine.score;

import org.jboss.schlawiner.engine.game.Numbers;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Players;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreboardTest {
    private Players players;
    private Player me;
    private Player computer;
    private Numbers numbers;
    private Scoreboard scoreboard;

    @BeforeEach
    void setUp() {
        players = new Players();
        players.clear();
        me = new Player("Harald", true);
        computer = new Player("MacBook", false);
        players.add(me);
        players.add(computer);
        numbers = new Numbers();
        scoreboard = new Scoreboard();
    }

    @Test
    void newInstance() {
        assertTrue(scoreboard.getNumberScores().isEmpty());
    }

    @Test
    void reset() {
        numbers.reset(8);
        scoreboard.reset(players, numbers);
        assertEquals(8, scoreboard.getNumberScores().size());
    }

    @Test
    void setScore() {
        numbers.reset(8);
        scoreboard.reset(players, numbers);
        scoreboard.setScore(0, me, "1", 1);
        assertEquals(1, scoreboard.getNumberScores().get(0).getScore(me).getDifference());
    }

    @Test
    void oneWinner() {
        numbers.reset(2);
        scoreboard.reset(players, numbers);
        scoreboard.setScore(0, me, "0", 0);
        scoreboard.setScore(0, computer, "1", 1);
        scoreboard.setScore(1, me, "1", 1);
        scoreboard.setScore(1, computer, "1", 1);

        assertEquals(1, scoreboard.getWinners().size());
        assertEquals(me, scoreboard.getWinners().get(0));
    }

    @Test
    void twoWinner() {
        numbers.reset(2);
        scoreboard.reset(players, numbers);
        scoreboard.setScore(0, me, "0", 0);
        scoreboard.setScore(0, computer, "0", 0);
        scoreboard.setScore(1, me, "1", 1);
        scoreboard.setScore(1, computer, "1", 1);

        assertEquals(2, scoreboard.getWinners().size());
        assertTrue(scoreboard.getWinners().contains(me));
        assertTrue(scoreboard.getWinners().contains(computer));
    }
}
