package org.jboss.schlawiner.engine.game;

import org.jboss.schlawiner.engine.score.Scoreboard;

/** Game state machine w/o handling of timeouts */
public interface Game {

    void next();

    boolean hasNext();

    void dice(Dice dice);

    void retry();

    void penalty();

    void solution(String term);

    Dice getDice();

    Scoreboard getScoreboard();
}
