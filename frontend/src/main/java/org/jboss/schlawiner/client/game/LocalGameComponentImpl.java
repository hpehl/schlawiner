package org.jboss.schlawiner.client.game;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import org.jboss.schlawiner.client.resources.CSS;
import org.jboss.schlawiner.engine.game.Dice;
import org.jboss.schlawiner.engine.game.Game;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.score.Score;
import org.jboss.schlawiner.engine.score.Scoreboard;

import static org.jboss.gwt.elemento.core.Elements.*;
import static org.jboss.gwt.elemento.core.Elements.input;
import static org.jboss.gwt.elemento.core.EventType.click;
import static org.jboss.gwt.elemento.core.InputType.text;
import static org.jboss.schlawiner.client.resources.CSS.*;

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
    private double timeout;

    public LocalGameComponentImpl() {
        diceElements = new DiceElement[3];
        root = div().css(page)
            .add(div().css(row, control)
                .add(div().css(CSS.input)
                    .add(div().css(CSS.dice)
                        .add(div().css(cubeContainer)
                            .add(diceElements[0] = new DiceElement()))
                        .add(div().css(cubeContainer)
                            .add(diceElements[1] = new DiceElement()))
                        .add(div().css(cubeContainer)
                            .add(diceElements[2] = new DiceElement())))
                    .add(solution = input(text).css(CSS.solution)
                        .apply(i -> i.placeholder = "Enter solution")
                        .asElement()))
                .add(div().css(countdownContainer).add(countdown = new CountdownElement()))
                .add(div().css(links)
                    .add(ul()
                        .add(li()
                            .add(dice = a().textContent("Dice").on(click, e -> getController().dice()).asElement()))
                        .add(li()
                            .add(skip = a().textContent("Skip").on(click, e -> getController().skip()).asElement()))
                        .add(li()
                            .add(restart = a().textContent("Restart")
                                .on(click, e -> getController().restart())
                                .asElement()))
                        .add(li()
                            .add(back = a().textContent("Back").on(click, e -> getController().back()).asElement())))))
            .add(div().css(row)
                .add(message = new MessageElement()))
            .add(div().css(row)
                .add(numpad = new NumpadElement()))
            .add(numberScoreContainer = div().css(row).asElement())
            .add(playerScoreContainer = div().css(row).asElement())
            .asElement();
    }

    @Override
    public void setController(LocalGameController controller) {
        super.setController(controller);
        numpad.setController(controller);
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
    public void role(Dice dice) {
        for (int i = 0, diceElementsLength = diceElements.length; i < diceElementsLength; i++) {
            diceElements[i].role(dice.numbers[i]);
        }
        numpad.showDice(dice);
    }

    @Override
    public void usage(boolean[] used) {
        for (int i = 0, diceElementsLength = diceElements.length; i < diceElementsLength; i++) {
            diceElements[i].highlight(used[i]);
        }
    }

    @Override
    public void countdown(int timeout, int number) {
        countdown.reset(timeout);
        countdown.number(number);
    }

    @Override
    public void showTerm(String term) {
        solution.value = term;
    }

    @Override
    public void message(String message) {
        this.message.setMessage(message);
    }

    @Override
    public void highlight(Player player, int numberIndex) {
        numberScore.highlight(player, numberIndex);
        playerScore.highlight(player, numberIndex);
    }

    @Override
    public void solve(Scoreboard scoreboard, Player player, int numberIndex, Score score) {
        numberScore.setScore(scoreboard, player, numberIndex, score);
        playerScore.setScore(scoreboard, player, numberIndex, score);
    }
}
