package org.jboss.schlawiner.client.game;

import elemental2.dom.HTMLElement;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.schlawiner.client.resources.CSS;

import static org.jboss.gwt.elemento.core.Elements.div;

class CountdownElement implements IsElement<HTMLElement> {

    private static final int HEIGHT = 150;
    private static final int RAD = 66;
    private static final int WIDTH = 150;

    private final HTMLElement root;
    private final Countdown countdown;

    CountdownElement() {
        this.root = div().css(CSS.countdown).asElement();
        this.countdown = new Countdown(root, WIDTH, HEIGHT, RAD);
    }

    @Override
    public HTMLElement asElement() {
        return root;
    }

    void reset(int total) {
        countdown.reset(total);
    }

    boolean tick() {
        return countdown.tick();
    }

    void number(int number) {
        countdown.number(number);
    }
}
