package org.jboss.schlawiner.client.game;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import org.jboss.schlawiner.client.resources.CSS;
import org.jboss.schlawiner.engine.game.Game;

import static org.jboss.gwt.elemento.core.Elements.*;
import static org.jboss.gwt.elemento.core.EventType.click;
import static org.jboss.gwt.elemento.core.InputType.text;
import static org.jboss.schlawiner.client.resources.CSS.container;
import static org.jboss.schlawiner.client.resources.CSS.control;
import static org.jboss.schlawiner.client.resources.CSS.page;
import static org.jboss.schlawiner.client.resources.CSS.row;

public class LocalGameComponentImpl extends AbstractComponent<LocalGameController, HTMLElement> implements
    LocalGameComponent {

    private final HTMLElement root;
    private final DiceElement[] diceElements;
    private final HTMLInputElement solution;
    private final HTMLElement dice;
    private final HTMLElement skip;
    private final HTMLElement restart;
    private final HTMLElement back;
    private final CountdownElement countdown;
    private final MessageElement message;
    private final NumpadElement numpad;
    private final HTMLElement numberScoreContainer;
    private final HTMLElement playerScoreContainer;
    private NumberScoreElement numberScore;
    private PlayerScoreElement playerScore;

    public LocalGameComponentImpl() {
        diceElements = new DiceElement[3];
        root = div().css(page)
            .add(div().css(row)
                .add(div().css(CSS.input)
                    .add(div().css(CSS.dice)
                        .add(div().css(container)
                            .add(diceElements[0] = new DiceElement()))
                        .add(div().css(container)
                            .add(diceElements[1] = new DiceElement()))
                        .add(div().css(container)
                            .add(diceElements[2] = new DiceElement())))
                    .add(solution = input(text).apply(i -> i.placeholder = "Enter solution").asElement()))
                .add(div().css(control)
                    .add(ul()
                        .add(li()
                            .add(dice = a().textContent("Dice").on(click, e -> getController().dice()).asElement())
                            .add(skip = a().textContent("Skip").on(click, e -> getController().skip()).asElement())
                            .add(restart = a().textContent("Restart")
                                .on(click, e -> getController().restart())
                                .asElement())
                            .add(back = a().textContent("Back").on(click, e -> getController().back()).asElement()))))
                .add(countdown = new CountdownElement()))
            .add(div().css(row)
                .add(message = new MessageElement()))
            .add(div().css(row)
                .add(numpad = new NumpadElement()))
            .add(numberScoreContainer = div().css(row).asElement())
            .add(playerScoreContainer = div().css(row).asElement())
            .asElement();
    }

    @Override
    public void render() {
        initElement(root);
    }

    @Override
    public void start(Game game) {
        numberScore = new NumberScoreElement(game.getPlayers(), game.getNumbers());
        playerScore = new PlayerScoreElement(game.getPlayers(), game.getNumbers());
        numberScoreContainer.appendChild(numberScore.asElement());
        playerScoreContainer.appendChild(playerScore.asElement());
    }

    @Override
    public void message(String message) {
        this.message.setMessage(message);
    }
}
