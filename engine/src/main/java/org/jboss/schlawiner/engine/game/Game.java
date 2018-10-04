package org.jboss.schlawiner.engine.game;

import org.jboss.schlawiner.engine.algorithm.Algorithm;
import org.jboss.schlawiner.engine.algorithm.Solution;
import org.jboss.schlawiner.engine.algorithm.Solutions;
import org.jboss.schlawiner.engine.score.Scoreboard;

import static java.lang.Math.abs;

public class Game {

    private final Players players;
    private final Numbers numbers;
    private final Algorithm algorithm;
    private final Settings settings;
    private Dice dice;
    private Scoreboard scoreboard;

    public Game(Players players, Numbers numbers, Algorithm algorithm, Settings settings) {
        this.players = players;
        this.numbers = numbers;
        this.algorithm = algorithm;
        this.settings = settings;
        this.scoreboard = new Scoreboard(players, numbers);

        for (Player player : this.players) {
            if (player.isHuman()) {
                player.setRetries(settings.getRetries());
            }
        }
    }

    public void next() {
        players.next();
        if (players.isFirst()) {
            numbers.next();
        }
    }

    public boolean hasNext() {
        return numbers.hasNext();
    }

    public void dice(Dice dice) {
        this.dice = dice;
    }

    public void retry() {
        if (players.current().isHuman() && players.current().getRetries() > 0) {
            players.current().retry();
            dice(settings.isAutoDice() ? new Dice() : null);
        }
    }

    public void penalty() {
        score("penalty", settings.getPenalty());
    }

    public void timeout() {
        score("timeout", settings.getPenalty());
    }

    public void calculate(String term) {
        if (!players.current().isHuman()) {
            throw new IllegalStateException("Current player is not human.");
        } else {
            int result = Calculator.calculate(term, dice);
            score(term, abs(result - numbers.current()));
        }
    }

    public void solve() {
        if (players.current().isHuman()) {
            throw new IllegalStateException("Current player is human.");
        } else {
            Solutions solutions = algorithm.compute(dice.numbers[0], dice.numbers[1], dice.numbers[2],
                numbers.current());
            Solution solution = solutions.bestSolution(settings.getLevel());
            score(solution.getTerm(), abs(solution.getValue() - numbers.current()));
        }
    }

    private void score(String term, int difference) {
        scoreboard.setScore(numbers.index(), players.current(), term, difference);
    }

    public Players getPlayers() {
        return players;
    }

    public Numbers getNumbers() {
        return numbers;
    }

    public Settings getSettings() {
        return settings;
    }

    public Dice getDice() {
        return dice;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
