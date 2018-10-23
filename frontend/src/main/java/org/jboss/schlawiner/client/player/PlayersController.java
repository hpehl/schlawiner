package org.jboss.schlawiner.client.player;

import com.github.nalukit.nalu.client.component.IsComponent;
import com.github.nalukit.nalu.client.component.IsComponentCreator;
import org.jboss.schlawiner.engine.game.Player;

public interface PlayersController extends IsComponent.Controller, IsComponentCreator<PlayersComponent> {

    void addPlayer(String name);

    void togglePlayer(Player player, boolean human);

    void removePlayer(Player player);

    void startGame();
}
