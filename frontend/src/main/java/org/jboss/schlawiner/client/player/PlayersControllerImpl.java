package org.jboss.schlawiner.client.player;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import elemental2.dom.HTMLElement;
import org.jboss.schlawiner.client.Context;
import org.jboss.schlawiner.client.LocalStorage;
import org.jboss.schlawiner.engine.game.Player;

@Controller(route = "/players",
    selector = "content",
    component = PlayersComponentImpl.class,
    componentInterface = PlayersComponent.class)
public class PlayersControllerImpl extends AbstractComponentController<Context, PlayersComponent, HTMLElement>
    implements PlayersController {

    @Override
    public PlayersComponent createComponent() {
        return PlayersComponentImpl.create();
    }

    @Override
    public void start() {
        component.showPlayers(context.getPlayers());
    }

    @Override
    public void addPlayer(String name) {
        Player player = new Player(name, true);
        context.getPlayers().add(player);
        LocalStorage.savePlayers(context.getPlayers());
        component.addPlayer(player);
    }

    @Override
    public void togglePlayer(Player player, boolean human) {
        if (player != null) {
            player.setHuman(human);
            LocalStorage.savePlayers(context.getPlayers());
            component.updateHuman(player);
        }
    }

    @Override
    public void removePlayer(Player player) {
        if (player != null) {
            context.getPlayers().remove(player);
            LocalStorage.savePlayers(context.getPlayers());
            component.removePlayer(player);
        }
    }

    @Override
    public void startGame() {
        router.route("/local-game");
    }
}
