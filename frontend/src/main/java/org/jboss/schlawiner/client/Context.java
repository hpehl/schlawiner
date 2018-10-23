package org.jboss.schlawiner.client;

import java.util.ArrayList;
import java.util.List;

import com.github.nalukit.nalu.client.application.IsContext;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Settings;

public class Context implements IsContext {

    private Settings settings;
    private Player currentPlayer; // the current player for network games
    private List<Player> players; // for local games we just use a list of players w/o any iteration logic

    Context() {
        settings = new Settings();
        players = new ArrayList<>();
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players.clear();
        this.players.addAll(players);
    }
}
