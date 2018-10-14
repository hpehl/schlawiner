package org.jboss.schlawiner.client.game;

import com.github.nalukit.nalu.client.component.IsComponent;
import elemental2.dom.HTMLElement;
import org.jboss.schlawiner.engine.game.Game;

public interface LocalGameComponent extends IsComponent<LocalGameController, HTMLElement> {

    void start(Game game);

    void message(String message);
}
