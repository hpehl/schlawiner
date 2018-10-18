package org.jboss.schlawiner.client.game;

import java.util.Iterator;

import com.github.nalukit.nalu.client.component.AbstractComponent;
import com.google.common.primitives.Chars;
import elemental2.core.JsArray;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLInputElement;
import org.jboss.gwt.elemento.core.EventType;
import org.jboss.gwt.elemento.core.Key;
import org.jboss.schlawiner.client.resources.CSS;
import org.jboss.schlawiner.engine.algorithm.Solution;
import org.jboss.schlawiner.engine.game.Dice;
import org.jboss.schlawiner.engine.game.Game;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Settings;
import org.jboss.schlawiner.engine.score.Score;
import org.jboss.schlawiner.engine.score.Scoreboard;
import rx.functions.Action0;

import static elemental2.dom.DomGlobal.clearInterval;
import static elemental2.dom.DomGlobal.setInterval;
import static org.gwtproject.safehtml.shared.SafeHtmlUtils.fromSafeConstant;
import static org.jboss.gwt.elemento.core.Elements.*;
import static org.jboss.gwt.elemento.core.Elements.input;
import static org.jboss.gwt.elemento.core.EventType.click;
import static org.jboss.gwt.elemento.core.InputType.text;
import static org.jboss.schlawiner.client.resources.CSS.*;
import static org.jboss.schlawiner.client.resources.UIConstants.TYPEWRITER_INTERVAL;

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
    private Modal modal;
    private double handle;
    private Settings settings;

    public LocalGameComponentImpl() {
        root = div().css(page, tingleContentWrapper)
            .add(div().css(row, control)
                .add(div().css(CSS.input)
                    .add(diceElements = new DiceElements())
                    .add(solution = input(text).css(CSS.solution)
                        .apply(i -> i.placeholder = "Enter solution")
                        .on(EventType.keyup, e -> {
                            if (Key.Enter.match(e)) {
                                getController().calculate();
                            } else {
                                getController().setTerm(((HTMLInputElement) e.target).value, false);
                            }
                        })
                        .asElement()))
                .add(div().css(countdownContainer).add(countdown = new CountdownElement()))
                .add(div().css(links)
                    .add(ul()
                        .add(li()
                            .add(dice = a().textContent("Dice")
                                .on(click, e -> {
                                    String textContent = ((HTMLElement) e.target).textContent;
                                    if ("Dice".equals(textContent)) {
                                        getController().dice();
                                    } else if (textContent.startsWith("Retry")) {
                                        getController().retry();
                                    }
                                }).asElement()))
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
    public void onDetach() {
        super.onDetach();
        if (modal != null) {
            modal.destroy();
        }
    }

    @Override
    public void start(Game game, Settings settings) {
        this.settings = settings;

        numberScore = new NumberScoreElement(this, game.getPlayers(), game.getNumbers());
        playerScore = new PlayerScoreElement(this, game.getPlayers(), game.getNumbers());
        setVisible(playerScore.asElement(), false);
        scoreContainer.appendChild(numberScore.asElement());
        scoreContainer.appendChild(playerScore.asElement());
    }

    @Override
    public void reset() {
        diceElements.clear();
        solution.value = "";
        message.setMessage(fromSafeConstant("&nbsp;"));
        numberScore.clear();
        playerScore.clear();
    }

    @Override
    public void role(Player currentPlayer, Dice dice, Action0 action) {
        disableLinks(this.dice, this.skip);
        numpad.disable();
        diceElements.role(dice, () -> {
            numpad.showDice(dice);
            if (currentPlayer.isHuman()) {
                enableLinks(this.skip);
                numpad.enable();
                if (settings.getRetries() > 0) {
                    this.dice.textContent = "Retry (" + currentPlayer.getRetries() + ")";
                    if (currentPlayer.getRetries() > 0) {
                        enableLinks(this.dice);
                    }
                }
            }
            action.call();
        });
    }

    @Override
    public void usage(boolean[] used) {
        diceElements.usage(used);
    }

    @Override
    public void countdown(int timeout) {
        countdown.reset(timeout);
    }

    @Override
    public void showTerm(String term) {
        solution.value = term;
    }

    @Override
    public void humanTurn(Player currentPlayer) {
        reset();
        dice.textContent = "Dice";
        enableLinks(dice, skip);
        numpad.disable();
        message(currentPlayer.getName() + " it's your turn.");
    }

    @Override
    public void computer(Solution solution, Action0 action) {
        clearInterval(handle);
        this.solution.value = "";

        Iterator<Character> iterator = Chars.asList(solution.getTerm().toCharArray()).iterator();
        handle = setInterval((o) -> {
            if (iterator.hasNext()) {
                this.solution.value = this.solution.value + String.valueOf(iterator.next());
            } else {
                clearInterval(handle);
                action.call();
            }
        }, TYPEWRITER_INTERVAL);
    }

    @Override
    public void message(String message) {
        this.message.setMessage(message);
    }

    @Override
    public void modal(String text) {
        modal(text, null);
    }

    @Override
    public void modal(String text, Action0 onClose) {
        if (modal != null) {
            modal.destroy();
        }
        Modal.ModalOptions options = new Modal.ModalOptions();
        options.closeMethods = new JsArray<>("overlay", "escape");
        if (onClose != null) {
            options.onClose = onClose::call;
        }
        modal = new Modal(options);
        modal.setContent(p().css(mb0).textContent(text).asElement());
        modal.open();
    }

    @Override
    public void highlight(Player player, int currentNumber, int numberIndex) {
        countdown.number(currentNumber);
        numberScore.highlight(player, numberIndex);
        playerScore.highlight(player, numberIndex);
    }

    @Override
    public void showScore(Game game) {
        Scoreboard scoreboard = game.getScoreboard();
        Player player = game.getPlayers().current();
        int numberIndex = game.getNumbers().index();
        Score score = scoreboard.getScore(player, numberIndex);

        numberScore.setScore(scoreboard, player, numberIndex, score);
        playerScore.setScore(scoreboard, player, numberIndex, score);
    }

    @Override
    public void gameOver() {
        reset();
        disableLinks(dice, skip);
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

    private void enableLinks(HTMLElement... links) {
        for (HTMLElement link : links) {
            link.classList.remove(disabled);
        }
    }

    private void disableLinks(HTMLElement... links) {
        for (HTMLElement link : links) {
            link.classList.add(disabled);
        }
    }
}
