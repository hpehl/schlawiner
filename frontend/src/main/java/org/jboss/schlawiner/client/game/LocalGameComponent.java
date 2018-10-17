package org.jboss.schlawiner.client.game;

import com.github.nalukit.nalu.client.component.IsComponent;
import elemental2.dom.HTMLElement;
import org.jboss.schlawiner.engine.algorithm.Solution;
import org.jboss.schlawiner.engine.game.Dice;
import org.jboss.schlawiner.engine.game.Game;
import org.jboss.schlawiner.engine.game.Player;
import rx.functions.Action0;

public interface LocalGameComponent extends IsComponent<LocalGameController, HTMLElement> {

    void start(Game game);

    void reset();

    void role(Player currentPlayer, Dice dice, Action0 action);

    void usage(boolean[] used);

    void countdown(int timeout, int number);

    void showTerm(String term);

    void computer(Solution solution, Action0 action);

    void message(String message);

    void modal(String text);

    void modal(String text, Action0 onClose);

    void highlight(Player player, int numberIndex);

    void showScore(Game game);
}
