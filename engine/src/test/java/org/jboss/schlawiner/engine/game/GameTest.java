package org.jboss.schlawiner.engine.game;

import org.jboss.schlawiner.engine.algorithm.OperationAlgorithm;
import org.jboss.schlawiner.engine.score.Scoreboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static java.util.Arrays.asList;

class GameTest {

    private Player foo;
    private Player computer;
    private Players players;
    private Numbers numbers;
    private Settings settings;
    private Game game;

    @BeforeEach
    void setUp() {
        foo = new Player("foo", true);
        computer = new Player("computer", false);
        players = new Players(asList(foo, computer));
        numbers = new Numbers(new int[]{16, 23, 42});
        settings = new Settings();
        settings.setLevel(Level.HARD);
        game = new Game(players, numbers, new OperationAlgorithm(), settings);
    }

    @Test
    void humanVsComputerDraw() {
        // 16
        game.dice(new Dice(1, 2, 3));
        game.next(); // foo
        game.calculate("10 + 2 * 3");
        game.next(); // computer
        game.solve();

        // 23
        game.dice(new Dice(4, 3, 1));
        game.next(); // foo
        game.calculate("30 - 10 + 4");
        game.next(); // computer
        game.solve();

        // 42
        game.dice(new Dice(2, 5, 6));
        game.next(); // foo
        game.calculate("50 - 6 - 2");
        game.next(); // computer
        game.solve();

        // game over
        Scoreboard scoreboard = game.getScoreboard();
        out.printf("Winners: %s%nNumberscore: %s%nPlayerscore: %s%n", scoreboard.getWinners(),
            scoreboard.getNumberScores(), scoreboard.getPlayerScores());
    }
}
