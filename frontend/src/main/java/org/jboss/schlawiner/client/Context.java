package org.jboss.schlawiner.client;

import java.util.ArrayList;
import java.util.List;

import com.github.nalukit.nalu.client.application.IsContext;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Settings;

public class Context implements IsContext {

    private Settings settings;
    private List<Player> players; // here we just use a list of players w/o any iteration logic

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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players.clear();
        this.players.addAll(players);
    }
}
