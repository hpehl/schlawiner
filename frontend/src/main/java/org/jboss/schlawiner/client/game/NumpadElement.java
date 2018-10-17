package org.jboss.schlawiner.client.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import elemental2.dom.HTMLButtonElement;
import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTableElement;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.gwt.elemento.core.builder.HtmlContentBuilder;
import org.jboss.schlawiner.engine.game.Dice;

import static org.gwtproject.safehtml.shared.SafeHtmlUtils.fromSafeConstant;
import static org.jboss.gwt.elemento.core.Elements.*;
import static org.jboss.gwt.elemento.core.EventType.click;
import static org.jboss.schlawiner.client.resources.CSS.disabled;
import static org.jboss.schlawiner.client.resources.CSS.numpad;

class NumpadElement implements IsElement<HTMLTableElement> {

    private static final int[] WIDTHS = {20, 20, 20, 4, 12, 12, 12};
    private static final int[] MULTIPLIER = {1, 10, 100};

    private final Stack<String> tokens;
    private final HTMLButtonElement numbers[][]; // first dimension is the multiplier, second dimension is the dice number
    private final List<HTMLButtonElement> controls;
    private final HTMLTableElement root;
    private LocalGameController controller;

    NumpadElement() {
        HTMLElement colgroup, tbody;
        this.tokens = new Stack<>();
        this.numbers = new HTMLButtonElement[3][3];
        this.controls = new ArrayList<>();
        this.root = table().css(numpad)
            .add(colgroup = colgroup().asElement())
            .add(tbody = tbody().asElement())
            .asElement();
        for (int width : WIDTHS) {
            colgroup.appendChild(col().style("width: " + width + "%").asElement());
        }

        // <dice number> * 1
        int multiplierIndex = 0, diceNumberIndex = 0;
        tbody.appendChild(tr()
            .add(td().add(numbers[multiplierIndex][diceNumberIndex++] = button("0").asElement()))
            .add(td().add(numbers[multiplierIndex][diceNumberIndex++] = button("0").asElement()))
            .add(td().add(numbers[multiplierIndex][diceNumberIndex] = button("0").asElement()))
            .add(td().innerHtml(fromSafeConstant("&nbsp;")))
            .add(td().add(pushControl(button("(").on(click, e -> addToken("(")))))
            .add(td().add(pushControl(button(")").on(click, e -> addToken(")")))))
            .add(td().add(pushControl(button("⌫").on(click, e -> removeToken()))))
            .asElement());

        // <dice number> * 10
        multiplierIndex++;
        diceNumberIndex = 0;
        tbody.appendChild(tr()
            .add(td().add(numbers[multiplierIndex][diceNumberIndex++] = button("0").asElement()))
            .add(td().add(numbers[multiplierIndex][diceNumberIndex++] = button("0").asElement()))
            .add(td().add(numbers[multiplierIndex][diceNumberIndex] = button("0").asElement()))
            .add(td().innerHtml(fromSafeConstant("&nbsp;")))
            .add(td().add(pushControl(button("+").on(click, e -> addToken(" + ")))))
            .add(td().add(pushControl(button("-").on(click, e -> addToken(" - ")))))
            .add(td().add(pushControl(button()
                .innerHtml(fromSafeConstant("&#10005;"))
                .on(click, e -> clear()))))
            .asElement());

        // <dice number> * 100
        multiplierIndex++;
        diceNumberIndex = 0;
        tbody.appendChild(tr()
            .add(td().add(numbers[multiplierIndex][diceNumberIndex++] = button("0").asElement()))
            .add(td().add(numbers[multiplierIndex][diceNumberIndex++] = button("0").asElement()))
            .add(td().add(numbers[multiplierIndex][diceNumberIndex] = button("0").asElement()))
            .add(td().innerHtml(fromSafeConstant("&nbsp;")))
            .add(td().add(pushControl(button("*").on(click, e -> addToken(" * ")))))
            .add(td().add(pushControl(button("/").on(click, e -> addToken(" / ")))))
            .add(td().add(pushControl(button("↵").on(click, e -> controller.calculate()))))
            .asElement());
    }

    private HtmlContentBuilder<HTMLButtonElement> pushControl(HtmlContentBuilder<HTMLButtonElement> button) {
        controls.add(button.asElement());
        return button;
    }

    @Override
    public HTMLTableElement asElement() {
        return root;
    }

    void setController(LocalGameController controller) {
        this.controller = controller;
    }

    void showDice(Dice dice) {
        clear();
        for (int diceNumberIndex = 0; diceNumberIndex < numbers.length; diceNumberIndex++) {
            for (int multiplierIndex = 0; multiplierIndex < numbers[diceNumberIndex].length; multiplierIndex++) {
                int number = dice.numbers[diceNumberIndex] * MULTIPLIER[multiplierIndex];
                numbers[multiplierIndex][diceNumberIndex].textContent = String.valueOf(number);
                numbers[multiplierIndex][diceNumberIndex].onclick = e -> {
                    addToken(String.valueOf(number));
                    return null;
                };
            }
        }
    }

    void disable() {
        setEnabled(false);
    }

    void enable() {
        setEnabled(true);
    }

    private void setEnabled(boolean enabled) {
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers[i].length; j++) {
                numbers[i][j].disabled = !enabled;
                toggle(numbers[i][j], disabled, !enabled);
            }
        }
        for (HTMLButtonElement control : controls) {
            control.disabled = !enabled;
            toggle(control, disabled, !enabled);
        }
    }

    private void addToken(String token) {
        tokens.push(token);
        controller.setTerm(String.join("", tokens), true);
    }

    private void removeToken() {
        if (!tokens.isEmpty()) {
            tokens.pop();
            controller.setTerm(String.join("", tokens), true);
        }
    }

    private void clear() {
        tokens.clear();
        controller.setTerm("", true);
    }
}
