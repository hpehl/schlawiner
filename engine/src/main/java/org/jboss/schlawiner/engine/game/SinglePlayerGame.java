package org.jboss.schlawiner.engine.game;

import javax.annotation.Nullable;

import org.jboss.schlawiner.engine.algorithm.Algorithm;
import org.jboss.schlawiner.engine.algorithm.Solution;
import org.jboss.schlawiner.engine.algorithm.Solutions;
import org.jboss.schlawiner.engine.score.Scoreboard;

import static java.lang.Math.abs;

public class SinglePlayerGame implements Game {

    private final Players players;
    private final Numbers numbers;
    private final Algorithm algorithm;
    private final Settings settings;
    private Dice dice;
    private Scoreboard scoreboard;

    public SinglePlayerGame(Players players, Numbers numbers, Algorithm algorithm, Settings settings) {
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

    @Override
    public void next() {
        players.next();
        if (players.isFirst()) {
            numbers.next();
        }
    }

    @Override
    public boolean hasNext() {
        return numbers.hasNext();
    }

    @Override
    public void dice(Dice dice) {
        this.dice = dice;
    }

    @Override
    public void retry() {
        if (players.current().isHuman() && players.current().getRetries() > 0) {
            players.current().retry();
            dice(settings.isAutoDice() ? new Dice() : null);
        }
    }

    @Override
    public void penalty() {
        score("penalty", settings.getPenalty());
    }

    @Override
    public void solution(@Nullable String term) {
        if (players.current().isHuman()) {
            if (term == null) {
                throw new NullPointerException("Term must not be null for human player.");
            }
            int result = Calculator.calculate(term, dice);
            score(term, abs(result - numbers.current()));
        } else {
            if (term != null) {
                throw new IllegalArgumentException("Term must be null for computer player.");
            }
            Solutions solutions = algorithm.compute(dice.numbers[0], dice.numbers[1], dice.numbers[2],
                numbers.current());
            Solution solution = solutions.bestSolution(settings.getLevel());
            score(solution.getTerm(), abs(solution.getValue() - numbers.current()));
        }
    }

    private void score(String term, int difference) {
        scoreboard.setScore(numbers.index(), players.current(), term, difference);
    }

    @Override
    public Dice getDice() {
        return dice;
    }

    @Override
    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
