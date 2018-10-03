package org.jboss.schlawiner.engine.score;

import java.util.ArrayList;
import java.util.List;

import org.jboss.schlawiner.engine.game.Numbers;
import org.jboss.schlawiner.engine.game.Player;

/** Stores the score per player */
public class PlayerScore {

    private final Player player;
    private final List<Score> scores;

    PlayerScore(Player player, Numbers numbers) {
        this.player = player;
        this.scores = new ArrayList<>();
        for (int index = 0; index < numbers.size(); index++) {
            scores.add(Score.EMPTY);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlayerScore)) {
            return false;
        }

        PlayerScore that = (PlayerScore) o;
        return !(player != null ? !player.equals(that.player) : that.player != null);
    }

    @Override
    public int hashCode() {
        return player != null ? player.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "PlayerScore{" + player + ": " + scores + '}';
    }

    void setScore(int numberIndex, Score score) {
        if (numberIndex > -1 && numberIndex < scores.size()) {
            scores.set(numberIndex, score);
        }
    }

    Score getScore(int numberIndex) {
        if (numberIndex > -1 && numberIndex < scores.size()) {
            return scores.get(numberIndex);
        }
        return null;
    }

    public Player getPlayer() {
        return player;
    }
}
