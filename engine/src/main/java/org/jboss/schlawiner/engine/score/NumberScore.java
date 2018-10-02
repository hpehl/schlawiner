package org.jboss.schlawiner.engine.score;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Players;

public class NumberScore {

    private final int index;
    private final int number;
    private final Map<Player, Score> scores;

    NumberScore(int index, int number, Players players) {
        this.index = index;
        this.number = number;
        this.scores = new LinkedHashMap<>();
        for (Player player : players) {
            scores.put(player, Score.EMPTY);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NumberScore)) {
            return false;
        }

        NumberScore numberScore = (NumberScore) o;
        return index == numberScore.index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public String toString() {
        return "NumberScore{#" + index + ": " + number + ", " + scores + '}';
    }

    public int getNumber() {
        return number;
    }

    Score getScore(Player player) {
        Score score = scores.get(player);
        if (score != null) {
            return score;
        }
        return Score.EMPTY;
    }

    boolean hasScore(Player player) {
        Score score = scores.get(player);
        return score != null && score != Score.EMPTY;
    }

    void setScore(Player player, Score score) {
        if (scores.containsKey(player)) {
            scores.put(player, score);
        }
    }
}
