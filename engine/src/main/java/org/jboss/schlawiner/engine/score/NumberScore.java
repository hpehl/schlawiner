package org.jboss.schlawiner.engine.score;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Players;

/**
 * Stores the numbers as rows and the players as columns.
 *
 * <table>
 * <colgroup>
 *     <col style="width: 10%">
 *     <col style="width: 45%">
 *     <col style="width: 45%">
 * </colgroup>
 * <thead>
 * <tr>
 *     <th>&nbsp;</th>
 *     <th>Player 1</th>
 *     <th>Player 2</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 *     <td>12</td>
 *     <td>3</td>
 *     <td>1</td>
 * </tr>
 * <tr>
 *     <td>34</td>
 *     <td>1</td>
 *     <td>2</td>
 * </tr>
 * <tr>
 *     <td>4</td>
 *     <td>1</td>
 *     <td>2</td>
 * </tr>
 * <tr>
 *     <td>52</td>
 *     <td>1</td>
 *     <td>2</td>
 * </tr>
 * <tr>
 *     <td>57</td>
 *     <td>1</td>
 *     <td>2</td>
 * </tr>
 * <tr>
 *     <td>80</td>
 *     <td>1</td>
 *     <td>2</td>
 * </tr>
 * </tbody>
 * <tfoot>
 * <tr>
 *     <th>&nbsp;</th>
 *     <th>2</th>
 *     <th>4</th>
 * </tr>
 * </tfoot>
 * </table>
 */
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

    int getNumber() {
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
