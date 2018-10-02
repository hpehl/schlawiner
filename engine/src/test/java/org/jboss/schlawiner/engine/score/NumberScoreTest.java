package org.jboss.schlawiner.engine.score;

import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Players;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.jboss.schlawiner.engine.score.Score.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberScoreTest {
    private Player me;
    private Player computer;
    private Player foo;
    private Score one = new Score("1", 1);
    private Score two = new Score("2", 2);
    private NumberScore numberScore;

    @BeforeEach
    void setUp() {
        Players players = new Players();
        players.clear();
        me = new Player("Harald", true);
        computer = new Player("MacBook", false);
        foo = new Player("Foo", true);
        players.add(me);
        players.add(computer);

        numberScore = new NumberScore(0, 42, players);
    }

    @Test
    void newInstance() {
        assertEquals(42, numberScore.getNumber());
        assertEquals(EMPTY, numberScore.getScore(me));
        assertEquals(EMPTY, numberScore.getScore(computer));
        assertEquals(EMPTY, numberScore.getScore(foo));
        assertFalse(numberScore.hasScore(me));
        assertFalse(numberScore.hasScore(computer));
        assertFalse(numberScore.hasScore(foo));
    }

    @Test
    void scores() {
        numberScore.setScore(me, one);
        numberScore.setScore(foo, two);

        assertEquals(1, numberScore.getScore(me).getDifference());
        assertEquals(EMPTY, numberScore.getScore(computer));
        assertEquals(EMPTY, numberScore.getScore(foo));
        assertTrue(numberScore.hasScore(me));
        assertFalse(numberScore.hasScore(computer));
        assertFalse(numberScore.hasScore(foo));
    }
}
