package org.jboss.schlawiner.engine.score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import org.jboss.schlawiner.engine.game.Numbers;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Players;

public class Scoreboard {

    private Players players;
    private NumberScore[] numberScores;
    private PlayerScore[] playerScores;
    private Map<Player, Integer> playerSums;

    public Scoreboard(Players players, Numbers numbers) {
        this.players = players;
        this.numberScores = new NumberScore[numbers.size()];
        this.playerScores = new PlayerScore[players.size()];
        this.playerSums = new HashMap<>();

        int numberIndex = 0;
        for (Integer number : numbers) {
            numberScores[numberIndex] = new NumberScore(numberIndex, number, players);
            numberIndex++;
        }
        int playerIndex = 0;
        for (Player player : players) {
            playerScores[playerIndex++] = new PlayerScore(player, numbers);
            playerSums.put(player, 0);
        }
    }

    public void setScore(int numberIndex, Player player, String term, int difference) {
        Score score = new Score(term, difference);
        if (numberIndex > -1 && numberIndex < numberScores.length) {
            numberScores[numberIndex].setScore(player, score);
        }
        int playerIndex = players.indexOf(player);
        if (playerIndex > -1 && playerIndex < players.size()) {
            playerScores[playerIndex].setScore(numberIndex, score);
        }
        if (playerSums.containsKey(player)) {
            int newScore = playerSums.get(player) + score.getDifference();
            playerSums.put(player, newScore);
        }
    }

    public Score getScore(int numberIndex, Player player) {
        return numberScores[numberIndex].getScore(player);
    }

    public List<NumberScore> getNumberScores() {
        return Arrays.asList(numberScores);
    }

    public List<PlayerScore> getPlayerScores() {
        return Lists.newArrayList(playerScores);
    }

    public int getSummedScore(Player player) {
        if (playerSums.containsKey(player)) {
            return playerSums.get(player);
        }
        return 0;
    }

    public List<Player> getWinners() {
        int min = Integer.MAX_VALUE;
        for (Integer sum : playerSums.values()) {
            min = Math.min(min, sum);

        }
        List<Player> winners = new ArrayList<>();
        for (Map.Entry<Player, Integer> entry : playerSums.entrySet()) {
            if (entry.getValue() == min) {
                winners.add(entry.getKey());
            }
        }
        return winners;
    }
}
