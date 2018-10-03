package org.jboss.schlawiner.engine.game;

import org.jboss.schlawiner.engine.algorithm.Algorithm;
import org.jboss.schlawiner.engine.score.Scoreboard;

public class SinglePlayerGame implements Game {

    private final Players players;
    private final Numbers numbers;
    private final Algorithm algorithm;
    private final Settings settings;
    private Scoreboard scoreboard;
    private Calculator calculator;

    public SinglePlayerGame(Players players, Numbers numbers, Algorithm algorithm, Settings settings) {
        this.players = players;
        this.numbers = numbers;
        this.algorithm = algorithm;
        this.settings = settings;
        this.scoreboard = new Scoreboard(players, numbers);
    }

    @Override
    public void start() {
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public void solution(String term) {

    }

    @Override
    public void penalty(int points) {

    }

    @Override
    public Scoreboard gameover() {
        return null;
    }
}
