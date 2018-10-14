package org.jboss.schlawiner.client.game;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import elemental2.dom.HTMLElement;
import org.jboss.schlawiner.client.Context;
import org.jboss.schlawiner.engine.algorithm.OperationAlgorithm;
import org.jboss.schlawiner.engine.game.Game;
import org.jboss.schlawiner.engine.game.Numbers;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Players;
import org.jboss.schlawiner.engine.game.Settings;

import static java.util.Arrays.asList;

@Controller(route = "/local-game",
    selector = "content",
    component = LocalGameComponentImpl.class,
    componentInterface = LocalGameComponent.class)
public class LocalGameControllerImpl extends AbstractComponentController<Context, LocalGameComponent, HTMLElement>
    implements LocalGameController {

    private Game game;

    @Override
    public void start() {
        Settings settings = context.getSettings();
        Players players = new Players(asList(
            new Player(settings.getName(), true),
            new Player("Computer", false)));
        Numbers numbers = new Numbers(settings.getNumbers());
        game = new Game(players, numbers, new OperationAlgorithm(), settings);
        component.start(game);
    }

    @Override
    public void dice() {
        component.message("Dice not yet implemented");
    }

    @Override
    public void skip() {
        component.message("Skip not yet implemented");
    }

    @Override
    public void restart() {
        component.message("Restart not yet implemented");
    }

    @Override
    public void back() {
        router.route("/main");
    }

    @Override
    public void solve() {
        component.message("Solve not yet implemented");
    }
}
