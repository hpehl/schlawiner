package org.jboss.schlawiner.client.player;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import elemental2.dom.HTMLElement;
import org.jboss.schlawiner.client.Context;
import org.jboss.schlawiner.client.LocalStorage;
import org.jboss.schlawiner.engine.game.Player;

@Controller(route = "/player",
    selector = "content",
    component = PlayerComponentImpl.class,
    componentInterface = PlayerComponent.class)
public class PlayerControllerImpl extends AbstractComponentController<Context, PlayerComponent, HTMLElement>
    implements PlayerController {

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
    public void editPlayer(String id, String name) {
        Player player = find(id);
        if (player != null) {
            player.setName(name);
            LocalStorage.savePlayers(context.getPlayers());
            component.updateName(player);
        }
    }

    @Override
    public void editPlayer(String id, boolean human) {
        Player player = find(id);
        if (player != null) {
            player.setHuman(human);
            LocalStorage.savePlayers(context.getPlayers());
            component.updateHuman(player);
        }
    }

    @Override
    public void removePlayer(String id) {
        Player player = find(id);
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

    private Player find(String id) {
        for (Player player : context.getPlayers()) {
            if (id.equals(player.getId())) {
                return player;
            }
        }
        return null;
    }
}
