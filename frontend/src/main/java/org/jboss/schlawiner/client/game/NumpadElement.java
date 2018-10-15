package org.jboss.schlawiner.client.game;

import java.util.Stack;

import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTableElement;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.schlawiner.engine.game.Dice;

import static org.gwtproject.safehtml.shared.SafeHtmlUtils.fromSafeConstant;
import static org.jboss.gwt.elemento.core.Elements.*;
import static org.jboss.gwt.elemento.core.EventType.click;
import static org.jboss.schlawiner.client.resources.CSS.numpad;

class NumpadElement implements IsElement<HTMLTableElement> {

    private static final int[] WIDTHS = {20, 20, 20, 4, 12, 12, 12};
    private static final int[] MULTIPLIER = {1, 10, 100};

    private final Stack<String> tokens;
    private final HTMLElement numbers[][]; // first dimension is the dice number, second dimension is the multiplier
    private final HTMLTableElement root;
    private LocalGameController controller;

    NumpadElement() {
        HTMLElement colgroup, tbody;
        this.tokens = new Stack<>();
        this.numbers = new HTMLElement[3][3];
        this.root = table().css(numpad)
            .add(colgroup = colgroup().asElement())
            .add(tbody = tbody().asElement())
            .asElement();
        for (int width : WIDTHS) {
            colgroup.appendChild(col().style("width: " + width + "%").asElement());
        }

        // first dice number
        int diceNumberIndex = 0, multiplierIndex = 0;
        tbody.appendChild(tr()
            .add(td().add(numbers[diceNumberIndex][multiplierIndex++] = button("0").asElement()))
            .add(td().add(numbers[diceNumberIndex][multiplierIndex++] = button("0").asElement()))
            .add(td().add(numbers[diceNumberIndex][multiplierIndex] = button("0").asElement()))
            .add(td().innerHtml(fromSafeConstant("&nbsp;")))
            .add(td().add(button("(").on(click, e -> addToken("("))))
            .add(td().add(button(")").on(click, e -> addToken(")"))))
            .add(td().add(button()
                .innerHtml(fromSafeConstant("&larr;"))
                .on(click, e -> removeToken())))
            .asElement());

        // second dice number
        diceNumberIndex++;
        multiplierIndex = 0;
        tbody.appendChild(tr()
            .add(td().add(numbers[diceNumberIndex][multiplierIndex++] = button("0").asElement()))
            .add(td().add(numbers[diceNumberIndex][multiplierIndex++] = button("0").asElement()))
            .add(td().add(numbers[diceNumberIndex][multiplierIndex] = button("0").asElement()))
            .add(td().innerHtml(fromSafeConstant("&nbsp;")))
            .add(td().add(button("+").on(click, e -> addToken(" + "))))
            .add(td().add(button("-").on(click, e -> addToken(" - "))))
            .add(td().add(button()
                .innerHtml(fromSafeConstant("&#10005;"))
                .on(click, e -> clear())))
            .asElement());

        // third dice number
        diceNumberIndex++;
        multiplierIndex = 0;
        tbody.appendChild(tr()
            .add(td().add(numbers[diceNumberIndex][multiplierIndex++] = button("0").asElement()))
            .add(td().add(numbers[diceNumberIndex][multiplierIndex++] = button("0").asElement()))
            .add(td().add(numbers[diceNumberIndex][multiplierIndex] = button("0").asElement()))
            .add(td().innerHtml(fromSafeConstant("&nbsp;")))
            .add(td().add(button("*").on(click, e -> addToken(" * "))))
            .add(td().add(button("/").on(click, e -> addToken(" / "))))
            .add(td().add(button()
                .innerHtml(fromSafeConstant("&crarr;"))
                .on(click, e -> solve())))
            .asElement());
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
        for (int diceNumberIndex = 0; diceNumberIndex < dice.numbers.length; diceNumberIndex++) {
            for (int multiplierIndex = 0; multiplierIndex < MULTIPLIER.length; multiplierIndex++) {
                int number = dice.numbers[diceNumberIndex] * MULTIPLIER[multiplierIndex];
                numbers[diceNumberIndex][multiplierIndex].textContent = String.valueOf(number);
                numbers[diceNumberIndex][multiplierIndex].onclick = e -> {
                    addToken(String.valueOf(number));
                    return null;
                };
            }
        }
    }

    private void addToken(String token) {
        tokens.push(token);
        controller.setTerm(String.join("", tokens));
    }

    private void removeToken() {
        if (!tokens.isEmpty()) {
            tokens.pop();
            controller.setTerm(String.join("", tokens));
        }
    }

    private void clear() {
        tokens.clear();
        controller.setTerm("");
    }

    private void solve() {
        controller.solve();
    }
}
