package org.jboss.schlawiner.client;

import java.util.List;

import com.github.nalukit.nalu.client.application.AbstractApplicationLoader;
import org.jboss.schlawiner.engine.game.Player;

public class Loader extends AbstractApplicationLoader<Context> {

    @Override
    public void load(FinishLoadCommand finishLoadCommand) {
        // TODO load from server
        context.setCurrentPlayer(new Player("Schlawiner", true));

        List<Player> players = LocalStorage.loadPlayers();
        if (players.isEmpty()) {
            players.add(new Player("Schlawiner", true));
            players.add(new Player("Computer", false));
        }
        context.setPlayers(players);

        context.setSettings(LocalStorage.loadSettings());
        finishLoadCommand.finishLoading();
    }
}
