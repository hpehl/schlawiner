package org.jboss.schlawiner.client.game;

import com.github.nalukit.nalu.client.component.IsComponent;
import elemental2.dom.HTMLElement;
import org.jboss.schlawiner.engine.game.Dice;
import org.jboss.schlawiner.engine.game.Game;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.score.Score;
import org.jboss.schlawiner.engine.score.Scoreboard;

public interface LocalGameComponent extends IsComponent<LocalGameController, HTMLElement> {

    void start(Game game);

    void role(Dice dice);

    void usage(boolean[] used);

    void countdown(int timeout, int number);

    void showTerm(String term);

    void message(String message);

    void highlight(Player player, int numberIndex);

    void solve(Scoreboard scoreboard, Player player, int numberIndex, Score score);
}
