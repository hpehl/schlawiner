package org.jboss.schlawiner.client.game;

import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTableElement;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.schlawiner.engine.game.Numbers;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Players;

import static org.jboss.gwt.elemento.core.Elements.*;
import static org.jboss.gwt.elemento.core.EventType.click;
import static org.jboss.schlawiner.client.resources.CSS.clickable;
import static org.jboss.schlawiner.client.resources.CSS.contentTable;
import static org.jboss.schlawiner.client.resources.CSS.numberScore;
import static org.jboss.schlawiner.client.resources.UIConstants.NUMBER_WIDTH;

class NumberScoreElement extends ScoreElement implements IsElement<HTMLTableElement> {

    private final LocalGameComponentImpl component;
    private final HTMLTableElement root;

    NumberScoreElement(LocalGameComponentImpl component, Players players, Numbers numbers) {
        this.component = component;

        HTMLElement colgroup, theadRow, tbody, tfootRow;
        root = table().css(contentTable, numberScore)
            .add(colgroup = colgroup().asElement())
            .add(thead().add(theadRow = tr().asElement()))
            .add(tbody = tbody().asElement())
            .add(tfoot().add(tfootRow = tr().asElement()))
            .asElement();

        // colgroup
        double playerWidth = (100.0 - NUMBER_WIDTH) / players.size();
        colgroup.appendChild(col().style("width: " + NUMBER_WIDTH + "%").asElement());
        for (Player ignored : players) {
            colgroup.appendChild(col().style("width: " + playerWidth + "%").asElement());
        }

        // header
        theadRow.appendChild(th().css(clickable)
            .textContent("↻")
            .title("Switch to player score")
            .on(click, e -> component.showPlayerScore()).asElement());
        for (Player player : players) {
            theadRow.appendChild(th()
                .id(playerId(player))
                .textContent(player.getName())
                .asElement());
        }

        // body
        HTMLElement tr;
        int numberIndex = 0;
        for (int number : numbers) {
            tbody.appendChild(tr = tr()
                .add(td().textContent(String.valueOf(number)))
                .asElement());
            for (Player player : players) {
                tr.appendChild(td()
                    .id(scoreId(player, numberIndex))
                    .textContent(" ")
                    .asElement());
            }
            numberIndex++;
        }

        // footer
        tfootRow.appendChild(th().textContent("∑").asElement());
        for (Player player : players) {
            tfootRow.appendChild(th()
                .id(sumId(player))
                .textContent(" ")
                .asElement());
        }
    }

    @Override
    public HTMLTableElement asElement() {
        return root;
    }

    @Override
    String prefix() {
        return "ns";
    }
}
