package org.jboss.schlawiner.engine.score;

import java.util.ArrayList;
import java.util.List;

import org.jboss.schlawiner.engine.game.Numbers;
import org.jboss.schlawiner.engine.game.Player;

/**
 * Stores the players as rows and the numbers as columns.
 *
 * <table>
 * <colgroup>
 *     <col style="width: 20%">
 *     <col style="width: 11.4%">
 *     <col style="width: 11.4%">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 *     <col style="width: 11.4">
 * </colgroup>
 * <thead>
 * <tr>
 *     <th>&nbsp;</th>
 *     <th>12</th>
 *     <th>34</th>
 *     <th>4</th>
 *     <th>52</th>
 *     <th>57</th>
 *     <th>80</th>
 *     <th>&nbsp;</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 *     <td>Player 1</td>
 *     <td>1</td>
 *     <td>0</td>
 *     <td>2</td>
 *     <td>1</td>
 *     <td>0</td>
 *     <td>4</td>
 * </tr>
 * <tr>
 *     <td>Player 2</td>
 *     <td>0</td>
 *     <td>0</td>
 *     <td>1</td>
 *     <td>2</td>
 *     <td>0</td>
 *     <td>3</td>
 * </tr>
 * </tbody>
 * </table>
 */
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

    Player getPlayer() {
        return player;
    }
}
