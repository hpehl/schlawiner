package org.jboss.schlawiner.client.player;

import com.github.nalukit.nalu.client.component.IsComponent;
import elemental2.dom.HTMLElement;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.schlawiner.engine.game.Player;

public interface PlayersComponent extends IsComponent<PlayersController, HTMLElement>, IsElement<HTMLElement> {

    void showPlayers(Iterable<Player> players);

    void addPlayer(Player player);

    void updateHuman(Player player);

    void removePlayer(Player player);
}
