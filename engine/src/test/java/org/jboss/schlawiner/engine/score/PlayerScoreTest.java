package org.jboss.schlawiner.engine.score;

import org.jboss.schlawiner.engine.game.Numbers;
import org.jboss.schlawiner.engine.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerScoreTest {
    private Player foo;
    private Numbers numbers;
    private Score one = new Score("1", 1);
    private Score two = new Score("2", 2);
    private PlayerScore playerScore;

    @BeforeEach
    void setUp() {
        foo = new Player("foo", true);
        numbers = new Numbers(8);
        playerScore = new PlayerScore(foo, numbers);
    }

    @Test
    void newInstance() {
        // assertEquals(42, playerScore.getNumber());
        // assertEquals(EMPTY, playerScore.getScore(me));
        // assertEquals(EMPTY, playerScore.getScore(computer));
        // assertEquals(EMPTY, playerScore.getScore(foo));
        // assertFalse(playerScore.hasScore(me));
        // assertFalse(playerScore.hasScore(computer));
        // assertFalse(playerScore.hasScore(foo));
    }

    @Test
    void scores() {
        // playerScore.setScore(me, one);
        // playerScore.setScore(foo, two);
        //
        // assertEquals(1, playerScore.getScore(me).getDifference());
        // assertEquals(EMPTY, playerScore.getScore(computer));
        // assertEquals(EMPTY, playerScore.getScore(foo));
        // assertTrue(playerScore.hasScore(me));
        // assertFalse(playerScore.hasScore(computer));
        // assertFalse(playerScore.hasScore(foo));
    }
}
