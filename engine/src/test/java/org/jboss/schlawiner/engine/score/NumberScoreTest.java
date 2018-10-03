package org.jboss.schlawiner.engine.score;

import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Players;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.jboss.schlawiner.engine.score.Score.EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberScoreTest {
    private Player foo;
    private Player bar;
    private Players players;
    private Score one = new Score("1", 1);
    private Score two = new Score("2", 2);
    private NumberScore numberScore;

    @BeforeEach
    void setUp() {
        foo = new Player("foo", true);
        bar = new Player("bar", true);
        players = new Players(asList(foo, bar));
        numberScore = new NumberScore(0, 42, players);
    }

    @Test
    void newInstance() {
        assertEquals(42, numberScore.getNumber());
        assertEquals(EMPTY, numberScore.getScore(foo));
        assertEquals(EMPTY, numberScore.getScore(bar));
        assertFalse(numberScore.hasScore(foo));
        assertFalse(numberScore.hasScore(bar));
    }

    @Test
    void scores() {
        numberScore.setScore(foo, one);
        numberScore.setScore(bar, two);

        assertEquals(1, numberScore.getScore(foo).getDifference());
        assertEquals(2, numberScore.getScore(bar).getDifference());
        assertTrue(numberScore.hasScore(foo));
        assertTrue(numberScore.hasScore(bar));
    }
}
