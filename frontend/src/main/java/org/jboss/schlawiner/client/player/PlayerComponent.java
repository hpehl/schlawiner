package org.jboss.schlawiner.client.player;

import java.util.List;

import com.github.nalukit.nalu.client.component.IsComponent;
import elemental2.dom.HTMLElement;
import org.jboss.schlawiner.engine.game.Player;

public interface PlayerComponent extends IsComponent<PlayerController, HTMLElement> {

    void showPlayers(List<Player> players);

    void addPlayer(Player player);

    void updateName(Player player);

    void updateHuman(Player player);

    void removePlayer(Player player);
}
