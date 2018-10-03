package org.jboss.schlawiner.engine.game;

import org.jboss.schlawiner.engine.score.Scoreboard;

public interface Game {

    void start();

    boolean hasNext();

    void solution(String term);

    void penalty(int points);

    Scoreboard gameover();
}
