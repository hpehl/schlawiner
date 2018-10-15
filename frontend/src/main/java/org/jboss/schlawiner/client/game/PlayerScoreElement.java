package org.jboss.schlawiner.client.game;

import elemental2.dom.HTMLElement;
import elemental2.dom.HTMLTableElement;
import org.jboss.gwt.elemento.core.IsElement;
import org.jboss.schlawiner.engine.game.Numbers;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Players;

import static org.jboss.gwt.elemento.core.Elements.*;
import static org.jboss.schlawiner.client.resources.CSS.contentTable;
import static org.jboss.schlawiner.client.resources.UIConstants.PLAYER_WIDTH;

class PlayerScoreElement extends ScoreElement implements IsElement<HTMLTableElement> {

    private final HTMLTableElement root;

    PlayerScoreElement(Players players, Numbers numbers) {
        HTMLElement colgroup, theadRow, tbody;
        root = table().css(contentTable)
            .add(colgroup = colgroup().asElement())
            .add(thead().add(theadRow = tr().asElement()))
            .add(tbody = tbody().asElement())
            .asElement();

        // colgroup
        double numberWidth = (100.0 - PLAYER_WIDTH) / (numbers.size() + 1); // the last column shows the sum
        colgroup.appendChild(col().style("width: " + PLAYER_WIDTH + "%").asElement());
        for (int ignored : numbers) {
            colgroup.appendChild(col().style("width: " + numberWidth + "%").asElement());
        }
        colgroup.appendChild(col().style("width: " + numberWidth + "%").asElement());

        // header
        int numberIndex = 0;
        theadRow.appendChild(th().textContent(" ").asElement());
        for (int number : numbers) {
            theadRow.appendChild(th()
                .id(numberId(numberIndex))
                .textContent(String.valueOf(number))
                .asElement());
            numberIndex++;
        }
        theadRow.appendChild(th().textContent(" ").asElement()); // sum column

        // body
        HTMLElement tr;
        for (Player player : players) {
            tbody.appendChild(tr = tr()
                .add(td()
                    .id(playerId(player))
                    .textContent(player.getName()))
                .asElement());
            for (int i = 0; i < numbers.size(); i++) {
                tr.appendChild(td()
                    .id(scoreId(player, i))
                    .textContent(" ")
                    .asElement());
            }
            tr.appendChild(td()
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
        return "ps";
    }
}
