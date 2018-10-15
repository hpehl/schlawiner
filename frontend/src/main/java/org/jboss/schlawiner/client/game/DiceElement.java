package org.jboss.schlawiner.client.game;

import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableMap;
import elemental2.dom.HTMLDivElement;
import elemental2.dom.HTMLElement;
import org.jboss.gwt.elemento.core.IsElement;
import rx.functions.Action0;

import static elemental2.dom.DomGlobal.clearInterval;
import static elemental2.dom.DomGlobal.setInterval;
import static org.jboss.gwt.elemento.core.Elements.div;
import static org.jboss.gwt.elemento.core.Elements.figure;
import static org.jboss.schlawiner.client.resources.CSS.*;
import static org.jboss.schlawiner.client.resources.UIConstants.DICE_INTERVAL;
import static org.jboss.schlawiner.client.resources.UIConstants.DICE_TIMES;

public class DiceElement implements IsElement<HTMLElement> {

    private static final Map<Integer, String> NUMBER_TO_FACE = new ImmutableMap.Builder<Integer, String>()
        .put(1, front)
        .put(2, back)
        .put(3, right)
        .put(4, left)
        .put(5, top)
        .put(6, bottom)
        .build();

    private final HTMLDivElement root;
    private int counter;
    private double handle;
    private String currentFace;

    DiceElement() {
        this.currentFace = showFront;
        this.root = div().css(cube, currentFace)
            .add(figure().css(front).textContent("1"))
            .add(figure().css(back).textContent("2"))
            .add(figure().css(right).textContent("3"))
            .add(figure().css(left).textContent("4"))
            .add(figure().css(top).textContent("5"))
            .add(figure().css(bottom).textContent("6"))
            .asElement();
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    void role(int number, Action0 action) {
        counter = 0;
        Random random = new Random();
        flip(random.nextInt(6));
        handle = setInterval((o) -> {
            counter++;
            if (counter == DICE_TIMES) {
                flip(number);
            } else if (counter > DICE_TIMES) {
                clearInterval(handle);
                action.call();
            } else {
                flip(random.nextInt(6));
            }
        }, DICE_INTERVAL);
    }

    void flip(int number) {
        String newFace = "show-" + NUMBER_TO_FACE.get(number);
        root.classList.remove(currentFace);
        root.classList.add(newFace);
        currentFace = newFace;
    }

    void highlight(boolean on) {
        if (on) {
            root.classList.add(highlight);
        } else {
            root.classList.remove(highlight);
        }
    }
}
