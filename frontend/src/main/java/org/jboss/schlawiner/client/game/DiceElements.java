package org.jboss.schlawiner.client.game;

import java.util.Random;

import elemental2.dom.HTMLElement;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.schlawiner.engine.game.Dice;
import rx.functions.Action0;

import static elemental2.dom.DomGlobal.clearInterval;
import static elemental2.dom.DomGlobal.setInterval;
import static org.jboss.gwt.elemento.core.Elements.div;
import static org.jboss.schlawiner.client.resources.CSS.cubeContainer;
import static org.jboss.schlawiner.client.resources.CSS.dice;
import static org.jboss.schlawiner.client.resources.UIConstants.DICE_INTERVAL;
import static org.jboss.schlawiner.client.resources.UIConstants.DICE_TIMES;

class DiceElements implements IsElement<HTMLElement> {

    private final HTMLElement root;
    private final DiceElement[] diceElements;
    private int counter;
    private double handle;

    DiceElements() {
        diceElements = new DiceElement[3];
        root = div().css(dice)
            .add(div().css(cubeContainer)
                .add(diceElements[0] = new DiceElement()))
            .add(div().css(cubeContainer)
                .add(diceElements[1] = new DiceElement()))
            .add(div().css(cubeContainer)
                .add(diceElements[2] = new DiceElement()))
            .asElement();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    void clear() {
        for (DiceElement diceElement : diceElements) {
            diceElement.highlight(false);
        }
    }

    void role(Dice dice, Action0 action) {
        counter = 0;
        Random random = new Random();
        for (DiceElement diceElement : diceElements) {
            diceElement.flip(random.nextInt(6));
        }
        handle = setInterval((o) -> {
            counter++;
            if (counter == DICE_TIMES) {
                for (int i = 0; i < diceElements.length; i++) {
                    diceElements[i].flip(dice.numbers[i]);
                }
            } else if (counter > DICE_TIMES) {
                clearInterval(handle);
                action.call();
            } else {
                for (DiceElement diceElement : diceElements) {
                    diceElement.flip(random.nextInt(6));
                }
            }
        }, DICE_INTERVAL);
    }

    void usage(boolean[] used) {
        for (int i = 0; i < diceElements.length; i++) {
            diceElements[i].highlight(used[i]);
        }
    }
}
