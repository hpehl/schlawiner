package org.jboss.schlawiner.client.game;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import org.gwtproject.safehtml.shared.SafeHtmlUtils;
import org.jboss.gwt.elemento.core.EventType;
import org.jboss.gwt.elemento.core.Key;
import org.jboss.schlawiner.client.resources.CSS;
import org.jboss.schlawiner.engine.game.Dice;
import org.jboss.schlawiner.engine.game.Game;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.score.Score;
import org.jboss.schlawiner.engine.score.Scoreboard;
import rx.functions.Action0;

import static org.jboss.gwt.elemento.core.Elements.*;
import static org.jboss.gwt.elemento.core.Elements.input;
import static org.jboss.gwt.elemento.core.EventType.click;
import static org.jboss.gwt.elemento.core.InputType.text;
import static org.jboss.schlawiner.client.resources.CSS.*;

public class LocalGameComponentImpl extends AbstractComponent<LocalGameController, HTMLElement> implements
    LocalGameComponent {

    private final HTMLElement root;
    private final DiceElements diceElements;
    private final HTMLInputElement solution;
    private final HTMLElement dice;
    private final HTMLElement skip;
    private final HTMLElement restart;
    private final HTMLElement back;
    private final CountdownElement countdown;
    private final MessageElement message;
    private final NumpadElement numpad;
    private final HTMLElement scoreContainer;
    private NumberScoreElement numberScore;
    private PlayerScoreElement playerScore;

    public LocalGameComponentImpl() {
        root = div().css(page)
            .add(div().css(row, control)
                .add(div().css(CSS.input)
                    .add(diceElements = new DiceElements())
                    .add(solution = input(text).css(CSS.solution)
                        .apply(i -> i.placeholder = "Enter solution")
                        .on(EventType.keyup, e -> {
                            if (Key.Enter.match(e)) {
                                getController().solve();
                            } else {
                                getController().setTerm(((HTMLInputElement) e.target).value, false);
                            }
                        })
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
            .add(scoreContainer = div().css(row, score).asElement())
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
        numberScore = new NumberScoreElement(this, game.getPlayers(), game.getNumbers());
        playerScore = new PlayerScoreElement(this, game.getPlayers(), game.getNumbers());
        setVisible(playerScore.asElement(), false);
        scoreContainer.appendChild(numberScore.asElement());
        scoreContainer.appendChild(playerScore.asElement());
    }

    @Override
    public void clear() {
        diceElements.clear();
        solution.value = "";
        message.setMessage(SafeHtmlUtils.fromSafeConstant("&nbsp;"));
    }

    @Override
    public void role(Dice dice, Action0 action) {
        diceElements.role(dice, () -> {
            numpad.showDice(dice);
            action.call();
        });
    }

    @Override
    public void usage(boolean[] used) {
        diceElements.usage(used);
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
    public void showScore(Scoreboard scoreboard, Player player, int numberIndex, Score score) {
        numberScore.setScore(scoreboard, player, numberIndex, score);
        playerScore.setScore(scoreboard, player, numberIndex, score);
    }

    void showNumberScore() {
        numberScore.asElement().classList.remove(animated, fadeInRight, fadeOutRight);
        playerScore.asElement().classList.remove(animated, fadeInLeft, fadeOutLeft);
        playerScore.asElement().classList.add(animated, fadeOutLeft);
        numberScore.asElement().classList.add(animated, fadeInRight);
    }

    void showPlayerScore() {
        numberScore.asElement().classList.remove(animated, fadeInRight, fadeOutRight);
        playerScore.asElement().classList.remove(animated, fadeInLeft, fadeOutLeft);
        numberScore.asElement().classList.add(animated, fadeOutRight);
        playerScore.asElement().classList.add(animated, fadeInLeft);
        setVisible(playerScore.asElement(), true);
    }
}
