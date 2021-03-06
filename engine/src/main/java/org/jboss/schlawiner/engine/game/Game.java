package org.jboss.schlawiner.engine.game;

import org.jboss.schlawiner.engine.algorithm.Algorithm;
import org.jboss.schlawiner.engine.algorithm.Solution;
import org.jboss.schlawiner.engine.algorithm.Solutions;
import org.jboss.schlawiner.engine.score.Scoreboard;

import static java.lang.Math.abs;

/** Implements the basic game workflow w/o timout handling. Timeouts are meant to be implemented outside this class. */
public class Game {

    private final Players players;
    private final Numbers numbers;
    private final Algorithm algorithm;
    private final Settings settings;
    private Dice dice;
    private Scoreboard scoreboard;
    private boolean canceled;

    /** Starts a new game. The retry counter of all human players is set to {@link Settings#getRetries()}. */
    public Game(Players players, Numbers numbers, Algorithm algorithm, Settings settings) {
        this.players = players;
        this.numbers = numbers;
        this.algorithm = algorithm;
        this.settings = settings;
        this.scoreboard = new Scoreboard(players, numbers);
        this.canceled = false;

        for (Player player : this.players) {
            if (player.isHuman()) {
                player.setRetries(settings.getRetries());
            }
        }
    }

    /** Passes the dice to the next player. If the player is the first player, then it's the next number's turn. */
    public void next() {
        players.next();
        if (players.isFirst()) {
            numbers.next();
        }
    }

    /**
     * @return {@code true} if there are more numbers or if it's not the last player and the game was not canceled,
     * {@code false} otherwise
     */
    public boolean hasNext() {
        return (numbers.hasNext() || !players.isLast()) && !canceled;
    }

    /** Sets the specified dice numbers. */
    public void dice(Dice dice) {
        this.dice = dice;
    }

    /**
     * If the current player is human and has retries left, its retry count is decreased and new dice numbers are set.
     * Otherwise this method does nothing.
     *
     * @return {@code true} if retry was successful, {@code false} otherwise
     */
    public boolean retry() {
        Player currentPlayer = players.current();
        if (currentPlayer.isHuman() && currentPlayer.getRetries() > 0) {
            currentPlayer.retry();
            dice(new Dice());
            return true;
        }
        return false;
    }

    /**
     * Skips the current number and scores {@link Settings#getPenalty()} points as penalty. Does <strong>not</strong>
     * call {@link #next()}
     */
    public void skip() {
        score("Skipped", settings.getPenalty());
    }

    /** Cancels this game */
    public void cancel() {
        canceled = true;
    }

    /** Scores {@link Settings#getPenalty()} points as penalty. Meant to be called after a timeout. */
    public void timeout() {
        score("Timeout", settings.getPenalty());
    }

    /**
     * Calculates the specified term for the current dice numbers and current target number. Stores the difference in
     * the score board.
     *
     * Meant to be called for human players.
     *
     * @return the difference between the calculated solution and the current number
     *
     * @throws ArithmeticException if the term is not valid
     */
    public Calculation calculate(String term) {
        Calculation calculation;
        DiceValidator.validate(dice, term);
        int result = Calculator.calculate(term, dice);
        int difference = abs(result - numbers.current());
        if (difference > 0) {
            Solutions solutions = algorithm.compute(dice.numbers[0], dice.numbers[1], dice.numbers[2],
                numbers.current());
            calculation = new Calculation(difference, numbers.current(), solutions.bestSolution());
        } else {
            calculation = new Calculation(difference, numbers.current(), null);
        }
        return calculation;
    }

    /**
     * Computes the best solution for the current dice numbers and current target number based on the level. Stores
     * the difference in the score board.
     *
     * Meant to be called for computer players.
     *
     * @return the best solution based on the level
     */
    public Solution solve() {
        Solutions solutions = algorithm.compute(dice.numbers[0], dice.numbers[1], dice.numbers[2],
            numbers.current());
        Solution solution = solutions.bestSolution(settings.getLevel());
        return solution;
    }

    /** Stores the difference to the current number for the current player in the score board. */
    public void score(String term, int difference) {
        scoreboard.setScore(numbers.index(), players.current(), term, difference);
    }

    /** Stores the solution for the current player in the score board. */
    public void score(Solution solution) {
        scoreboard.setScore(numbers.index(), players.current(), solution.getTerm(),
            abs(solution.getValue() - numbers.current()));
    }

    public Players getPlayers() {
        return players;
    }

    public Numbers getNumbers() {
        return numbers;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public Dice getDice() {
        return dice;
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public boolean isCanceled() {
        return canceled;
    }
}
